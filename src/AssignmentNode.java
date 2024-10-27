public class AssignmentNode implements ASTNode {
    private String variableName;
    private ASTNode value;

    public AssignmentNode(String variableName, ASTNode value) {
        this.variableName = variableName;
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }

    public ASTNode getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AssignmentNode{variableName='" + variableName + "', value=" + value + "}";
    }
}
