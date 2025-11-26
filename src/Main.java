import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main class for running Marathi language interpreter from command line
 */
public class Main {
    public static void main(String[] args) {
        String filePath = "test.marathi";
        String input = readFile(filePath);

        if (input.isEmpty()) {
            System.out.println("Error: Could not read file or file is empty");
            return;
        }

        try {
            // Tokenization
            MarathiTokenizer tokenizer = new MarathiTokenizer(input);
            List<Token> tokens = tokenizer.tokenize();

            System.out.println("=== TOKENIZATION ===");
            tokens.forEach(System.out::println);

            // Parsing
            MarathiParser parser = new MarathiParser(tokens);
            ASTNode ast = parser.parse();

            System.out.println("\n=== ABSTRACT SYNTAX TREE ===");
            System.out.println(ast);

            // Interpretation
            System.out.println("\n=== PROGRAM OUTPUT ===");
            MarathiInterpreter interpreter = new MarathiInterpreter();
            interpreter.interpret(ast);
            interpreter.printFinalOutput();

        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return "";
        }
    }
}
