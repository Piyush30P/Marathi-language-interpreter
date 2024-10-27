public class PrintStatementNode implements ASTNode {
    private ASTNode expression;

    public PrintStatementNode(ASTNode expression) {
        this.expression = expression;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "PrintStatementNode{" + "expression=" + expression + '}';
    }
}
