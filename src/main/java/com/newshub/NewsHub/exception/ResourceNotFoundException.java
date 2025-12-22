package com.newshub.NewsHub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Кастомное исключение для выбрасывания ошибки
 * при не найденом ресурсе в БД (User, NewsArticle..) (код ошибки 404)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * конструктор для удобного формирования исключения
     * @param resourceName не найденный ресурс
     * @param fieldName свойство для поиска ресурса
     * @param fieldValue значение свойства ресурска
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
