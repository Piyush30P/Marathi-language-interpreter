import java.util.ArrayList;
import java.util.List;

public class MarathiParser {
    private List<Token> tokens;
    private int currentPosition;

    public MarathiParser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentPosition = 0;
    }

    private Token currentToken() {
        if (currentPosition >= tokens.size()) {
            return null;
        }
        return tokens.get(currentPosition);
    }

    private boolean match(String type) {
        Token token = currentToken();
        return token != null && token.getType().equals(type);
    }

    private Token consume(String expectedType) {
        if (currentPosition >= tokens.size()) {
            throw new RuntimeException("Unexpected end of input. Expected: " + expectedType);
        }
        Token token = tokens.get(currentPosition);
        if (!token.getType().equals(expectedType)) {
            throw new RuntimeException(
                    "Expected " + expectedType + " but found " + token.getType() + ": " + token.getValue());
        }
        currentPosition++;
        return token;
    }

    public ASTNode parse() {
        return parseBlock();
    }

    private ASTNode parseBlock() {
        List<ASTNode> statements = new ArrayList<>();
        while (currentToken() != null && !match("RBRACE")) { // Continue parsing until a closing brace is encountered
            statements.add(parseStatement());
        }
        return new BlockNode(statements); // Return the block node
    }

    private ASTNode parseStatement() {
        if (match("VAR_DECL")) {
            return parseVariableDeclaration();
        } else if (match("PRINT")) {
            return parsePrintStatement();
        }
        else if (match("FOR")) {
            return parseForStatement();
        }else if (match("IF")) {
            return parseIfStatement();
        } else if (match("WHILE")) {  // Handle while loops
            return parseWhileStatement();
        } else if (match("RETURN")) {  // Handle return statements
            return parseReturnStatement();
        } else if (match("BREAK")) {  // Handle break statements
            return parseBreakStatement();
        } else if (match("CONTINUE")) {  // Handle continue statements
            return parseContinueStatement();
        } else if (match("IDENTIFIER")) {  // Handle assignment or function calls
            Token identifierToken = consume("IDENTIFIER");

            if (match("OPERATOR") && currentToken().getValue().equals("=")) {  // Handle assignments
                return parseAssignment(identifierToken.getValue());
            } else {  // Handle function calls
                return parseFunctionCall(identifierToken.getValue());
            }
        } else if (match("KARYA")) { // Handle function declaration
            return parseFunctionDeclaration();
        } else if (match("RBRACE")) {
            return null;  // Skip closing brace
        }
        throw new RuntimeException("Unexpected token: " + currentToken());
    }
    
    private ASTNode parseAssignment(String variableName) {
        consume("OPERATOR");  // Consume '='
        ASTNode value = parseExpression();  // Parse the right-hand side as an expression
        consume("SEMICOLON");  // Expect a semicolon at the end of the assignment
        return new AssignmentNode(variableName, value);  // Return an AssignmentNode
    }
    
    
    
    private ASTNode parseFunctionDeclaration() {
        consume("KARYA"); // Consume the 'Kaarya' keyword
        String functionName = consume("IDENTIFIER").getValue(); // Parse the function name (e.g., 'greet')

        consume("LPAREN"); // Expect '(' for parameters
        List<String> parameters = new ArrayList<>();
        if (!match("RPAREN")) { // If there are parameters
            parameters.add(consume("IDENTIFIER").getValue()); // Parse the first parameter
            while (match("COMMA")) {
                consume("COMMA");
                parameters.add(consume("IDENTIFIER").getValue()); // Handle additional parameters
            }
        }
        consume("RPAREN"); // Expect ')'

        consume("LBRACE"); // Expect '{' to start the function body
        ASTNode body = parseBlock(); // Parse the function body (statements inside the function)
        consume("RBRACE"); // Expect '}' to end the function body

        return new FunctionDeclarationNode(functionName, parameters, body);
    }

    // Parse while loop
    private ASTNode parseWhileStatement() {
        consume("WHILE"); // Consume 'joparyant' or whatever keyword you use for while loops
        consume("LPAREN"); // Expect '('
        ASTNode condition = parseExpression(); // Parse the loop condition
        consume("RPAREN"); // Expect ')'
        consume("LBRACE"); // Expect '{' for the block of statements
        ASTNode body = parseBlock(); // Parse the block of statements inside the loop
        consume("RBRACE"); // Expect '}' to close the block

        return new WhileStatementNode(condition, body); // Return a new WhileStatementNode
    }
    private ASTNode parseFunctionCall(String functionName) {
        consume("LPAREN");  // Expect '('
        List<ASTNode> arguments = new ArrayList<>();
        
        if (!match("RPAREN")) {
            arguments.add(parseExpression());  // Parse the first argument
            
            while (match("COMMA")) {  // Handle multiple arguments
                consume("COMMA");
                arguments.add(parseExpression());
            }
        }
        
        consume("RPAREN");  // Expect ')'
        consume("SEMICOLON");  // Expect a semicolon after the function call
        return new FunctionCallNode(functionName, arguments);  // Return a function call node
    }
    
    private ASTNode parseForStatement() {
        consume("FOR");  // Consume 'Suruwaat'
        consume("LPAREN");  // Consume '('

        // Parse initialization (e.g., He aahe i = 0 or i = 0)
        ASTNode initialization = null;
        if (match("VAR_DECL")) {  // Variable declaration for initialization (He aahe i = 0)
            initialization = parseVariableDeclaration();  // Parse variable declaration (includes semicolon)
        } else if (match("IDENTIFIER")) {  // Assignment for initialization (i = 0)
            String variableName = consume("IDENTIFIER").getValue();  // Get the variable name
            initialization = parseAssignment(variableName);  // Parse the assignment (includes semicolon)
        } else {
            consume("SEMICOLON");  // Empty initialization
        }
        // Note: semicolon already consumed by parseVariableDeclaration or parseAssignment

        // Parse condition (e.g., i < 5)
        ASTNode condition = parseExpression();  // Parse the loop condition
        consume("SEMICOLON");  // Ensure semicolon is consumed after the condition

        // Parse increment (e.g., i = i + 1)
        String incrementVariable = consume("IDENTIFIER").getValue();  // Get the increment variable name
        consume("OPERATOR");  // Consume the '=' operator
        ASTNode incrementValue = parseExpression();  // Parse the expression for the increment
        consume("RPAREN");  // Ensure ')' is consumed after the increment expression
        consume("LBRACE");  // Ensure '{' is consumed to start the loop body

        // Parse the loop body
        ASTNode body = parseBlock();  // Parse the block as the loop body

        consume("RBRACE");  // Ensure '}' is consumed to close the loop body

        return new ForStatementNode(initialization, condition, new AssignmentNode(incrementVariable, incrementValue), body);  // Return the for loop node
    }
    
     
    
    private ASTNode parseExpression() {
        ASTNode left = parsePrimaryExpression();  // Start by parsing the left-hand side

        // Check for binary operators like +, -, *, / and logical operators
        while (match("OPERATOR") || match("LOGICAL_OP")) {
            String operator;
            if (match("OPERATOR")) {
                operator = consume("OPERATOR").getValue();
            } else {
                operator = consume("LOGICAL_OP").getValue();
            }
            ASTNode right = parsePrimaryExpression();  // Parse the right-hand side
            left = new BinaryOperationNode(left, operator, right);  // Create a BinaryOperationNode
        }

        return left;  // Return the full expression
    }
    
    private ASTNode parsePrimaryExpression() {
        if (match("IDENTIFIER")) {
            Token idToken = consume("IDENTIFIER");
            // Check if it's a function call (identifier followed by '(')
            if (match("LPAREN")) {
                return parseFunctionCallExpression(idToken.getValue());
            } else {
                return new VariableReferenceNode(idToken.getValue());  // Handle variables
            }
        } else if (match("FLOAT")) {
            return new FloatNode(consume("FLOAT").getValue());  // Handle floats
        } else if (match("NUMBER")) {
            return new NumberNode(consume("NUMBER").getValue());  // Handle integers
        } else if (match("STRING")) {
            return new StringNode(consume("STRING").getValue());  // Handle string literals
        } else if (match("BOOLEAN")) {
            String boolValue = consume("BOOLEAN").getValue();
            return new BooleanNode(boolValue.equals("khara"));  // Handle boolean literals
        } else if (match("INPUT")) {
            return parseInputExpression();  // Handle user input
        } else {
            throw new RuntimeException("Unexpected expression: " + currentToken());  // Handle unexpected tokens
        }
    }

    private ASTNode parseFunctionCallExpression(String functionName) {
        consume("LPAREN");  // Expect '('
        List<ASTNode> arguments = new ArrayList<>();

        if (!match("RPAREN")) {
            arguments.add(parseExpression());  // Parse the first argument

            while (match("COMMA")) {  // Handle multiple arguments
                consume("COMMA");
                arguments.add(parseExpression());
            }
        }

        consume("RPAREN");  // Expect ')'
        // Note: No semicolon here since it's an expression, not a statement
        return new FunctionCallNode(functionName, arguments);
    }

    private ASTNode parseInputExpression() {
        consume("INPUT");  // Consume 'Ghe'
        consume("LPAREN");
        String prompt = "";
        if (match("STRING")) {
            prompt = consume("STRING").getValue();
        }
        consume("RPAREN");
        return new InputNode(prompt);
    }
    
    

    private ASTNode parseVariableDeclaration() {
        consume("VAR_DECL");
        String variableName = consume("IDENTIFIER").getValue();
        consume("OPERATOR"); // Expect '='

        // Handle different value types or expressions
        ASTNode value = parseExpression();  // Parse the expression (can be number, float, string, boolean, input, or complex expression)

        consume("SEMICOLON");
        return new VariableDeclarationNode(variableName, value);
    }
    
    

    // private ASTNode parsePrintStatement() {
    // consume("PRINT");
    // consume("LPAREN");

    // // Handle string literals or expressions
    // ASTNode expression;
    // if (match("STRING")) {
    // String message = consume("STRING").getValue();
    // expression = new StringNode(message); // Use StringNode for string literals
    // } else {
    // expression = parseExpression(); // Handle expressions such as a + b or
    // variable references
    // }

    // consume("RPAREN");
    // consume("SEMICOLON");
    // return new PrintStatementNode(expression); // Now returning an ASTNode for
    // the print statement
    // }
    private ASTNode parsePrintStatement() {
        consume("PRINT"); // Consume the 'Chapa' keyword
        consume("LPAREN"); // Expect '(' for the print statement

        // Parse the full expression (string literal or variable)
        ASTNode expression = parseExpression(); // This handles string literals, variables, or expressions like "Hello "
                                                // + name

        consume("RPAREN"); // Expect closing parenthesis ')'
        consume("SEMICOLON"); // Expect semicolon ';' at the end of the print statement

        return new PrintStatementNode(expression); // Return the parsed print statement as an AST node
    }

    private ASTNode parseIfStatement() {
        consume("IF");
        consume("LPAREN");
        ASTNode condition = parseCondition();
        consume("RPAREN");

        consume("LBRACE"); // Expect '{' to start the block
        ASTNode thenBranch = parseBlock();
        consume("RBRACE"); // Expect '}' to close the block

        ASTNode elseBranch = null;
        if (match("ELSE")) {
            consume("ELSE");
            consume("LBRACE"); // Expect '{' to start the else block
            elseBranch = parseBlock();
            consume("RBRACE"); // Expect '}' to close the else block
        }

        return new IfStatementNode(condition, thenBranch, elseBranch);
    }

    private ASTNode parseCondition() {
        String variableName = consume("IDENTIFIER").getValue();
        String operator = consume("OPERATOR").getValue();
        String value = consume("NUMBER").getValue();
        return new ConditionNode(variableName, operator, value);
    }

    private ASTNode parseReturnStatement() {
        consume("RETURN");  // Consume 'Parat'
        ASTNode expression = null;
        if (!match("SEMICOLON")) {  // Check if there's a return value
            expression = parseExpression();
        }
        consume("SEMICOLON");
        return new ReturnStatementNode(expression);
    }

    private ASTNode parseBreakStatement() {
        consume("BREAK");  // Consume 'Thamba'
        consume("SEMICOLON");
        return new BreakStatementNode();
    }

    private ASTNode parseContinueStatement() {
        consume("CONTINUE");  // Consume 'Pudhe'
        consume("SEMICOLON");
        return new ContinueStatementNode();
    }
}
