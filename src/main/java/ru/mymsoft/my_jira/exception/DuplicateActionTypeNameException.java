package ru.mymsoft.my_jira.exception;

/**
 * Исключение для совпадающих имен в классе ActionType.java
 */
public class DuplicateActionTypeNameException extends RuntimeException {
    public DuplicateActionTypeNameException(String name) {
        super("ActionType with name '" + name + "' already exists");
    }
}