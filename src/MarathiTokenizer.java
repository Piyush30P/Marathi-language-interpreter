import java.util.ArrayList;
import java.util.List;

public class MarathiTokenizer {
    private String input;
    private int position;

    public MarathiTokenizer(String input) {
        this.input = input;
        this.position = 0;
    }

    public Token nextToken() {
        skipWhitespace();

        if (position >= input.length()) {
            return null;
        }

        String currentSubstring = input.substring(position).toLowerCase();
        char currentChar = input.charAt(position);

        // Handle Marathi keywords and symbols
        if (input.startsWith("He aahe", position)) {
            position += "He aahe".length();
            return new Token("VAR_DECL", "He aahe");
        }
        if (input.startsWith("Jar", position)) {
            position += "Jar".length();
            return new Token("IF", "Jar");
        }
        if (input.startsWith("Nahitar", position)) {
            position += "Nahitar".length();
            return new Token("ELSE", "Nahitar");
        }
        if (input.startsWith("Chapa", position)) {
            position += "Chapa".length();
            return new Token("PRINT", "Chapa");
        }
        if (input.startsWith("joparyant", position)) {
            position += "joparyant".length();
            return new Token("WHILE", "joparyant");
        }
        if (input.startsWith("Suruwaat", position)) {
            position += "Suruwaat".length();
            return new Token("FOR", "Suruwaat");
        }
        if (input.startsWith("Karya", position)) {
            position += "Karya".length();
            return new Token("KARYA", "Karya");
        }
        if (input.startsWith("Bolav", position)) {
            position += "Bolav".length();
            return new Token("BOLAV", "Bolav");
        }

        // Handle string literals
        if (currentChar == '"') {
            return readString();
        }

        // Handle multi-character operators (==, !=, <=, >=)
        if (currentSubstring.startsWith("==") || currentSubstring.startsWith("!=") ||
                currentSubstring.startsWith("<=") || currentSubstring.startsWith(">=")) {
            position += 2;
            return new Token("OPERATOR", currentSubstring.substring(0, 2));
        }

        // Handle single-character operators (<, >, =, +, -, *, /)
        if ("<>=+-*/".indexOf(currentChar) != -1) {
            position++;
            return new Token("OPERATOR", String.valueOf(currentChar));
        }

        // Handle parentheses and braces
        if (currentChar == '(') {
            position++;
            return new Token("LPAREN", "(");
        }
        if (currentChar == ')') {
            position++;
            return new Token("RPAREN", ")");
        }
        if (currentChar == '{') {
            position++;
            return new Token("LBRACE", "{");
        }
        if (currentChar == '}') {
            position++;
            return new Token("RBRACE", "}");
        }

        // Handle semicolon
        if (currentChar == ';') {
            position++;
            return new Token("SEMICOLON", ";");
        }

        // Handle comma
        if (currentChar == ',') {
            position++;
            return new Token("COMMA", ",");
        }

        // Handle numbers (including floats)
        if (Character.isDigit(currentChar)) {
            return readNumber();
        }

        // Handle identifiers (variables and keywords)
        if (Character.isLetter(currentChar)) {
            return readIdentifier();
        }

        // Unexpected character
        throw new RuntimeException("Unexpected character: " + currentChar);
    }

    private void skipWhitespace() {
        while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }

    private Token readNumber() {
        int start = position;
        boolean isFloat = false;

        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            if (input.charAt(position) == '.') {
                if (isFloat) {
                    throw new RuntimeException("Invalid float format.");
                }
                isFloat = true; // It's a float if it contains a decimal point
            }
            position++;
        }

        String number = input.substring(start, position);
        return new Token(isFloat ? "FLOAT" : "NUMBER", number);
    }

    private Token readIdentifier() {
        int start = position;

        // Variable names can include letters, digits, and underscores
        while (position < input.length() && (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
            position++;
        }

        String identifier = input.substring(start, position);
        return new Token("IDENTIFIER", identifier);
    }

    private Token readString() {
        position++; // Skip the opening quote
        int start = position;

        while (position < input.length() && input.charAt(position) != '"') {
            position++;
        }

        if (position >= input.length()) {
            throw new RuntimeException("Unterminated string literal");
        }

        String stringLiteral = input.substring(start, position);
        position++; // Skip the closing quote
        return new Token("STRING", stringLiteral);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = nextToken()) != null) {
            tokens.add(token);
        }
        return tokens;
    }
}
