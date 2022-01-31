package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EoToJsonTest {
    @Test
    public void class_AnObject_values____expected() {
        final EoRoot eo = ObjectProvider.createEo(AnObject.class);
        eo.map("{\"myString\": \"value\", \"myInt\", 1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json)
                .isEqualTo("{\n" +
                        "  \"_rootmodel\": \"AnObject\",\n" +
                        "  \"myString\": \"value\",\n" +
                        "  \"myInt\": 1\n" +
                        "}");
    }

    @Test
    public void AnObject_myString_value____expected() {
        AnObject anObject = new AnObject();
        anObject.setMyString("value");
        String json = new EOToJSON()
                .toJson(ObjectProvider.CONFIG_MAPS, anObject);
        Assertions.assertThat(json)
                .isEqualTo("{\n" +
                        "  \"_rootmodel\": \"AnObject\",\n" +
                        "  \"myString\": \"value\"\n" +
                        "}");
    }


    @Test(expected = EoException.class)
    public void ModelConfig_ASubObject____exception() {
        ModelConfig modelConfig = ObjectProvider.CONFIG_MAPS.findModel(ASubObject.class);
        EOToJSON eoToJSON = new EOToJSON();
                    eoToJSON
                            .toJson(ObjectProvider.CONFIG_MAPS, modelConfig);
     }

    @Test
    public void ModelConfig_ASubObject__JSONSerializationType_STANDARD__no_exception() {
        ModelConfig modelConfig = ObjectProvider.CONFIG_MAPS.findModel(ASubObject.class);
        String json = new EOToJSON(JSONSerializationType.STANDARD)
                .toJson(ObjectProvider.CONFIG_MAPS, modelConfig);
        Assertions.assertThat(json).isNotEmpty();
    }
}
