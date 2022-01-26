package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import java.util.List;

/**
 * @author Werner Diwischek
 * @since 7.10.2020
 */
public class DirectoryListReadCallTest implements IModelConfigCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return DirectoryListReadCall.class;
    }

    @Override
    @Test
    public void create_noEoException()  {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig()  {
        assertModelConfigEqualsPersisted();
    }
    @Override
    @Test
    public void compareBeanFromModelConfig()  {
        assertBeanFromModelConfigEqualsPersisted();
    }


    @Test
    public void dir_xpect_filter_AnObjectTest__read__notEmpty() {
        DirectoryListReadCall call = new DirectoryListReadCall();
        List<String> result = call.listFiles("Xpect","CallTest", false);
        Assertions.assertThat(result.get(0)).isEqualTo("CallTest");
    }

    @Test
    public void fileConfigKey_XPECT_TEST__read__notEmpty() {
        DirectoryListReadCall call = new DirectoryListReadCall("XPECT_TEST");
        List<String> result = (List<String>)call.execute(ObjectProvider.createEo());
        Assertions.assertThat(result).contains("compareBeanFromModelConfig.json");
    }
}
