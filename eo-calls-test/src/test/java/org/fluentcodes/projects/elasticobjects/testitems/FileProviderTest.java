package org.fluentcodes.projects.elasticobjects.testitems;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_AN_OBJECT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_NATURAL_ID;
import static org.junit.Assert.assertEquals;

public class FileProviderTest {

    @Test
    public void readFromDataDirectory_AnObjectAllStandardJson() {
        EoChild dataEo = FileProvider.readFromDataDirectory(FileProvider.ANOBJECT_ALL_STANDARD_JSON);
        assertEquals("test", dataEo.get(MY_AN_OBJECT, F_MY_STRING));
        assertEquals(Map.class, dataEo.getEo(MY_AN_OBJECT).getModelClass());
    }

    @Test
    public void readFromDataDirectory_AnObjectAllTypedJson() {
        EoChild dataEo = FileProvider.readFromDataDirectory(FileProvider.ANOBJECT_ALL_TYPED_JSON);
        assertEquals("test", dataEo.get(F_NATURAL_ID));
        assertEquals("test", dataEo.get(MY_AN_OBJECT, F_MY_STRING));
        assertEquals(AnObject.class, dataEo.getModelClass());
        assertEquals(AnObject.class, dataEo.getEo(MY_AN_OBJECT).getModelClass());

    }
}
