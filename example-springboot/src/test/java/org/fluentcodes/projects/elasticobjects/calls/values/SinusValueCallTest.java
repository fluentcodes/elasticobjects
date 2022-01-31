package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

/**
 * Created by werner.diwischek on 11.03.20.
 */
public class SinusValueCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return SinusValueCall.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void eoStringTemplate__execute__xpected() {
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "   \"(List,Double)source\": {\n" +
                "     \"0\": 1,\n" +
                "     \"1\": 2,\n" +
                "     \"2\": 3\n" +
                "   },\n" +
                "   \"(SinusValueCall)0\": {\n" +
                "     \"sourcePath\": \"/source/*\",\n" +
                "     \"targetPath\": \"/target\"\n" +
                "   },\n" +
                "   \"(TemplateCall)1\": {\n" +
                "     \"sourcePath\": \"/source/*\",\n" +
                "     \"content\": \"sin(.{_value}.) = .{/target/_parent}.\\n\"\n" +
                "   }\n" +
                " }");
        eo.execute();
        Assertions.assertThat(eo.get("_template")).isEqualTo("sin(1.0) =0.8414709848078965\n" +
                "sin(2.0) =0.9092974268256817\n" +
                "sin(3.0) =0.1411200080598672\n");
    }
}
