package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

/*.{javaHeader}|*/

/**
 * For setting values to EO.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 12:04:23 CET 2020
 */
public class ValueCall extends CallImpl implements CallContent, SimpleCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    public static final String CONTENT = "content";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private String content;
    /*.{}.*/

    public ValueCall() {
    }

    public ValueCall(final String content) {
        this();
        this.content = content;
    }

    @Override
    public String execute(final EOInterfaceScalar eo) {
        super.check(eo);
        if (!hasContent()) {
            if (eo.isScalar()) {
                content = new ShapeTypeSerializerString().asObject(eo.get());
            } else {
                content = new EOToJSON()
                        .setSerializationType(JSONSerializationType.STANDARD)
                        .toJson(eo);
            }
        }
        return super.createReturnString(eo, content);
    }

    /*.{javaAccessors}|*/

    /**
     * A content for different calls. In a template context the content of the markup.
     */

    public ValueCall setContent(String content) {
        this.content = content;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }
    /*.{}.*/


}
