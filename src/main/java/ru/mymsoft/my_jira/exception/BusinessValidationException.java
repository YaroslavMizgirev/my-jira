package ru.mymsoft.my_jira.exception;

/**
 * Исключение для ошибок бизнес-валидации
 * Например: некорректный статус перехода, невалидные даты
 */
public class BusinessValidationException extends RuntimeException {

    public BusinessValidationException(String message) {
        super(message);
    }

    public BusinessValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
