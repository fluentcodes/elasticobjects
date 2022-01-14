package org.fluentcodes.projects.elasticobjects.documentation;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.values.SinusValueCall;
import org.junit.Test;

import java.util.HashMap;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;

public class EOReadmeTest {
    @Test
    public void direct() {
        final Call call = new SinusValueCall();
        EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new HashMap());
        IEOScalar child = root.set(2.1, "source");
        assertEquals(2.1, child.get());

        assertEquals(Double.valueOf(0.8632093666488737), call.execute(child));
    }

    @Test
    public void fromJson() {
        EoRoot root = EoRoot.ofValue(CONFIG_MAPS, "{\n" +
                "  \"(Double)source\":1,\n" +
                "  \"(SinusValueCall)/target\": {\n" +
                "    \"sourcePath\": \"/source\"\n" +
                "  }\n" +
                "}");
        root.execute();
        assertEquals("{\n" +
                "  \"source\": 1.0,\n" +
                "  \"target\": 0.8414709848078965\n" +
                "}", root.toJson(JSONSerializationType.STANDARD));
    }

    @Test
    public void asTemplate() {
        EoRoot root = EoRoot.of(CONFIG_MAPS);
        String template = "START - @{\n" +
                "  \"(Double)source\":1,\n" +
                "  \"(SinusValueCall)_asString\": {\n" +
                "    \"sourcePath\": \"/source\"\n" +
                "  }\n" +
                "}. - END";
        Call call = new TemplateCall(template);
        assertEquals("START -0.8414709848078965 - END", call.execute(root));
    }
}
