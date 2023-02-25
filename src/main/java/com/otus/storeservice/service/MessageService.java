package com.otus.storeservice.service;

import com.otus.storeservice.entity.Message;


public interface MessageService {

    Message save(Message user);
    Message findById(String msgId);

}
