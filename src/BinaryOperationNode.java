public class BinaryOperationNode implements ASTNode {
    private ASTNode left;
    private String operator;
    private ASTNode right;

    public BinaryOperationNode(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ASTNode getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public ASTNode getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "BinaryOperationNode{" + "left=" + left + ", operator='" + operator + '\'' + ", right=" + right + '}';
    }
}
