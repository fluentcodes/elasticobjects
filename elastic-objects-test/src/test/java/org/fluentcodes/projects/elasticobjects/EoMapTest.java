package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_DOUBLE;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoMapTest {
    @Test
    public void TEST__JSON_Double_key_2_2__get_key_2_2() {
        EoRoot eo = ObjectProvider.createEo("{\"(Double)key\": 2.2}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void DEV__JSON_Float_key_1_1__get_key_1_1() {
        EoRoot eo = ObjectProvider.createEo("{\"key\": 1.1}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo((1.1F));
    }

    @Test
    public void TEST__JSON_Float_key_1_1__get_key_1_1() {
        EoRoot eo = ObjectProvider.createEo("{\"(Float)key\": 1.1}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo((1.1F));
    }

    /**
     * Wiki example
     */
    @Test
    public void TEST__JSON_AnObject_myString_value__get_myString_value() {
        final EoRoot eo = ObjectProvider.createEo();
        final String jsonString = "{\n" +
                "\t\"(" + AnObject.class.getSimpleName() + ")key0\":{\n" +
                "\t\t\"" + AnObject.MY_STRING + "\":\"value\"\n" +
                "    }\n" +
                "}";
        eo.map(jsonString);
        assertThat(eo.get("key0", AnObject.MY_STRING)).isEqualTo("value");
        assertThat(((AnObject) eo.get("key0")).getMyString()).isEqualTo("value");
        assertThat(eo.getEo("key0").getModelClass()).isEqualTo(AnObject.class);
    }

    /**
     * Wiki example
     */
    @Test
    public void TEST__JSON_AnObject_with_comment_text__will_be_ignored() {
        final EoRoot eo = ObjectProvider.createEo();
        final String jsonString = "{\n" +
                "\t\"(" + AnObject.class.getSimpleName() + ")level0\":{\n" +
                "\t\t\"" + AnObject.MY_STRING + "\":\"value\",\n" +
                "\"_comment\":\"_comment is not a field of the " + AnObject.class.getSimpleName() + ".class\"" +
                "    }\n" +
                "}";
        eo.map(jsonString);
        assertThat(((AnObject) eo.get("level0")).getMyString()).isEqualTo("value");
    }

    /**
     * Basic Wiki Example
     */
    @Test
    public void TEST__AnObject_myString_value__get_myString_value() {
        final EoRoot eo = ObjectProvider.createEo();
        AnObject bt = new AnObject()
                .setMyString("value");
        eo.map(bt);
        assertThat(eo.get(AnObject.MY_STRING)).isEqualTo("value");
        assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }


    @Test
    public void TEST_AnObject_JSON_myString_value__JSON_myInt_1__get_myInt_1() {
        final EoRoot eo = ObjectProvider.createEoWithClasses(AnObject.class);
        eo.map("{\"myString\": \"value\"}");
        eo.map("{\"myInt\": 1}");
        assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        assertThat(eo.get(AnObject.MY_STRING)).isEqualTo("value");
        assertThat(eo.get(AnObject.MY_INT)).isEqualTo(1);
    }
}


