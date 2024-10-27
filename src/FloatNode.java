public class FloatNode implements ASTNode {
    private String value;

    public FloatNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
