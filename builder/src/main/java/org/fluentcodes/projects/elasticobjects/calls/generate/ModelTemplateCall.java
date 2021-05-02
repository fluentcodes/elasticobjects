package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;

/*=>{javaHeader}|*/
/**
 * Call for generation of java code from ModelConfig data. 
 *  * FieldConfig will be loaded from ConfigsCache. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:35:57 CET 2020
 */
public class ModelTemplateCall extends ModelAbstract {
/*=>{}.*/
    private final static String JAVA_GEN_MODEL = "/modelKey";
    public final static String FIELD_MAP = "fieldMap";

/*=>{javaStaticNames}|*/
/*=>{}.*/

/*=>{javaInstanceVars}|*/
/*=>{}.*/

    public ModelTemplateCall() {
        super();
    }

    public boolean filter (final ModelBean modelBean) {
        if (modelBean.getNaturalId()==null) {
            throw new EoException("Natural id is null!");
        }
        if (hasNaturalId() && !modelBean.getNaturalId().equals(getNaturalId())) {
            return false;
        }
        return super.filter(modelBean);
    }

    @Override
    public Object execute(EO eo) {
        FileConfig targetFileConfig = eo.getConfigsCache().findFile(getTargetFileConfigKey());
        if (!targetFileConfig.hasTemplate()) {
            throw new EoInternalException("No template defined for target file config key '" + getTargetFileConfigKey() + "'");
        }
        super.init(eo);
        StringBuilder feedback = new StringBuilder();
        this.setNaturalId(ParserSqareBracket.replacePathValues(getNaturalId(), eo));
        int counter = 1;
        for (final String naturalId: eo.keys()) {
            EO eoModel = eo.getEo(naturalId);
            if (!isModelBean(eoModel)) {
                continue;
            }
            ModelBean modelBean = getModelBean(eoModel);
            if (!filter(modelBean)) {
                continue;
            }
            final String modelKey = modelBean.getModelKey();
            eo.set(modelKey, JAVA_GEN_MODEL);
            create(eoModel, targetFileConfig);
            counter++;
        }
        if (counter==1) {
            throw new EoException("No java entry in ModelConfig found matching '" + getModule() + "', '" + getModuleScope() + "' and '" + getNaturalId() + "'.");
        }
        return feedback.toString();
    }


/*=>{javaAccessors}|*/
/*=>{}.*/


}
