public class VariableReferenceNode implements ASTNode {
    private String variableName;

    public VariableReferenceNode(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public String toString() {
        return "VariableReferenceNode{" + "variableName='" + variableName + '\'' + '}';
    }
}
