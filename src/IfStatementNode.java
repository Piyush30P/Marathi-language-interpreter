// IfStatementNode.java
public class IfStatementNode implements ASTNode {
    private ASTNode condition;
    private ASTNode thenBranch;
    private ASTNode elseBranch;

    public IfStatementNode(ASTNode condition, ASTNode thenBranch, ASTNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getThenBranch() {
        return thenBranch;
    }

    public ASTNode getElseBranch() {
        return elseBranch;
    }

    @Override
    public String toString() {
        return "IfStatementNode{" + "condition=" + condition + ", thenBranch=" + thenBranch + ", elseBranch="
                + elseBranch + '}';
    }
}