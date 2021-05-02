package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperFirstCharCall;

public interface FieldBeanInterface4Java extends FieldBeanInterface {

    default String getJavaFieldType() {
        String[] models = getModelKeys().split(",");
        if (models.length == 2) {
            if (models[0].endsWith("Map")) {
                return "Map<String, " + models[1] + ">";
            }
            else if (models[0].endsWith("List")) {
                return "List<" + models[1] + ">";
            }
            else {
                return models[0];
            }
        }
        else {
            return models[0];
        }
    }

    default String getJavaAccess4Bean() {
        if (isSuper()) {
            return "";
        }
        return getJavaGet4Bean() + getJavaSet4Bean();
    }

    default String getJavaAccess4Interface() {
        if (isSuper()) {
            return "";
        }
        return getJavaGet4Interface() + getJavaSet4Interface();
    }

    default String getter() {
        return "get" + getUpperFirstCharFieldKey() + "()";
    }

    default String getJavaGet4Bean() {
        if (isSuper()) {
            return "";
        }
        return  getJavaOverrideAnnotation() +
                "   public " + getJavaFieldType() + " " + getter() + " {\n" +
                "      return this." + getFieldKey() + ";\n" +
                "   }\n" + getJavaHas4Bean();
    }

    default String getJavaGet4Interface() {
        if (isSuper()) {
            return "";
        }
        if (hasFinal() && !getFinal()) {
            return "";
        }
        if (isProperty()) {
            return "   default " + getJavaFieldType() + " get" + getUpperFirstCharFieldKey() + "(){\n" +
                    "      return (" + getModelKeys() + ") getProperties().get(" + getUpperFieldKey() + ");\n" +
                    "   }\n" + getJavaHas4Interface();
        }
        else {
            return "   " + getJavaFieldType() + " get" + getUpperFirstCharFieldKey() + "();\n" + getJavaHas4Interface();
        }
    }

    default String getJavaSet4Bean() {
        if (isSuper()) {
            return "";
        }
        if (isFinal()) {
            return "";
        }
        StringBuilder builder = new StringBuilder(getJavaOverrideAnnotation() );
        builder.append(
                "   public " + getParentModel().getModelKey() + " set" + getUpperFirstCharFieldKey() + "(final " + getJavaFieldType() + " " + getFieldKey() + ") {\n" +
                "      this." + getFieldKey() + " = " + getFieldKey() + ";\n" +
                "      return this;\n " +
                "   }\n\n"
        );
        return builder.toString();
    }

    default String getJavaSet4Interface() {
        if (isSuper()) {
            return "";
        }
        if (hasFinal() && getFinal()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        if (isProperty()) {
            return "   default " + getParentModel().getModelKey() + " set" + getUpperFirstCharFieldKey() + "(" + getJavaFieldType() + " value) {\n" +
                    "      getProperties().put(" + getUpperFieldKey() + ", value);\n" +
                    "      return this;\n" +
                    "   }\n" + getJavaMerge();
        }
        else {
            builder.append(
                    "   " + getParentModel().getModelKey() + " set" + getUpperFirstCharFieldKey() + "(final " + getJavaFieldType() + " " + getFieldKey() + ");\n"
            );
            builder.append(getJavaMerge());
        }

        return builder.toString();
    }

    default String getJavaMerge() {
        StringBuilder builder = new StringBuilder("   default void merge");
        builder.append(getUpperFirstCharFieldKey());
        builder.append("(final Object value) {\n" +
                "      if (value == null) return;\n" +
                "      if (has");
        builder.append(getUpperFirstCharFieldKey());
        builder.append("()) return;\n" +
                "      set");
        builder.append(getUpperFirstCharFieldKey());
        builder.append("(ScalarConverter.to");
        builder.append(getModelKeys());
        builder.append("(value));\n" +
                "   }\n\n");
        return builder.toString();
    }


    default String getUpperFirstCharFieldKey() {
        return StringUpperFirstCharCall.upper(getFieldKey());
    }

    default String getUpperFieldKey() {
        return StringUpperCall.upper(getFieldKey());
    }

    default boolean hasEmptyMethod() {
        return getModelKeys().matches(".*(Map|List|String).*");
    }
    default String getJavaDescription() {
        if (hasSuper()) {
            return "";
        }
        return "/*\n " + ModelBeanGen.replaceLinks(getDescription()) + "\n  */";
    }

    default String getJavaHas4Interface() {
        return getJavaHas("default");
    }

    default String getJavaHas(final String type) {
        if (isSuper()) {
            return "";
        }
        if (isOverride()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("   " + type + " boolean has" + getUpperFirstCharFieldKey() + "() {\n");
        if (isProperty()) {
            builder.append("      return getProperties().containsKey(" + getUpperFieldKey() + ") && getProperties().get(" + getUpperFieldKey() + ") != null");
        }
        else {
            builder.append("      return " + getter() + " != null");
            builder.append(hasEmptyMethod() ? " && !" + getter() + ".isEmpty()" : "");
        }
        builder.append(";\n   }\n");
        if ("Boolean".equals(getModelKeys())) {
            builder.append("   " + type + " boolean is" + getUpperFirstCharFieldKey() + "() {\n");
            builder.append("      return has" + getUpperFirstCharFieldKey()  + "() && get" + getUpperFirstCharFieldKey()  + "()");
            builder.append(";\n   }\n\n");
        }
        return builder.toString();
    }

    default String getJavaHas4Bean() {
        return getJavaHas("public");
    }

    default String createFinal() {
        if (!isFinal()) {
            return "";
        }
        return "final ";
    }

    default String getJavaBeanConstructor() {
        if (isSuper()) {
            return "";
        }
        return ("      this." + getFieldKey() + " = bean." + getter() + ";\n");
    }

    default String getJavaInstanceVar() {
        if (isSuper()) {
            return "";
        }
        StringBuilder builder = new StringBuilder("   /* ");
        builder.append(ModelBeanGen.replaceLinks(getDescription()));
        builder.append(" */\n");
        if (getParentModel().isDbAnnotated()) {
            builder.append(createAnnotationNotNull());
            builder.append(createAnnotationSize());
            builder.append(createAnnotationDb());
        }
        builder.append("   private ");
        builder.append(createFinal());
        builder.append(getJavaFieldType());
        builder.append(" ");
        builder.append(getFieldKey());
        builder.append(";\n");
        return builder.toString();
    }

    default String getJavaStaticName() {
        if (isSuper())  return "";
        if (isOverride()) return "";
        if (isStaticName())  return "";

        StringBuilder builder = new StringBuilder("   public static final String ");
        builder.append(getUpperFieldKey());
        builder.append(" = \"");
        builder.append(getFieldKey());
        builder.append("\";\n");
        return builder.toString();
    }
    default String getJavaStaticName4Interface() {
        if (isSuper())  return "";
        if (isOverride()) return "";
        if (isStaticName())  return "";

        StringBuilder builder = new StringBuilder("   String ");
        builder.append(getUpperFieldKey());
        builder.append(" = \"");
        builder.append(getFieldKey());
        builder.append("\";\n");
        return builder.toString();
    }

    default String createAnnotationNotNull() {
        if (!hasParentModel()) return "";
        if (!getParentModel().isDbAnnotated())  return "";
        return "   @NotNull\n";
    }

    default String createAnnotationSize() {
        if (!hasParentModel()) return "";
        if (!getParentModel().isDbAnnotated()) return "";
        if (!hasSize()) return "";

        StringBuilder builder = new StringBuilder("@Size(");
        if (hasMin()) {
            builder.append("min=");
            builder.append(getMin());
            builder.append(", ");
        }
        if (hasMin()) {
            builder.append("max=");
            builder.append(getMax());
            builder.append(", ");
        }
        return builder.toString().replaceAll(", ", ")\n");
    }

    default String createAnnotationDb() {
        if (!hasParentModel()) return "";
        if (!getParentModel().isDbAnnotated()) return "";
        if (isTransient()) {
            return "@Transient";
        }
        if (isGenerated()) {
            return "  @Id\n   @GeneratedValue(strategy = GenerationType.IDENTITY)\n";
        }
        StringBuilder builder = new StringBuilder("   @Column(");
        if (hasFieldName()) {
            builder.append("name = \"");
            builder.append(getFieldName());
            builder.append("\", ");
        }
        if (isUnique()) {
            builder.append("unique = true, ");
        }
        if (isNotNull()) {
            builder.append("nullable = false, ");
        }
        if (String.class.getSimpleName().equals(getModelKeys())) {
            if (hasLength()) {
                builder.append(" length = ");
                builder.append(getLength());
                builder.append(", ");
            }
        }
        return builder.toString().replaceAll(", $",")\n");

    }
    default String getJavaOverrideAnnotation() {
        return isOverride()?
                "   @Override\n":
                "";
    }


}
