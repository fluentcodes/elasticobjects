package org.fluentcodes.projects.elasticobjects.calls.files;

import java.util.List;
/**
 * Access methods for field properties map.
 *
 * @author Werner Diwischek
 * @creationDate Wed Sep 09 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 14:24:01 CET 2021
 */
public interface FilePropertiesInterface {

    List<String> getColKeys();

    default boolean hasColKeys() {
        return getColKeys() != null && !getColKeys().isEmpty();
    }

    String getFieldDelimiter();

    default boolean hasFieldDelimiter() {
        return getFieldDelimiter() != null && !getFieldDelimiter().isEmpty();
    }

    String getFilter();

    default boolean hasFilter() {
        return getFilter() != null && !getFilter().isEmpty();
    }

    Integer getLength();

    default boolean hasLength() {
        return getLength() != null && getLength() > 0;
    }

    Integer getRowHead();

    default boolean hasRowHead() {
        return getRowHead() != null && getRowHead() > -1;
    }

    Integer getRowStart();

    default boolean hasRowStart() {
        return getRowStart() != null && getRowStart() > -1;
    }

    Integer getRowEnd();

    default boolean hasRowEnd() {
        return getRowEnd() != null && getRowEnd() > -1;
    }

    String getRowDelimiter();

    default boolean hasRowDelimiter() {
        return getRowDelimiter() != null && !getRowDelimiter().isEmpty();
    }

    String getSheetName();

    default boolean hasSheetName() {
        return getSheetName() != null && !getSheetName().isEmpty();
    }

}
