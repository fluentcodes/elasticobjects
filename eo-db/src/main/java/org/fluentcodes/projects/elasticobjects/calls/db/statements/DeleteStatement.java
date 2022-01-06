package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;

import java.util.Map;

public class DeleteStatement extends PreparedStatementValues {

    public DeleteStatement(String model, Map<String, Object> values) {
        super(SqlType.DELETE);
        StringBuilder builderValues = new StringBuilder("");
        for (String key : values.keySet()) {
            add(values.get(key));
            builderValues.append(key + " = ? AND ");
        }
        append("DELETE ");
        append(model);
        append(" WHERE ");
        append(builderValues.toString().replaceAll(" AND $", " "));
    }

    public static DeleteStatement of(IEOScalar source) {
        if (source == null) {
            throw new EoException("Null eo for delete");
        }
        ModelConfig model = source.getModel();
        if (!model.isObject()) {
            throw new EoException("Model '" + model.getModelKey() + "' is not a object");
        }
        return new DeleteStatement(model.getModelKey(), ((EoChild) source).getKeyValues());
    }
}
