public class ReturnStatementNode implements ASTNode {
    private ASTNode expression;

    public ReturnStatementNode(ASTNode expression) {
        this.expression = expression;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "ReturnStatementNode{" +
                "expression=" + expression +
                '}';
    }
}
