package com.otus.storeservice.rabbitmq.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RMessage extends AMessage{
    public RMessage(String msgId, String cmd, Object message) {
        super(msgId, cmd, message);
    }
}

