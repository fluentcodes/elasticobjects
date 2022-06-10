package org.fluentcodes.projects.elasticobjects.calls.db;

import java.util.List;
import java.util.Map;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBeanInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Read an entries in database by creating a select sql from entry in sourcePath.
 */
public class DbModelReadCall extends ListCall implements ConfigReadCommand {
    private String dbModelsConfigKey;

    DbModelsConfig dbModelsConfig;
    DbModelConfig dbModelConfig;

    public DbModelReadCall() {
        super();
    }

    public DbModelReadCall(final String dbModelsConfigKey) {
        this();
        this.dbModelsConfigKey= dbModelsConfigKey;
    }

    public String getDbModelsConfigKey() {
        return dbModelsConfigKey;
    }

    public DbModelReadCall setDbModelsConfigKey(String dbModelsConfigKey) {
        this.dbModelsConfigKey = dbModelsConfigKey;
        return this;
    }

    public boolean hasDbModelsConfigKey() {
        return dbModelsConfigKey!=null && !dbModelsConfigKey.isEmpty();
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        if (!(eo instanceof EoChild)) {
            throw new EoException("Could not query scalar value");
        }
        if (!hasDbModelsConfigKey()) {
            throw new EoException("No db models config key set!");
        }
        dbModelsConfig = (DbModelsConfig) eo.getConfigMaps().find(DbModelsConfig.class, dbModelsConfigKey);
        Class<?> modelClass = deriveModelClass(eo);
        dbModelConfig = dbModelsConfig.getDbModelConfig(modelClass);
        getListParams().initDb();
        return readRaw((EoChild) eo);
    }

    public Object readRaw(final EoChild eo) {
        StatementFind findStatement =
            dbModelsConfig.getDbModelConfig(deriveModelClass(eo)).createQueryStatement(eo);
        if (!hasTargetPath()) {
            if (eo.isObject()) {
                readOne(eo, findStatement);
            } else {
                readMulti(eo, findStatement);
            }
        } else {
            EOInterfaceScalar targetEo = eo.createChild(getTargetPath(), null);
            readToTarget(targetEo, findStatement);
        }
        return "";
    }

    private void readToTarget(EOInterfaceScalar targetEo, StatementFind findStatement) {
        List<Map<String, Object>> dbResult = findStatement
            .read(
                dbModelsConfig.getDbConfig().getConnection(),
                targetEo.getConfigMaps(),
                getListParams());
        if (dbResult == null || dbResult.isEmpty()) {
            throw new EoException("Could not find entry for " + findStatement.toString());
        }
        for (int i = 0; i < dbResult.size(); i++) {
            Map<String, Object> entry = dbResult.get(i);
            String key = Integer.valueOf(i).toString();
            targetEo.set(entry, key);
        }
    }

    private void readOne(EoChild eo, StatementFind findStatement) {
        Map<String, Object> dbResult = findStatement
            .readOneOrEmpty(
                dbModelsConfig.getDbConfig().getConnection(),
                eo.getConfigMaps());
        if (dbResult == null || dbResult.isEmpty()) {
            throw new EoException("Could not find entry for " + findStatement.toString());
        }
        eo.map(dbResult);
    }

    private void readMulti(EoChild eo, StatementFind findStatement) {
        for (String key : eo.keys()) {
            readOne((EoChild) eo.getEo(key), findStatement);
        }
    }

    private Class deriveModelClass(EOInterfaceScalar eo) {
        if (eo.isObject()) {
            return eo.getModelClass();
        }
        if (!eo.getModels().hasChildModel()) {
            throw new EoException("Could not derive childModel for " + eo.getModels());
        }
        ModelConfig childModel = eo.getModels().getChildModel();
        if (!childModel.isObject()) {
            throw new EoException("ChildModle is not an object but " + childModel.getModelClass().getSimpleName());
        }
        return childModel.getModelClass();
    }

    @Override
    public void setConfigKey(String configKey) {

    }
}
