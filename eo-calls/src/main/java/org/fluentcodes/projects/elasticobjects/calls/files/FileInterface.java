package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
/*.{javaAccessors}|*/

/**
 * Access methods for field properties map.
 *
 * @author Werner Diwischek
 * @creationDate Wed Sep 09 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 14:24:01 CET 2021
 */
public interface FileInterface extends PermissionInterface, ConfigInterface {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    String F_FILE_NAME = "fileName";
    String F_FILE_PATH = "filePath";
    String F_CACHED = "cached";
    String F_TEMPLATE = "template";
    /*.{}.*/

    /*.{javaAccessors}|*/
    Boolean getCached();

    default boolean hasCached() {
        return getCached() != null;
    }

    default boolean isCached() {
        return hasCached() && getCached();
    }

    String getFileName();

    default boolean hasFileName() {
        return getFileName() != null && !getFileName().isEmpty();
    }

    String getFilePath();

    default boolean hasFilePath() {
        return getFilePath() != null && !getFilePath().isEmpty();
    }

    String getHostConfigKey();

    default boolean hasHostConfigKey() {
        return getHostConfigKey() != null && !getHostConfigKey().isEmpty();
    }

    /*.{}.*/
    String getTemplate();

    default boolean hasTemplate() {
        return getTemplate()!=null && !getTemplate().isEmpty();
    }

    default boolean isTargetTemplate() {
        return true;
    }
}
