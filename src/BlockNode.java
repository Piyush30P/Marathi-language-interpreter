
// BlockNode.java
import java.util.List;

public class BlockNode implements ASTNode {
    private List<ASTNode> statements;

    public BlockNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    public List<ASTNode> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "BlockNode{" + "statements=" + statements + '}';
    }
}
