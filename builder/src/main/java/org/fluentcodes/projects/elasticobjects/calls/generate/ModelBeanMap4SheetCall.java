package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.ModelBeanGen;
import org.fluentcodes.projects.elasticobjects.models.ModelBeanMap4Sheet;
import org.fluentcodes.projects.elasticobjects.models.Scope;

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
public class ModelBeanMap4SheetCall extends GenerateAbstract implements GenerateAbstractInterface {
    /*=>{javaStaticNames}|*/
/*=>{}.*/

/*=>{javaInstanceVars}|*/
/*=>{}.*/
    public ModelBeanMap4SheetCall() {
        super();
        setTargetFileConfigKey(MODEL_BEANS_JSON);
    }

    public ModelBeanMap4SheetCall(final String sourceFileConfigKey) {
        super();
        setSourceFileConfigKey(sourceFileConfigKey);
        setTargetFileConfigKey(MODEL_BEANS_JSON);
    }

    @Override
    public String execute(final EO eo) {
        return read(getSourceFileConfigKey(), eo);
    }

    public static String read(final String sourceFileConfigKey, final EO eo) {
        StringBuilder feedback = new StringBuilder();
        Map<String, ModelBeanGen> modelMap = readModelBeanMap(sourceFileConfigKey, eo.getScope());
        eo.mapObject(modelMap);
        return "Readed '" + sourceFileConfigKey + "' with " + modelMap.size() + " entries.";
    }

    public static Map<String, ModelBeanGen> readModelBeanMap(final String sourceFileConfigKey, Scope scope) {
        ModelBeanMap4Sheet modelMap = new ModelBeanMap4Sheet(scope, ModelBeanGen.class);
        modelMap.readSheet(sourceFileConfigKey);
        return modelMap.getBeanMap();
    }

    public static ModelBeanMap4Sheet read(final String sourceFileConfigKey, Scope scope) {
        ModelBeanMap4Sheet modelMap = new ModelBeanMap4Sheet(scope, ModelBeanGen.class);
        modelMap.readSheet(sourceFileConfigKey);
        return modelMap;
    }

/*=>{javaAccessors}|*/
/*=>{}.*/

}
