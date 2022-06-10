package org.fluentcodes.projects.elasticobjects.calls.templates;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;

/*.{javaHeader}|*/

/**
 * Executes a TemplateRecourceCall and then a FileWriteCall wth the targetConfigKey.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 11:39:40 CET 2020
 */
public class TemplateResourceStoreCall extends FileReadWriteCall {
    public TemplateResourceStoreCall() {
        super();
        setCompare(true);
    }

    public TemplateResourceStoreCall(final String sourceFileConfigKey, final String targetFileConfigKey) {
        super(sourceFileConfigKey, targetFileConfigKey);
    }


    public String execute(EOInterfaceScalar eo) {
        String template = readSourceOrTarget(eo);
        if (!TemplateMarker.CURLY.hasStartSequence(template)) {
            return "Nothing to do no template parts.";
        }
        String content = new TemplateCall(template).execute(eo);
        //throw new EoInternalException("");
        return writeTarget(eo, content);
    }

    private String readSourceOrTarget(final EOInterfaceScalar eo) {
        if (!hasSourceFileConfigKey()) {
            return readTarget(eo);
        }
        try {
            return readTarget(eo);
        } catch (Exception e) {
            return readSource(eo);
        }
    }

    /*.{javaAccessors}|*/
    /*.{}.*/
}
