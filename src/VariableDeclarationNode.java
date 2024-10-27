public class VariableDeclarationNode implements ASTNode {
    private String variableName;
    private ASTNode value;

    public VariableDeclarationNode(String variableName, ASTNode value) {
        this.variableName = variableName;
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }

    public ASTNode getValue() {
        return value;
    }
}
