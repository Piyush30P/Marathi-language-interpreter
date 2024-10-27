import java.util.List;
public class FunctionCallNode implements ASTNode {
    private String functionName;
    private List<ASTNode> arguments;

    public FunctionCallNode(String functionName, List<ASTNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<ASTNode> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "FunctionCallNode{" +
                "functionName='" + functionName + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
