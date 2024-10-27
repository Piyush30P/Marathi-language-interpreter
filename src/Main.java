import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String filePath = "test.marathi";

        // String fileContent = readFile(filePath);

        // String filePath = "test.marathi";
        String input = readFile(filePath);

        MarathiTokenizer tokenizer = new MarathiTokenizer(input);
        List<Token> tokens = tokenizer.tokenize();

        System.out.println("Tokens:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        MarathiParser parser = new MarathiParser(tokens);
        ASTNode ast = parser.parse();

        System.out.println("AST:");
        System.out.println(ast);

        MarathiInterpreter interpreter = new MarathiInterpreter();
        interpreter.interpret(ast);
        // Print all accumulated output at the end
        interpreter.printFinalOutput();
    }

    private static String readFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
