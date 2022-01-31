package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreCall;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

public class GenerateHtml {
    static final String XLSX_FILE = "content.xlsx";
    static final String DATA = "data";

    @Test
    public void createIntro() {
        EoRoot eo = READ_XLSX(XLSX_FILE);
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();
        call.setSourceFileConfigKey("Presentation.html");
        //call.setLocalCondition("head like Get an");
        call.setSourcePath("/data/*");
        call.setTargetFileConfigKey("src/main/resources/static/presentation/=.[StringReplaceWhiteSpaceCall->head]..html");
        eo.addCall(call);
        eo.execute();
    }

    public static EoRoot READ_XLSX(final String xlsxFile) {
        XlsxReadCall call = new XlsxReadCall(xlsxFile);
        //call.setFilter("head like Get");
        call.setTargetPath(String.join(Path.DELIMITER, new String[]{DATA, ".[head]."}));
        EoRoot eo = ObjectProvider.createEo();
        call.execute(eo);

        return eo;
    }
}
