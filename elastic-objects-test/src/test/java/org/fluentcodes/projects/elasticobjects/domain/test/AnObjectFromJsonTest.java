package org.fluentcodes.projects.elasticobjects.domain.test;


import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_DOUBLE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.createEo;
import static org.junit.Assert.assertEquals;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class AnObjectFromJsonTest {

    final String DOUBLE_TYPED = "{\"(Double)myDouble\": 2.2}";
    public static final String ALL = "{\n" +
            "  \"myString\": \"test\",\n" +
            "  \"myInt\": 1,\n" +
            "  \"myLong\": 2,\n" +
            "  \"myFloat\": 1.1,\n" +
            "  \"myDouble\": 2.2,\n" +
            "  \"myDate\": 1465280215000,\n" +
            "  \"myBoolean\": true,\n" +
            "  \"myASubObject\": {\n" +
            "    \"name\": \"testOther\",\n" +
            "    \"myString\": \"test\"\n" +
            "  },\n" +
            "  \"myAnObject\": {\n" +
            "    \"myInt\": 1,\n" +
            "    \"myString\": \"test\",\n" +
            "    \"myLong\": 2,\n" +
            "    \"myDate\": 1465280215000,\n" +
            "    \"myBoolean\": true,\n" +
            "    \"myFloat\": 1.1,\n" +
            "    \"myDouble\": 2.2\n" +
            "  },\n" +
            "  \"myList\": [\n" +
            "    \"test\",\n" +
            "    \"1\"\n" +
            "  ],\n" +
            "  \"myMap\": {\n" +
            "    \"myString\": \"test\",\n" +
            "    \"myInt\": 1\n" +
            "  },\n" +
            "  \"myASubObjectMap\": {\n" +
            "    \"key0\": {\n" +
            "      \"name\": \"testOther\",\n" +
            "      \"myString\": \"test\"\n" +
            "    },\n" +
            "    \"key1\": {\n" +
            "      \"name\": \"testOther\",\n" +
            "      \"myString\": \"test\"\n" +
            "    },\n" +
            "    \"key2\": {\n" +
            "      \"name\": \"testOther\",\n" +
            "      \"myString\": \"test\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
    public static final String ALL_TYPED = "{\n" +
            "    \"myString\": \"test\",\n" +
            "    \"(Integer)myInt\": 1,\n" +
            "    \"myLong\": 2,\n" +
            "    \"(Float)myFloat\": 1.1,\n" +
            "    \"(Double)myDouble\": 2.2,\n" +
            "    \"(Date)myDate\": 1465280215000,\n" +
            "    \"(Boolean)myBoolean\": true,\n" +
            "    \"(ASubObject)myASubObject\": {\n" +
            "      \"name\": \"testOther\",\n" +
            "      \"myString\": \"test\"\n" +
            "    },\n" +
            "    \"(AnObject)myAnObject\": {\n" +
            "      \"myInt\": 1,\n" +
            "      \"myString\": \"test\",\n" +
            "      \"myLong\": 2,\n" +
            "      \"myDate\": 1465280215000,\n" +
            "      \"myBoolean\": true,\n" +
            "      \"myFloat\": 1.1,\n" +
            "      \"myDouble\": 2.2\n" +
            "    },\n" +
            "    \"(ArrayList)myList\": {\n" +
            "      \"0\": \"test\",\n" +
            "      \"(Integer)1\": 1\n" +
            "    },\n" +
            "    \"myMap\": {\n" +
            "      \"myString\": \"test\",\n" +
            "      \"(Integer)myInt\": 1\n" +
            "    },\n" +
            "    \"myASubObjectMap\": {\n" +
            "      \"(ASubObject)key0\": {\n" +
            "        \"name\": \"testOther\",\n" +
            "        \"myString\": \"test\"\n" +
            "      },\n" +
            "      \"(ASubObject)key1\": {\n" +
            "        \"name\": \"testOther\",\n" +
            "        \"myString\": \"test\"\n" +
            "      },\n" +
            "      \"(ASubObject)key2\": {\n" +
            "        \"name\": \"testOther\",\n" +
            "        \"myString\": \"test\"\n" +
            "      }\n" +
            "    }\n" +
            "}";

    public static final EoRoot createAnObjectEo()  {
        return createEo(new AnObject());
    }
    public static final EoRoot createAnObjectEo(final String json)  {
        EoRoot root = createAnObjectEo();
        root.set(json);
        return root;
    }

    @Test
    public void id_1____id_1() {
        EoRoot eo = createEo("{\n" +
                "   \"(AnObject)abc\":{\n" +
                "        \"id\":1\n" +
                "   }" +
                "}");
        assertEquals(AnObject.class, eo.get("abc").getClass());
        assertEquals(1L, eo.get("abc/id"));

    }

    @Test
    public void givenJsonTypedDouble_thenDouble()  {
        EoRoot root = createAnObjectEo(DOUBLE_TYPED);
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(root.get(AnObject.MY_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void givenJsonUntypedDouble_thenDouble()  {
        EoRoot root = createEo(new AnObject());
        root.set("{\"myDouble\": 2.2}");
        AnObject bt = (AnObject) root.get();
        Assertions.assertThat(bt.getMyDouble()).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void small__xpected()  {
        EoRoot eo = createAnObjectEo("{\"myString\": \"test\", \"myInt\": 1}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(AnObject.F_MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(AnObject.F_MY_INT)).isEqualTo((S_INTEGER));
    }

    @Test
    public void all__expected()  {
        EoRoot eo = createAnObjectEo(ALL);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(AnObject.F_MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(AnObject.F_MY_INT)).isEqualTo((S_INTEGER));
    }

    @Test
    public void SubObjectJson__thenNoLog()  {
        EoRoot eo = createAnObjectEo("{\"(ASubObject)myASubObject\": {\"name\": \"testOther\",\"myString\": \"test\"}}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(AnObject.MY_ASUB_OBJECT, AnObject.F_MY_STRING)).isEqualTo((S_STRING));
    }

    @Test
    public void AnObject_rootModel()  {
        EoRoot root = createEo();
        EoChild child = (EoChild) root.set("{\n" +
                "  \"_rootmodel\": \"AnObject\"" +
                "}", "data");
        assertEquals(AnObject.class, child.getModelClass());
    }


}
