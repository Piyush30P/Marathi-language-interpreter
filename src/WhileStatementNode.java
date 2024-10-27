public class WhileStatementNode implements ASTNode {
    private ASTNode condition;
    private ASTNode body;

    public WhileStatementNode(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "WhileStatementNode{condition=" + condition + ", body=" + body + "}";
    }
}
