package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.generate.ModelAbstract.MODEL_BEANS_JSON;

/*=>{javaHeader}|*/

/**
 * Call for generation json configurations from the sheet.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:39:50 CET 2020
 */
public class GenerateModelBeansJsonCall extends GenerateAbstract implements GenerateAbstractInterface {
    /*=>{javaStaticNames}|*/
/*=>{}.*/

/*=>{javaInstanceVars}|*/
/*=>{}.*/
    public GenerateModelBeansJsonCall() {
        super();
        setTargetFileConfigKey(MODEL_BEANS_JSON);
    }

    public GenerateModelBeansJsonCall(final String sourceFileConfigKey) {
        super();
        setSourceFileConfigKey(sourceFileConfigKey);
        setTargetFileConfigKey(MODEL_BEANS_JSON);
    }

    @Override
    public String execute(EO eo) {
        long start = System.currentTimeMillis();
        init(eo);
        StringBuilder feedback = new StringBuilder();

        XlsxReadCall modelCall = new XlsxReadCall(getSourceFileConfigKey() + ":" + ModelConfig.class.getSimpleName());
        modelCall.setTargetPath(ModelConfig.class.getSimpleName() + "/=>[naturalId].");
        feedback.append(modelCall.execute(eo));

        XlsxReadCall fieldCall = new XlsxReadCall(getSourceFileConfigKey() + ":" + FieldConfig.class.getSimpleName());
        fieldCall.setTargetPath(FieldConfig.class.getSimpleName() + "/=>[naturalId].");
        feedback.append(fieldCall.execute(eo));

        Map<String, ModelBean> modelBeans = new LinkedHashMap<>();
        Map<String, Map> models = (Map<String, Map>)eo.get(ModelConfig.class.getSimpleName());
        for (String key: models.keySet()) {
            ModelBean modelBean = new ModelBean(models.get(key));
            modelBean.mergeFieldDefinition((Map<String, Map>)eo.get(FieldConfig.class.getSimpleName()));
            modelBeans.put(key, modelBean);
        }

        for (ModelBean modelBean: modelBeans.values()) {
            if (!modelBean.hasSuperKey()) {
                continue;
            }
            modelBean.resolveSuper(modelBeans, false);
        }
        FileWriteCall writeCall = new FileWriteCall(getTargetFileConfigKey());
        String content = new EOToJSON().toJson(eo.getConfigsCache(), modelBeans);
        //writeCall.setCompare(false);
        writeCall.setContent(content);
        feedback.append(writeCall.execute(eo));
        setDuration(System.currentTimeMillis()-start);
        return feedback.toString();
    }

/*=>{javaAccessors}|*/
/*=>{}.*/

}
