package com.otus.storeservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.storeservice.entity.Message;
import com.otus.storeservice.rabbitmq.domain.RMessage;
import com.otus.storeservice.rabbitmq.domain.dto.CancelDTO;
import com.otus.storeservice.rabbitmq.domain.dto.TrxDTO;
import com.otus.storeservice.service.OrderService;
import com.otus.storeservice.service.StorageService;
import com.otus.storeservice.service.MessageService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueListener {

    private final MessageService messageService;
    private final StorageService storageService;

    private final OrderService orderService;
    private final RabbitTemplate rt;

    @Value("${spring.rabbitmq.queues.service-answer-queue}")
    private String answerQueue;
    @Value("${spring.rabbitmq.exchanges.service-answer-exchange}")
    private String answerExchange;

    @Transactional
    @RabbitListener(queues = "${spring.rabbitmq.queues.service-queue}", ackMode = "MANUAL")
    public void orderQueueListener(RMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            var om = new ObjectMapper();
            var msg = messageService.findById(message.getMsgId());
            if (msg == null) {
                messageService.save(new Message(message.getMsgId()));
                switch (message.getCmd()) {
                    case "bookingFood" ->  {
                        om.getTypeFactory().constructCollectionType(ArrayList.class, TrxDTO.class);
                        var trxDTO = om.convertValue(message.getMessage(), TrxDTO.class);
                        var answer = storageService.bookingFood(trxDTO);
                        trxDTO.setStoreStatus(answer);
                        rt.convertAndSend(answerExchange, answerQueue,
                                new RMessage(UUID.randomUUID().toString(), "bookingCourier", trxDTO)
                        );
                    }
                    case "cancelBookingFood" ->  {
                        om.getTypeFactory().constructCollectionType(ArrayList.class, CancelDTO.class);
                        var cancelDTO = om.convertValue(message.getMessage(), CancelDTO.class);
                        var orders = orderService.findAllByOrderId(cancelDTO);
                        storageService.cancelStorage(orders);
                        orderService.deleteAll(orders);
                    }
                    default -> log.warn("::StoreService:: rabbitmq listener method. Unknown message type");
                }
            }
            else {
                log.warn("::StoreService:: rabbitmq listener method orderQueueListener duplicate message!");
            }
        } catch (Exception ex) {
            log.error("::StoreService:: rabbitmq listener method orderQueueListener with error message {}", ex.getLocalizedMessage());
            log.error("::StoreService:: rabbitmq listener method orderQueueListener with stackTrace {}", ExceptionUtils.getStackTrace(ex));
        } finally {
            basicAck(channel, tag, true);
        }
    }

    private void basicAck(Channel channel, Long tag, boolean b) {
        try {
            channel.basicAck(tag, b);
        } catch (IOException ex) {
            log.error("::StoreService:: rabbitmq listener method basicAck with stackTrace {}", ExceptionUtils.getStackTrace(ex));
        }
    }
}
