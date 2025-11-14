package ru.mymsoft.my_jira.exception;

/**
 * Исключение для случаев, когда пытаются создать ресурс с дублирующимися данными
 * Например: пользователь с таким email уже существует, проект с таким ключом уже есть
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: '%s'",
            resourceName, fieldName, fieldValue));
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
