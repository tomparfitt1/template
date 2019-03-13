package com.parfitt.template.controller;

import com.parfitt.template.entity.MessageRequest;
import com.parfitt.template.service.MessageService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity send(@Valid @RequestBody MessageRequest request) {

        messageService.send(request.getTemplate(), request.getChannel(), request.getContent());

        return ResponseEntity.noContent().build();
    }

}
