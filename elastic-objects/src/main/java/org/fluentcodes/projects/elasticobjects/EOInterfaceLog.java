package org.fluentcodes.projects.elasticobjects;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EOInterfaceLog extends EOInterfaceBase {
    default void log(String message, LogLevel logLevel, Exception e) {
        log(message + ": " + e.getMessage(), logLevel);
    }

    default boolean hasLogLevel() {
        return false;
    }

    default void log(String message, LogLevel logLevel) {
        getRoot().log(message, logLevel);
    }

    default String getLog() {
        return getLogList().stream().collect(Collectors.joining("\n"));
    }

    default List<String> getLogList() {
        return getRoot().getLogList();
    }

    default LogLevel getLogLevel() {
        return getParent().getLogLevel();
    }

    default void setLogLevel(LogLevel logLevel) {
        getParent().setLogLevel(logLevel);
    }

    default LogLevel getErrorLevel() {
        return getRoot().getErrorLevel();
    }

    default boolean hasErrors() {
        return getErrorLevel() == LogLevel.ERROR;
    }

    default boolean checkLevel(LogLevel messageLevel) {
        return getLogLevel().ordinal() <= messageLevel.ordinal();
    }

    default void debug(String message) {
        if (checkLevel(LogLevel.DEBUG)) {
            log(message, getLogLevel());
        }
    }

    default void info(String message) {
        if (checkLevel(LogLevel.INFO)) {
            log(message, LogLevel.INFO);
        }
    }

    default void warn(String message) {
        if (checkLevel(LogLevel.WARN)) {
            log(message, LogLevel.WARN);
        }
    }

    default void error(String message) {
        if (checkLevel(LogLevel.ERROR)) {
            log(message, LogLevel.ERROR);
        }
    }

    default void warn(String message, Exception e) {
        if (checkLevel(LogLevel.WARN)) {
            log(message, LogLevel.WARN, e);
        }
    }

    default void error(String message, Exception e) {
        if (checkLevel(LogLevel.ERROR)) {
            log(message, LogLevel.ERROR, e);
        }
    }
}
