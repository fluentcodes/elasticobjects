package org.fluentcodes.projects.elasticobjects.calls.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;


/**
 * Configuration for model based database sql queries.
 */
public class DbModelConfig extends PermissionConfig implements DbModelInterface {
    private final List<String> fieldKeys;
    private final String dropStatement;
    private final List<String> foreignKeys;
    private final List<String> uniqueList;
    private final String createStatement;
    private final String modelKey;
    private final ModelConfig modelConfig;

    public DbModelConfig(final ConfigBean configBean, final ConfigMaps configMaps) {
        this((DbModelBean) configBean, configMaps);
    }

    public DbModelConfig(final DbModelBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);

        this.modelKey = bean.getModelKey();
        modelConfig = configMaps.findModel(modelKey);

        this.fieldKeys = new ArrayList<>();

        StringBuilder create = new StringBuilder("Create table ");
        StringBuilder drop = new StringBuilder();
        create.append(getTable());
        create.append(" (");
        this.uniqueList = new ArrayList<>();
        this.foreignKeys = new ArrayList<>();
        String mariadbId = "";
        for (String fieldKey : modelConfig.getFieldKeys()) {
            FieldConfig fieldConfig = modelConfig.getField(fieldKey);
            if (fieldConfig.getProperties().hasIdKey()) {
                foreignKeys.add(createForeignConstraint(modelKey, fieldConfig));
            }
            if (fieldConfig.isTransient()) {
                continue;
            }
            create.append(fieldConfig.getFieldName());
            if (fieldKey.equals(modelConfig.getIdKey())) {
                create.append(" MEDIUMINT NOT NULL AUTO_INCREMENT ");
                mariadbId = " primary key (id) ";
            } else {
                create.append(" ");
                create.append(fieldConfig.getSqlType());
            }
            fieldKeys.add(fieldKey);
            if (fieldConfig.getProperties().isNotNull()) {
                create.append(" not null");
            }
            if (fieldConfig.getProperties().isUnique()) {
                uniqueList.add(createUniqueConstraint(modelKey, fieldConfig));
            }
            create.append(", ");
        }
        drop.append("Drop table  if exists " + getTable());
        this.dropStatement = drop.toString();
        if (!mariadbId.isEmpty()) {
            String replace = ", " + mariadbId + ")";
            this.createStatement = create.toString().replaceAll(", $", replace);
        }
        else {
            this.createStatement = create.toString().replaceAll(", $", ")");
        }
    }

    public boolean hasForeignKeys() {
        return !foreignKeys.isEmpty();
    }

    public List<String> getForeignKeys() {
        return foreignKeys;
    }

    public boolean hasUniqueList() {
        return !uniqueList.isEmpty();
    }

    public List<String> getUniqueList() {
        return uniqueList;
    }

    public String getCreateStatement() {
        return createStatement;
    }

    public String getDropStatement() {
        return dropStatement;
    }

    static String createUniqueConstraint(final String tableName, FieldConfig fieldConfig) {
        StringBuilder constraint = new StringBuilder("Alter table " + tableName + " add ");
        constraint.append("unique   (");
        constraint.append(fieldConfig.getFieldName());
        constraint.append(")");
        return constraint.toString();
    }

    static String createUniqueConstraint(final String tableName, List<String> uniqueList) {
        StringBuilder constraint = new StringBuilder("Alter table " + tableName + " add constraint UC ");
        constraint.append("unique   (");
        constraint.append(String.join(",", uniqueList));
        constraint.append(")");
        return constraint.toString();
    }

    static String createForeignConstraint(final String tableName, FieldConfig fieldConfig) {
        StringBuilder constraint = new StringBuilder("Alter table " + tableName + " add constraint ");
        constraint.append(tableName);
        constraint.append("_");
        constraint.append(fieldConfig.getFieldName());
        constraint.append("_id_FK foreign key ");
        constraint.append("(");
        constraint.append(fieldConfig.getFieldName());
        constraint.append("_id) references ");
        constraint.append(fieldConfig.getModelClass().getSimpleName());
        constraint.append("(");
        constraint.append(fieldConfig.getModels().getModel().getIdKey());
        constraint.append(")");
        return constraint.toString();
    }

    static String dropForeignConstraint(final String tableName, FieldConfig fieldConfig) {
        StringBuilder constraint = new StringBuilder("Alter table " + tableName + " drop constraint ");
        constraint.append(tableName);
        constraint.append("_");
        constraint.append(fieldConfig.getFieldName());
        constraint.append("_id_FK");
        return constraint.toString();
    }

    String getIdValue() {
        if (modelConfig.getProperties().hasIdKey()) {
            return modelConfig.getProperties().getIdKey();
        }
        return "id";
    }

    String getNaturalKeys() {
        if (modelConfig.getProperties().hasNaturalKeys()) {
            return modelConfig.getProperties().getNaturalKeys();
        }
        return "naturalId";
    }

    boolean hasIdKey(EoChild eo) {
        return eo.hasEo(getIdValue());
    }

    Object getIdValue(EoChild eo) {
        return eo.get(getIdValue());
    }

    Object[] getNaturalKeyValues(EoChild eo) {
        String[] naturalKeys = getNaturalKeys().split(",");
        Object[] result = new Object[naturalKeys.length];
        for (int i = 0; i < naturalKeys.length; i++) {
            result[i] = eo.get(naturalKeys[i]);
        }
        return result;
    }

    List<Object> getFieldValues(EoChild eo) {
        List<Object> values = new ArrayList<>();
        for (String key : fieldKeys) {
            if (eo.hasEo(key)) {
                values.add(eo.get(key));
            } else {
                values.add(null);
            }
        }
        return values;
    }

    boolean hasNaturalKeys() {
        return modelConfig.getProperties().hasNaturalKeys() || modelConfig.hasNaturalId();
    }

    void addId(EoChild eo, StatementPreparedValues statement) {
        statement.append(" where " + getIdValue() + " = ? ");
        statement.addValue(getIdValue(eo));
    }

    void addNaturalIds(EoChild eo, StatementPreparedValues statement) {
        StringBuilder builder = new StringBuilder(" where ");
        String[] naturalKeys = getNaturalKeys().split(",");
        for (String naturalKey : naturalKeys) {
            if (eo.hasEo(naturalKey)) {
                statement.addValue(eo.get(naturalKey));
            }
            else {
                throw new EoException("Add naturalIds... No value defined for " + naturalKey + ".");
            }
            builder.append(naturalKey);
            builder.append(" = ? and ");
        }
        statement.append(builder.toString()
            .replaceAll(" and $", ""));
    }

    private void addIdentifier(EoChild eo, StatementPreparedValues statement) {
        if (hasIdKey(eo)) {
            addId(eo, statement);
        } else if (hasNaturalKeys()) {
            addNaturalIds(eo, statement);
        } else {
            throw new EoException("Problem create identifier where statement");
        }
    }

    private String getTable() {
        if (modelConfig.getProperties().hasTable()) {
            return modelConfig.getProperties().getTable();
        }
        return modelKey;
    }

    @Override
    public String getModelKey() {
        return modelKey;
    }

    public ModelConfig getModelConfig() {
        return modelConfig;
    }

    public Map<String, Object> find(Connection connection, EoChild eo) {
        StatementFind statement = createFindStatement(eo);
        return statement.readFirst(connection, eo.getConfigMaps());
    }

    public Map<String, Object> readOneOrEmpty(Connection connection, EoChild eo) {
        StatementFind statement = createFindStatement(eo);
        return statement.readOneOrEmpty(connection, eo.getConfigMaps());
    }



    public StatementFind createFindStatement(EoChild eo) {
        StatementFind statement = new StatementFind();
        statement.append("Select * from ");
        statement.append(getTable());
        addIdentifier(eo, statement);
        return statement;
    }

    public List<Map<String, Object>> query(final Connection connection, final EoChild eo,
                                           final ListParamsBean listParams) {
        StatementFind statement = createQueryStatement(eo);
        return statement.read(connection, eo.getConfigMaps(), listParams);
    }

    public StatementFind createQueryStatement(final EoChild eo) {
        StatementFind statement = new StatementFind();
        statement.append("Select * from ");
        statement.append(getTable());
        statement.append(" where 1=1 ");
        for (String key : fieldKeys) {
            if (!eo.hasEo(key)) {
                continue;
            }
            if (eo.getEo(key).isEmpty()) {
                continue;
            }
            statement.append(" and ");
            statement.append(key);
            statement.append(" = ?");
            statement.addValue(eo.get(key));
        }
        return statement;
    }

    public Map<String, Object> write(final Connection connection, final EoChild eo) {
        StatementFind findStatement = createFindStatement(eo);
        try {
            if (findStatement.exists(connection)) {
                StatementPreparedValues statement = createUpdateStatement(eo);
                statement.execute(connection);
            } else {
                StatementPreparedValues statement = createInsertStatement(eo);
                statement.execute(connection);
            }
        } catch (EoException e) {
            eo.error(e.getMessage());
            throw e;
        }
        return readOneOrEmpty(connection, eo);
    }

    public StatementPreparedValues createUpdateStatement(EoChild eo) {
        StatementPreparedValues statement = new StatementPreparedValues(StatementPreparedValues.SqlType.UPDATE);
        StringBuilder update = new StringBuilder("Update ");
        update.append(getTable());
        update.append(" set ");
        for (String fieldKey : modelConfig.getFieldKeys()) {
            FieldConfig fieldConfig = modelConfig.getField(fieldKey);
            if (fieldConfig.isTransient()) {
                continue;
            }
            if (!eo.hasEo(fieldKey)) {
                continue;
            }
            if (fieldConfig.getModels().isObject()) {
                update.append(fieldKey + "_id");
            } else if (fieldConfig.getModels().isContainer()) {
                continue;
            } else {
                update.append(fieldConfig.getFieldName());
            }
            statement.addValue(eo.get(fieldKey));
            update.append(" = ?, ");
        }
        statement.append(update.toString().replaceAll(", $", ""));
        addIdentifier(eo, statement);
        return statement;
    }

    public StatementPreparedValues createInsertStatement(EoChild eo) {
        StatementPreparedValues statement = new StatementPreparedValues(StatementPreparedValues.SqlType.INSERT);

        StringBuilder insert = new StringBuilder("Insert into ");
        insert.append(getTable());
        insert.append(" (");

        StringBuilder insertValues = new StringBuilder(" values(");

        for (String fieldKey : modelConfig.getFieldKeys()) {
            FieldConfig fieldConfig = modelConfig.getField(fieldKey);
            if ("id".equals(fieldKey)) {
                //insert.append("id, ");
                //insertValues.append("?, ");
                //statement.addValue(-1);
                continue;
            }
            if (fieldConfig.isTransient()) {
                continue;
            }
            if (!eo.hasEo(fieldKey)) {
                continue;
            }

            if (fieldConfig.getModels().isObject()) {
                insert.append(fieldKey + "_id");
            } else if (fieldConfig.getModels().isContainer()) {
                continue;
            } else {
                insert.append(fieldConfig.getFieldName());
            }
            insert.append(", ");
            insertValues.append("?, ");
            statement.addValue(eo.get(fieldKey));
        }
        statement.append(insert.toString().replaceAll(", $", ")") +
            insertValues.toString().replaceAll(", $", ")"));
        return statement;
    }

    public Map<String, Object> delete(Connection connection, EoChild eo) {
        StatementFind findStatement = createFindStatement(eo);
        Map<String, Object> toDelete = findStatement.readFirst(connection, eo.getConfigMaps());
        if (toDelete == null) {
            throw new EoException("Could not find entry to delete for '" + eo.getModelClass().getSimpleName() + "':" +
                findStatement.toString());
        }
        StatementPreparedValues statement = createDeleteStatement(eo);
        statement.execute(connection);
        eo.map(toDelete);
        return toDelete;
    }

    public StatementPreparedValues createDeleteStatement(EoChild eo) {
        StatementPreparedValues statement = new StatementPreparedValues(StatementPreparedValues.SqlType.DELETE);
        StringBuilder delete = new StringBuilder("DELETE from ");
        delete.append(getTable());
        statement.append(delete.toString().replaceAll(", $", ""));
        addIdentifier(eo, statement);
        return statement;
    }
}
