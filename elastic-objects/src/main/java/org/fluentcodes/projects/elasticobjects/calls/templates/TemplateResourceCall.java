package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 26.08.20.
 */
public class TemplateResourceCall extends TemplateCall {
    private static final transient Logger LOG = LogManager.getLogger(TemplateResourceCall.class);
    private String configKey;
    private KeepCalls keepCall;
    private String directive;
    private String endDirective;

    public TemplateResourceCall() {
        super();
    }

    public String getConfigKey() {
        return configKey;
    }

    public TemplateResourceCall setConfigKey(String configKey) {
        this.configKey = configKey;
        return this;
    }

    public KeepCalls getKeepCall() {
        return keepCall;
    }

    public void setKeepCall(KeepCalls keepCall) {
        if (this.keepCall != null) {
            return;
        }
        this.keepCall = keepCall;
    }

    public String getDirective() {
        return directive;
    }

    public void setDirective(String directive) {
        this.directive = directive;
    }

    public String getEndDirective() {
        return endDirective;
    }

    public void setEndDirective(String endDirective) {
        this.endDirective = endDirective;
    }

    public String prepend() {
        if (directive==null) {
            return "";
        }
        return directive;
    }
    public String postPend() {
        if (endDirective==null) {
            return "";
        }
        return endDirective;
    }

    public String execute(EO eo)  {
        String replacedConfig = new ParserEoReplace(configKey).parse(eo);
        TemplateConfig config = eo.getConfigsCache().findTemplate(replacedConfig);
        super.setContent(config.read());
        return super.execute(eo);
    }

}
