package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoRootTestTest {
    @Test
    public void __JSONMap_Models_String_Map_ASubObject__noError()  {
        final String json = "{  \"ASubObject\": {\n" +
                "    \"configModelKey\": \"ModelConfigDbObject\",\n" +
                "    \"module\": \"elastic-objects-test\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": [\n" +
                "      \"id\",\n" +
                "      \"myASubObject\",\n" +
                "      \"myString\",\n" +
                "      \"name\",\n" +
                "      \"naturalId\"\n" +
                "    ],\n" +
                "    \"expose\": \"WEB\",\n" +
                "    \"description\": \"A sub object as an example.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.domain.test\",\n" +
                "    \"modelKey\": \"ASubObject\",\n" +
                "    \"properties\": {\n" +
                "      \"classPath\": \"src.main.java\",\n" +
                "      \"create\": true,\n" +
                "      \"shapeType\": \"BEAN\"\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"rolePermissions\": {\n" +
                "      \"read\": \"guest\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        EoRoot modelEo = ObjectProviderDev.createEo( json);
    }

    @Test
    public void TEST__ofValue_AnObject_myString_value__get_myString_value() {
        AnObject anObject = new AnObject()
                .setMyString("value");
        EoRoot eoRoot = ObjectProvider.createEo( anObject);
        Assertions.assertThat(eoRoot.get("myString"))
                .isEqualTo("value");
    }
}


