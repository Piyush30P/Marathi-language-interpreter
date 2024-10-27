public class StringNode implements ASTNode {
    private String value;

    public StringNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringNode{" + "value='" + value + '\'' + '}';
    }
}
