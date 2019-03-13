package com.parfitt.template.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "channel not found")
public class ChannelNotFoundException extends RuntimeException {

}
