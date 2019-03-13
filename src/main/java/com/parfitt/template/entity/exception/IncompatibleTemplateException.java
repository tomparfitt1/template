package com.parfitt.template.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "template incompatible with channel type")
public class IncompatibleTemplateException extends RuntimeException {

}
