package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_FLOAT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_FLOAT;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONBTTest extends TestHelper {

    @Test
    public void mapDefault()  {
        EO adapter = BTProviderEO.create();
        String serialized = new EOToJSON().toJSON(adapter);
        AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0()  {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1()  {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2()  {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withSTANDARD()  {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }


    @Test
    public void withEO()  {
        BTProviderEO.create();
    }

    @Test
    public void withString()  {
        BTProviderEO.createString();
    }

    @Test
    public void withInteger()  {
        BTProviderEO.createInteger();
    }

    @Test
    public void withLong()  {
        BTProviderEO.createLong();
    }

    @Test
    public void withFloat()  {
        BTProviderEO.createFloat();
    }

    @Test
    public void withFloatStandard()  {
        EO adapter = BTProviderEO.createFloat();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestEOProvider.create(new File(file));
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), fromJson.get(F_TEST_FLOAT));
    }

    @Test
    public void withDouble()  {
        BTProviderEO.createDouble();
    }

    @Test
    public void withDate()  {
        BTProviderEO.createDate();
    }

    @Test
    public void withBoolean()  {
        BTProviderEO.createBoolean();
    }

    @Test
    public void withMap()  {
        BTProviderEO.createMap();
    }

    @Test
    public void withList()  {
        BTProviderEO.createList();
    }

    @Test
    public void withBasicTest()  {
        BTProviderEO.createBT();
    }

    @Test
    public void withSubTest()  {
        BTProviderEO.createST();
    }

    @Test
    public void withSubTestMap()  {
        BTProviderEO.createMapST();
    }

    @Test
    public void withSubTestList()  {
        BTProviderEO.createListST();
    }

    @Test
    public void withSmall()  {
        BTProviderEO.createSmall();
    }

    @Test
    public void withSimple()  {
        BTProviderEO.createSimple();
    }

    @Test
    public void withAll()  {
        BTProviderEO.create();
    }


    @Test
    public void withSubTestMapAndSerializationTypePARAMS()  {
        EO adapter = TestEOProvider
                .create(BTProvider.createMapST());
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndentAndSerializationTypeSCALAR_TYPES()  {
        EO adapter = BTProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }
}
