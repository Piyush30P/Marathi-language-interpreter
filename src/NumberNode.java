public class NumberNode implements ASTNode {
    private String value;

    public NumberNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NumberNode{" + "value='" + value + '\'' + '}';
    }
}
