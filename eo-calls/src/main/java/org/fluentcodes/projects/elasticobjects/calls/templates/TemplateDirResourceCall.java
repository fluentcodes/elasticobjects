package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.io.IORuntimeException;
/**
 * Parses the content loaded by {@link DirectoryReadCall}.
 * This will use a FileConfig entry using {@link DirectoryConfig} instance.
 */
public class TemplateDirResourceCall extends DirectoryReadCall {

    public TemplateDirResourceCall() {
        super();
    }

    public TemplateDirResourceCall(final String configKey) {
        super(configKey);
    }

    public TemplateDirResourceCall(final String configKey, final String fileName) {
        super(configKey, fileName);
    }

    @Override
    public void setByParameter(final String values) {
        if (values == null || values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length > 5) {
            throw new EoException("Short form should have form '<configKey>[,<targetPath>][,<condition>][,<keepCall>]' with max length 4 but has size " + array.length + ": '" + values + "'.");
        }
        if (array.length > 0) {
            setConfigKey(array[0]);
        }
        if (array.length > 1) {
            setFileName(array[1]);
        }
        if (array.length > 2) {
            setTargetPath(array[2]);
        }
        if (array.length > 3) {
            setCondition(array[3]);
        }
        if (array.length > 4) {
            setKeepCall(KeepCalls.valueOf(array[4]));
        }
    }

    @Override
    public String execute(EOInterfaceScalar eo) {
        try {
            String content = super.read(eo);
            String result = new TemplateCall(content)
                    .execute(eo);
            return result;
        } catch (EoException | IORuntimeException e) {
            if (hasLogLevel() && getLogLevel().equals(LogLevel.NONE)) {
                return "";
            }
            throw e;
        }
    }

    /*.{javaAccessors}|*/
    /*.{}.*/
}
