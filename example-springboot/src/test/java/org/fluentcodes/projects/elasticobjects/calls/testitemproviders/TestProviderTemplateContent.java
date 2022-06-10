package org.fluentcodes.projects.elasticobjects.calls.testitemproviders;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.io.IOString;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.PATH_INPUT;

;

public enum TestProviderTemplateContent {
    CONFIGS_HTML(PATH_INPUT + "calls/configs/ConfigsPage.html");

    private final String content;
    private String fileName;

    TestProviderTemplateContent(final String content) {
        if (content.startsWith(PATH_INPUT)) {
            this.fileName = content;
            this.content = new IOString(content).read();
        } else {
            this.content = content;
        }
    }

    public String content() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getConfigKey() {
        if (fileName == null) {
            throw new EoException("No fileName for " + name() + " defined");
        }
        return fileName.replaceAll(".*/", "");
    }
}
