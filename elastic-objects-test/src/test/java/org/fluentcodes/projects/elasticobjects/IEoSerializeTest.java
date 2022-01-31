package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek 14.8.2020..
 */
public class IEoSerializeTest {

    @Test
    public void givenDev_thenSerializationTypeEo()  {
        EoRoot eo = ObjectProviderDev.createEo();
        Assertions.assertThat(eo.getSerializationType()).isEqualTo(JSONSerializationType.EO);
        Assertions.assertThat(eo.size()).isEqualTo(0);
        String serialized = new EOToJSON().toJson(eo);
        Assert.assertEquals("{\n" +
                "}", serialized);

        eo.setSerializationType(JSONSerializationType.EO);
        serialized = new EOToJSON().toJson(eo);
        Assert.assertEquals("{\n" +
                "  \"(JSONSerializationType)_serializationType\": \"EO\"\n" +
                "}", serialized);
        EoRoot fromJson = ObjectProviderDev.createEo(serialized);
        Assertions.assertThat(fromJson.getSerializationType()).isEqualTo(JSONSerializationType.EO);
    }

    @Test
    public void givenDev_whenSetSerializationTypeStandard_thenSerializationTypeEo()  {
        EoRoot eo = ObjectProviderDev.createEo();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getSerializationType()).isEqualTo(JSONSerializationType.STANDARD);
        Assertions.assertThat(eo.size()).isEqualTo(0);
        Assert.assertEquals("{\n" +
                "}", new EOToJSON().toJson(eo));

        final String serialized = new EOToJSON(JSONSerializationType.EO).toJson(eo);
        Assert.assertEquals("{\n" +
                "  \"(JSONSerializationType)_serializationType\": \"STANDARD\"\n" +
                "}", serialized);
        EoRoot fromJson = ObjectProviderDev.createEo(serialized);
        Assertions.assertThat(fromJson.getSerializationType()).isEqualTo(JSONSerializationType.STANDARD);
    }
}
