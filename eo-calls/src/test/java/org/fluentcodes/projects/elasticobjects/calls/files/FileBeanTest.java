package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.files.FileInterface.F_FILE_NAME;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileBeanTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return FileBean.class;
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
    public void getSetFileName() {
        assertSetGet(F_FILE_NAME, "test");
    }

    @Test
    public void getNaturalId() {
        assertSetGet(F_NATURAL_ID, "test");
    }

    @Test
    public void getSetModule() {
        assertSetGet(F_MODULE, "test");
    }

    @Test
    public void getSetPermissionRole() {
        List<String> readPermission = new ArrayList<>();
        FileBean fileBean = (FileBean) assertSetGet(PermissionType.READ.getFieldKey(), readPermission);
        Assertions.assertThat(fileBean.getRead()).isEqualTo(readPermission);
    }

    @Test
    public void createByElement() {
        final String serialized = "{    \"FileBean\": {\n" +
                "    \"module\": \"eo-calls\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": \"cached, fileName, filePath, hostConfigKey\",\n" +
                "    \"interfaces\": \"FileInterface\",\n" +
                "    \"superKey\": \"PermissionBean\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"The bean counterpart for @FileConfig.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.calls.files\",\n" +
                "    \"modelKey\": \"FileBean\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": true,\n" +
                "      \"final\": false,\n" +
                "      \"override\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1608073200000\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(FileBean.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(FileBean.class.getSimpleName(), bean.getModelKey());
        assertEquals(FileBean.class.getSimpleName(), bean.getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.BEAN, bean.getShapeType());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }

}
