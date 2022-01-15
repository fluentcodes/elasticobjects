package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EoToJsonDevTest {
    @Test
    public void Map_empty____expected() {
        EoRoot eo = ObjectProviderDev.createEo("{}");
        String json = new EOToJSON().toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "}");
    }

    @Test
    public void Map_empty__indent_0__expected() {
        EoRoot eo = ObjectProviderDev.createEo("{}");
        String json = new EOToJSON()
                .setSpacer("")
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{" +
                "}");
    }

    @Test
    public void Map_empty__indent_1__expected() {
        EoRoot eo = ObjectProviderDev.createEo("{}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "}");
    }

    @Test
    public void Map_empty__indent_2__expected() {
        EoRoot eo = ObjectProviderDev.createEo("{}");
        String json = new EOToJSON()
                .setSpacer("    ")
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "}");
    }

    @Test
    public void Map_key_value__indent_2__expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"key\": \"value\"}");
        String json = new EOToJSON()
                .setSpacer("    ")
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "    \"key\": \"value\"\n" +
                "}");
    }

    @Test
    public void Map_key_value__JSONSerialitationType_SCALAR__expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"key\": \"value\"}");
        String json = new EOToJSON(JSONSerializationType.SCALAR)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"key\": \"value\"\n" +
                "}");
    }

    @Test
    public void Map_key_1__JSONSerialitationType_SCALAR__expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"key\": 1}");
        String json = new EOToJSON(JSONSerializationType.SCALAR)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"key\": 1\n" +
                "}");
    }

    @Test
    public void Map_Long_key_1____expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"(Long)key\": 1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +

                "  \"(Long)key\": 1\n" +
                "}");
    }

    @Test
    public void Map_Long_key_1__JSONSerialitationType_SCALAR__expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"(Long)key\": 1}");
        String json = new EOToJSON(JSONSerializationType.SCALAR)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"key\": 1\n" +
                "}");
    }

    @Test
    public void Map_key_1_1____expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"key\": 1.1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +

                "  \"(Float)key\": 1.1\n" +
                "}");
    }


    @Test
    public void Map_Double_key_1_1____expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"(Double)key\": 1.1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +

                "  \"(Double)key\": 1.1\n" +
                "}");
    }

    @Test
    public void Map_Date_key_1465280215000____expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"(Date)key\": 1465280215000}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +

                "  \"(Date)key\": 1465280215000\n" +
                "}");
    }

    @Test
    public void Map_Boolean_key_true____expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"key\": true}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"key\": true\n" +
                "}");
    }

    @Test
    public void Map_myString_value_myInt_1____expected() {
        EoRoot eo = ObjectProviderDev.createEo("{\"myString\": \"value\", \"myInt\", 1}");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"myString\": \"value\",\n" +
                "  \"myInt\": 1\n" +
                "}");
    }

    @Test
    public void List_empty__indent_0__expected() {
        EoRoot eo = ObjectProviderDev.createEo("[]");
        String json = new EOToJSON()
                .setSpacer("")
                .toJson(eo);
        Assertions.assertThat(json).
                isEqualTo("{\"_rootmodel\": \"List\"" +
                        "}");
    }

    @Test
    public void List_empty__indent_2__expected() {
        EoRoot eo = ObjectProviderDev.createEo("[]");
        String json = new EOToJSON()
                .setSpacer("    ")
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "    \"_rootmodel\": \"List\"\n" +
                "}");
    }

    @Test
    public void List_String_test____expected() {
        EoRoot eo = ObjectProviderDev.createEo("[\"test\"]");
        String json = new EOToJSON()
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("{\n" +
                "  \"_rootmodel\": \"List\",\n" +
                "  \"0\": \"test\"\n" +
                "}");

    }

    @Test
    public void List_String_test__JSONSerializeType_Standard__expected() {
        EoRoot eo = ObjectProviderDev.createEo("[\"test\"]");
        String json = new EOToJSON()
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJson(eo.getRoot());
        Assertions.assertThat(json).isEqualTo("[\n" +
                "  \"test\"\n" +
                "]");
    }

    @Test
    public void List_Integer__JSONSerializionType_Standard__expected() {
        EoRoot eo = ObjectProviderDev.createEo("[1]");
        String json = new EOToJSON()
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJson(eo);
        Assertions.assertThat(json).isEqualTo("[\n" +
                "  1\n" +
                "]");
    }

}
