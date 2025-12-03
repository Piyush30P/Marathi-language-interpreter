import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        String fileName = "test_new_features.marathi";
        if (args.length > 0) {
            fileName = args[0];
        }

        try {
            // Read the file content
            String code = new String(Files.readAllBytes(Paths.get(fileName)));

            System.out.println("==========================================");
            System.out.println("Running Marathi Interpreter Test");
            System.out.println("File: " + fileName);
            System.out.println("==========================================\n");

            // Tokenize the code
            MarathiTokenizer tokenizer = new MarathiTokenizer(code);
            List<Token> tokens = tokenizer.tokenize();

            // Parse the tokens into an AST
            MarathiParser parser = new MarathiParser(tokens);
            ASTNode ast = parser.parse();

            // Interpret the AST
            MarathiInterpreter interpreter = new MarathiInterpreter();
            interpreter.interpret(ast);

            // Print the final output
            System.out.println("\n==========================================");
            interpreter.printFinalOutput();
            System.out.println("==========================================");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
