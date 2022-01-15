package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;

public class InsertStatement extends PreparedStatementValues {

    public InsertStatement(String model, Map<String, Object> values) {
        super(SqlType.INSERT);
        StringBuilder builderFields = new StringBuilder("(");
        StringBuilder builderValues = new StringBuilder("(");
        for (String key : values.keySet()) {
            add(values.get(key));
            builderFields.append(key + ", ");
            builderValues.append("?, ");
        }
        append("INSERT INTO ");
        append(model);
        append(" ");
        append(builderFields.toString().replaceAll(", $", ") "));
        append(" VALUES ");
        append(builderValues.toString().replaceAll(", $", ") "));
    }

    public static InsertStatement of(EOInterfaceScalar source) {
        if (source == null) {
            throw new EoException("Null eo for delete");
        }
        if (!(source instanceof EoChild)) {
            throw new EoException("Model '" + source.getModelClass().getSimpleName() + "' is not a object");
        }
        return new InsertStatement(source.getModel().getModelKey(), ((EoChild) source).getKeyValues());
    }
}
