import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.ArrayList;
import java.util.List;

public class MarathiInterpreter {
    // A symbol table to store variable names and their values
    private Map<String, Object> symbolTable = new HashMap<>();

    // List to accumulate output
    private List<String> outputBuffer = new ArrayList<>();

    public void interpret(ASTNode node) {
        if (node instanceof BlockNode) {
            interpretBlockNode((BlockNode) node);
        } else if (node instanceof WhileStatementNode) {
            interpretWhileStatementNode((WhileStatementNode) node);
        } else if (node instanceof ForStatementNode) {
            interpretForStatementNode((ForStatementNode) node);
        } else if (node instanceof IfStatementNode) {
            interpretIfStatementNode((IfStatementNode) node);
        } else if (node instanceof VariableDeclarationNode) {
            interpretVariableDeclarationNode((VariableDeclarationNode) node);
        } else if (node instanceof PrintStatementNode) {
            interpretPrintStatementNode((PrintStatementNode) node);
        } else if (node instanceof FunctionDeclarationNode) { // Handle function declarations
            interpretFunctionDeclarationNode((FunctionDeclarationNode) node);
        } else if (node instanceof AssignmentNode) {
            interpretAssignmentNode((AssignmentNode) node); // Handle assignments
        } else if (node instanceof FunctionCallNode) { // Handle function calls
            interpretFunctionCallNode((FunctionCallNode) node);
        } else {
            throw new RuntimeException("Unexpected AST node type: " + node.getClass().getName());
        }
    }

    private void interpretBlockNode(BlockNode blockNode) {
        for (ASTNode statement : blockNode.getStatements()) {
            System.out.println("    Interpreting statement in the block..."); // Add debug info for each statement in
                                                                              // the block
            interpret(statement);
        }
    }

    private void interpretAssignmentNode(AssignmentNode assignmentNode) {
        String variableName = assignmentNode.getVariableName();
        Object value = evaluateExpression(assignmentNode.getValue()); // Evaluate the right-hand side
        symbolTable.put(variableName, value); // Store the result in the symbol table
    }

    private void interpretIfStatementNode(IfStatementNode ifStmtNode) {
        ConditionNode condition = (ConditionNode) ifStmtNode.getCondition();
        System.out.println("Interpreting If Statement...");
        System.out.println("  Evaluating Condition:");
        System.out.println("    Variable: " + condition.getVariableName());
        System.out.println("    Operator: " + condition.getOperator());
        System.out.println("    Value: " + condition.getValue());

        // Evaluate the condition
        boolean conditionIsTrue = evaluateCondition(condition);

        if (conditionIsTrue) {
            System.out.println("  Then Branch:"); // Add this line to clearly indicate we're in the thenBranch
            interpret(ifStmtNode.getThenBranch());
        } else if (ifStmtNode.getElseBranch() != null) {
            System.out.println("  Else Branch:"); // Similarly for elseBranch
            interpret(ifStmtNode.getElseBranch());
        }

        System.out.println("\n\n"); // Add space for clarity after interpreting the statement
    }

    private boolean evaluateCondition(ConditionNode condition) {
        Object variableValue = symbolTable.get(condition.getVariableName());
        String operator = condition.getOperator();
        Object conditionValue = condition.getValue();

        if (variableValue == null) {
            throw new RuntimeException("Undefined variable: " + condition.getVariableName());
        }

        int variableIntValue = Integer.parseInt(variableValue.toString());
        int conditionIntValue = Integer.parseInt(conditionValue.toString());

        switch (operator) {
            case "==":
                return variableIntValue == conditionIntValue;
            case "<":
                return variableIntValue < conditionIntValue;
            case ">":
                return variableIntValue > conditionIntValue;
            case "<=":
                return variableIntValue <= conditionIntValue;
            case ">=":
                return variableIntValue >= conditionIntValue;
            default:
                throw new RuntimeException("Unsupported operator: " + operator);
        }
    }

    private void interpretWhileStatementNode(WhileStatementNode whileStmtNode) {
        while (true) {
            // Evaluate the condition
            Boolean conditionValue = (Boolean) evaluateExpression(whileStmtNode.getCondition());

            if (!conditionValue) {
                break; // Exit the loop if the condition is false
            }

            // Execute the loop body
            interpret(whileStmtNode.getBody());
        }
    }

    private void interpretForStatementNode(ForStatementNode forStmtNode) {
        // Interpret the initialization
        interpret(forStmtNode.getInitialization());

        // Interpret the condition, increment, and loop body
        while ((boolean) evaluateExpression(forStmtNode.getCondition())) {
            interpret(forStmtNode.getBody());
            interpret(forStmtNode.getIncrement());
        }
    }

    private void interpretVariableDeclarationNode(VariableDeclarationNode varDeclNode) {
        System.out.println("Interpreting Variable Declaration:");
        System.out.println("  Variable Name: " + varDeclNode.getVariableName());
        System.out.println("  Value: " + varDeclNode.getValue());

        // Check the type of the value and store it in the symbol table
        if (varDeclNode.getValue() instanceof NumberNode) {
            // Handle integer numbers
            NumberNode numberNode = (NumberNode) varDeclNode.getValue();
            symbolTable.put(varDeclNode.getVariableName(), Integer.parseInt(numberNode.getValue()));
        } else if (varDeclNode.getValue() instanceof FloatNode) {
            // Handle floating-point numbers
            FloatNode floatNode = (FloatNode) varDeclNode.getValue();
            symbolTable.put(varDeclNode.getVariableName(), Double.parseDouble(floatNode.getValue()));
        } else if (varDeclNode.getValue() instanceof StringNode) {
            // Handle strings
            StringNode stringNode = (StringNode) varDeclNode.getValue();
            symbolTable.put(varDeclNode.getVariableName(), stringNode.getValue());
        } else {
            throw new RuntimeException(
                    "Unsupported variable declaration value type: " + varDeclNode.getValue().getClass().getName());
        }
    }

    private Map<String, FunctionDeclarationNode> functionTable = new HashMap<>(); // Store functions

    private void interpretFunctionDeclarationNode(FunctionDeclarationNode funcDeclNode) {
        functionTable.put(funcDeclNode.getFunctionName(), funcDeclNode); // Store the function in the function table
    }

    private void interpretFunctionCallNode(FunctionCallNode funcCallNode) {
        String functionName = funcCallNode.getFunctionName();
        FunctionDeclarationNode funcDecl = functionTable.get(functionName); // Get the function declaration

        if (funcDecl == null) {
            throw new RuntimeException("Undefined function: " + functionName);
        }

        // Evaluate the arguments
        List<Object> argumentValues = new ArrayList<>();
        for (ASTNode arg : funcCallNode.getArguments()) {
            argumentValues.add(evaluateExpression(arg)); // Evaluate each argument
        }

        // Save the current symbol table
        Map<String, Object> previousSymbolTable = new HashMap<>(symbolTable);

        // Bind the arguments to the parameters
        for (int i = 0; i < funcDecl.getParameters().size(); i++) {
            symbolTable.put(funcDecl.getParameters().get(i), argumentValues.get(i));
        }

        // Execute the function body
        interpret(funcDecl.getBody());

        // Restore the previous symbol table
        symbolTable = previousSymbolTable;
    }

    // Evaluate the expression (either a variable reference, number, or binary
    // operation)
    // Evaluate the expression (either a variable reference, number, or binary operation)
private Object evaluateExpression(ASTNode node) {
    if (node instanceof VariableReferenceNode) {
        // Handle variable reference
        String variableName = ((VariableReferenceNode) node).getVariableName();
        return symbolTable.get(variableName);
    } else if (node instanceof NumberNode) {
        // Handle integer numbers
        return Integer.parseInt(((NumberNode) node).getValue());
    } else if (node instanceof FloatNode) {
        // Handle floating-point numbers
        return Double.parseDouble(((FloatNode) node).getValue());
    } else if (node instanceof BinaryOperationNode) {
        // Handle binary operations like a + b or relational operators
        BinaryOperationNode binOp = (BinaryOperationNode) node;
        Object left = evaluateExpression(binOp.getLeft());
        Object right = evaluateExpression(binOp.getRight());
        String operator = binOp.getOperator();

        // Handle string concatenation
        if (left instanceof String || right instanceof String) {
            return left.toString() + right.toString();
        }

        // Handle mixed-type arithmetic (integers and floats)
        if (left instanceof Integer && right instanceof Integer) {
            return evaluateBinaryOperation((Integer) left, (Integer) right, operator);
        } else {
            return evaluateBinaryOperation(asDouble(left), asDouble(right), operator);
        }
    } else if (node instanceof StringNode) {
        // Handle string literals
        return ((StringNode) node).getValue();
    } else {
        throw new RuntimeException("Unknown expression type: " + node.getClass().getName());
    }
}

// Helper method to perform binary operations for integers
private Object evaluateBinaryOperation(int left, int right, String operator) {
    switch (operator) {
        case "+":
            return left + right;
        case "-":
            return left - right;
        case "*":
            return left * right;
        case "/":
            if (right == 0) throw new RuntimeException("Division by zero error");
            return left / right;
        case "<":
            return left < right;
        case ">":
            return left > right;
        case "<=":
            return left <= right;
        case ">=":
            return left >= right;
        case "==":
            return left == right;
        case "!=":
            return left != right;
        default:
            throw new RuntimeException("Unsupported operator: " + operator);
    }
}

// Helper method to perform binary operations for doubles
private Object evaluateBinaryOperation(double left, double right, String operator) {
    switch (operator) {
        case "+":
            return left + right;
        case "-":
            return left - right;
        case "*":
            return left * right;
        case "/":
            if (right == 0) throw new RuntimeException("Division by zero error");
            return left / right;
        case "<":
            return left < right;
        case ">":
            return left > right;
        case "<=":
            return left <= right;
        case ">=":
            return left >= right;
        case "==":
            return left == right;
        case "!=":
            return left != right;
        default:
            throw new RuntimeException("Unsupported operator: " + operator);
    }
}


    // Helper method to cast numbers to double if needed
    private double asDouble(Object value) {
        if (value instanceof Integer) {
            return (double) (Integer) value;
        } else if (value instanceof Double) {
            return (Double) value;
        } else {
            throw new RuntimeException("Unexpected value type: " + value.getClass().getName());
        }
    }

    // A helper method to handle numeric operations between integers and floats
    private Object handleNumericOperation(Object left, Object right, BiFunction<Float, Float, Float> operation) {
        float leftValue = (left instanceof Integer) ? (float) (Integer) left : (Float) left;
        float rightValue = (right instanceof Integer) ? (float) (Integer) right : (Float) right;
        return operation.apply(leftValue, rightValue);
    }

    private void interpretPrintStatementNode(PrintStatementNode printStmtNode) {
        ASTNode expression = printStmtNode.getExpression(); // Get the expression in the print statement
        Object value = evaluateExpression(expression); // Evaluate the expression (e.g., a + b)

        System.out.println("Actual OUTPUT: " + value); // Print the evaluated result
        outputBuffer.add(value.toString()); // Accumulate output in the buffer
    }

    public void printFinalOutput() {
        System.out.println("PROGRAM OUTPUT :");
        for (String output : outputBuffer) {
            System.out.println("                 " + output);
        }
    }public String getOutput() {
        return String.join("\n", outputBuffer);  // Join the output buffer into a single string
    }
}


