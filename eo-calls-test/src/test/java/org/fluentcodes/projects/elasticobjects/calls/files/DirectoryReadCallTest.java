package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitems.FileProvider;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_AN_OBJECT;
import static org.junit.Assert.assertEquals;

/**
 * @author Werner Diwischek
 * @since 7.10.2020.
 */
public class DirectoryReadCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return DirectoryReadCall.class;
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
    public void call_AnObjectAllStandard() {
        final DirectoryReadCall call = new DirectoryReadCall(FileProvider.TEST_DATA, FileProvider.ANOBJECT_ALL_STANDARD_JSON);
        final EoRoot root = ObjectProvider.createEo();
        call.setTargetPath("/test");
        call.execute(root);
        assertEquals(Map.class, root.getEo("test", MY_AN_OBJECT).getModelClass());
        assertEquals("test", root.get("test", MY_AN_OBJECT, F_MY_STRING));
    }

    @Test
    public void call_AnObjectAllTyped() {
        final DirectoryReadCall call = new DirectoryReadCall(FileProvider.TEST_DATA, FileProvider.ANOBJECT_ALL_TYPED_JSON);
        final EoRoot root = ObjectProvider.createEo();
        call.setTargetPath("/test");
        call.execute(root);
        assertEquals(AnObject.class, root.getEo("test", MY_AN_OBJECT).getModelClass());
        assertEquals("test", root.get("test", MY_AN_OBJECT, F_MY_STRING));
    }
}
