package org.fluentcodes.projects.elasticobjects.calls.files;

/*.{javaHeader}|*/

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;

/**
 * Serialize the value of the sourcePath as JSON and write it to a file
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 09:49:43 CET 2020
 */
public class JsonWriteCall extends FileWriteCall {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    public static final String INDENT = "indent";
    public static final String SERIALIZATION_TYPE = "serializationType";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private String spacer;
    private JSONSerializationType serializationType;
    /*.{}.*/

    public JsonWriteCall() {
        super();
    }

    public JsonWriteCall(final String configKey) {
        super(configKey);
    }

    @Override
    public String execute(final EOInterfaceScalar eo) {
        if (!hasSerializationType()) {
            serializationType = JSONSerializationType.STANDARD;
        }
        if (!hasSpacer()) {
            spacer = "  ";
        }
        setContent(new EOToJSON()
                .setSerializationType(serializationType)
                .setSpacer(spacer)
                .toJson(eo));
        return super.execute(eo);
    }

    /*.{javaAccessors}|*/

    /**
     * The number of indent for serialization level.
     */

    public JsonWriteCall setSpacer(String spacer) {
        this.spacer = spacer;
        return this;
    }

    public String getSpacer() {
        return this.spacer;
    }

    public boolean hasSpacer() {
        return spacer != null && !spacer.isEmpty();
    }

    /**
     * The types of Serialization.
     */

    public JsonWriteCall setSerializationType(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
        return this;
    }

    public JSONSerializationType getSerializationType() {
        return this.serializationType;
    }

    public boolean hasSerializationType() {
        return serializationType != null;
    }
    /*.{}.*/
}
