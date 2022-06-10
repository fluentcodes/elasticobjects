package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import static org.fluentcodes.projects.elasticobjects.testitems.FileProvider.TEST_RESOURCES_DATA_DIR;
import static org.junit.Assert.assertEquals;

import com.sun.tools.javac.util.List;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.FileProvider;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderArray;
import org.junit.Test;

public class XlsxWriteCallTest implements IModelConfigCreateTests {

    private static final String LIST_SIMPLE_CSV = "ListSimple.csv";

    @Override
    public Class<?> getModelConfigClass() {
        return XlsxWriteCall.class;
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
    public void OutputCsv() {
        final XlsxWriteCall call = new XlsxWriteCall(TEST_RESOURCES_DATA_DIR,"Output.xlsx");

        EoRoot eo = ObjectProvider.createEo(ObjectProviderArray.ARRAY_SIMPLE);
        call.execute(eo);
        assertEquals("val11;val12\n" +
            "val21;val22\n", call.getFileWriteCall().getContent());
    }

    @Test
    public void OutputCsvDir() {
        final XlsxWriteCall call = new XlsxWriteCall("data_csv");
        call.setFileName("dir.csv");
        List<List<String>> array = List.of(
            List.of("val11", "val12"),
            List.of("val21", "val22")
        );
        EoRoot eo = ObjectProvider.createEo(array);

        call.execute(eo);
        assertEquals("val11;val12;\n" +
            "val21;val22;\n", call.getFileWriteCall().getContent());
    }

    @Test
    public void OutputCsvDir_ListAnObject() {
        EO eo = FileProvider.readAnObjectJson();
        final XlsxWriteCall call = new XlsxWriteCall("data_csv");
        call.setFileName("AnObjectList.csv");
        call.getListParams().setRowHead(0);
        call.execute(eo);
        assertEquals(
            "id;myASubObject;myASubObjectList;myASubObjectMap;myASubObject_id;myAnObject;myAnObject_id;myBoolean;myDate;myDouble;myFloat;myInt;myList;myLong;myMap;myObject;myString;naturalId\n" +
                ";;;;;;;;;;;1;;;;;test;;\n" +
                ";;;;;;;;;;;2;;;;;testOther;;\n", call.getFileWriteCall().getContent());
    }
}
