public class InputNode implements ASTNode {
    private String prompt;

    public InputNode(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    @Override
    public String toString() {
        return "InputNode{prompt='" + prompt + "'}";
    }
}
