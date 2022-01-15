package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObjectFromJsonTest.createAnObjectEo;


/**
 * Created by werner.diwischek on 14.1.18.
 */
public class AnObjectToJsonTest {

    private static final String EMPTY = "{}";
    private static final EO AN_OBJECT_EMPTY = ObjectProvider.createEoWithClasses(AnObject.class).map(EMPTY);
    @Test
    public void mapDefault()  {
        EO eo = AN_OBJECT_EMPTY;
        String serialized = new EOToJSON().toJson(eo);
        XpectStringJunit4.assertStatic(serialized);
    }

    @SuppressWarnings({"java:S5976", "perhaps later"})
    @Test
    public void withIndent0()  {
        EO eo = AN_OBJECT_EMPTY;
        String serialized = new EOToJSON()
                .setSpacer("")
                .toJson(eo);
        XpectStringJunit4.assertStatic(serialized);
    }

    @SuppressWarnings({"java:S5976", "perhaps later"})
    @Test
    public void withIndent1()  {
        EO eo = AN_OBJECT_EMPTY;
        String serialized = new EOToJSON()
                .setSpacer("    ")
                .toJson(eo);
        XpectStringJunit4.assertStatic(serialized);
    }

    @SuppressWarnings({"java:S5976", "perhaps later"})
    @Test
    public void withIndent2()  {
        EO eo = AN_OBJECT_EMPTY;
        String serialized = new EOToJSON()
                .setSpacer("    ")
                .toJson(eo);
        XpectStringJunit4.assertStatic(serialized);
    }

    @Test
    public void withSTANDARD()  {
        EO eo = AN_OBJECT_EMPTY;
        String serialized = new EOToJSON()
                .setSpacer("    ")
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJson(eo);
        XpectStringJunit4.assertStatic(serialized);
    }


    @Test
    public void givenUntypedJsonFloat_thenFloat()  {
        EoRoot eo = createAnObjectEo("{\"myFloat\": 1.1}");
        Assertions.assertThat(eo.get(AnObject.MY_FLOAT)).isEqualTo(1.1f);
    }
}
