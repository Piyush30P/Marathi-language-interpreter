public class ConditionNode implements ASTNode {
    private String variableName;
    private String operator;
    private String value;

    public ConditionNode(String variableName, String operator, String value) {
        this.variableName = variableName;
        this.operator = operator;
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Condition:\n  Variable Name: " + variableName + "\n  Operator: " + operator + "\n  Value: " + value;
    }
}
