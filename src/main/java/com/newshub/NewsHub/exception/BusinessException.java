package com.newshub.NewsHub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Кастомное исключения для выбрасывания ошибки
 * при некорректной работе бизнес логики (код ошибки 400)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException() {
    }
}
