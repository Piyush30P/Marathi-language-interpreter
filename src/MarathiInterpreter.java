import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarathiInterpreter {
    // A symbol table to store variable names and their values
    private Map<String, Object> symbolTable = new HashMap<>();

    // List to accumulate output
    private List<String> outputBuffer = new ArrayList<>();

    // Scanner for user input
    private Scanner scanner = new Scanner(System.in);

    // Exception classes for control flow
    public static class BreakException extends RuntimeException {}
    public static class ContinueException extends RuntimeException {}
    public static class ReturnException extends RuntimeException {
        private Object value;
        public ReturnException(Object value) {
            this.value = value;
        }
        public Object getValue() {
            return value;
        }
    }

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
        } else if (node instanceof ReturnStatementNode) {
            interpretReturnStatementNode((ReturnStatementNode) node);
        } else if (node instanceof BreakStatementNode) {
            throw new BreakException();
        } else if (node instanceof ContinueStatementNode) {
            throw new ContinueException();
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

            try {
                // Execute the loop body
                interpret(whileStmtNode.getBody());
            } catch (BreakException e) {
                break;  // Exit the loop on break statement
            } catch (ContinueException e) {
                continue;  // Continue to next iteration on continue statement
            }
        }
    }

    private void interpretForStatementNode(ForStatementNode forStmtNode) {
        // Interpret the initialization
        interpret(forStmtNode.getInitialization());

        // Interpret the condition, increment, and loop body
        while ((boolean) evaluateExpression(forStmtNode.getCondition())) {
            try {
                interpret(forStmtNode.getBody());
            } catch (BreakException e) {
                break;  // Exit the loop on break statement
            } catch (ContinueException e) {
                // Continue to next iteration, but still execute the increment
            }
            interpret(forStmtNode.getIncrement());
        }
    }

    private void interpretVariableDeclarationNode(VariableDeclarationNode varDeclNode) {
        System.out.println("Interpreting Variable Declaration:");
        System.out.println("  Variable Name: " + varDeclNode.getVariableName());
        System.out.println("  Value: " + varDeclNode.getValue());

        // Evaluate the expression and store it in the symbol table
        Object value = evaluateExpression(varDeclNode.getValue());
        symbolTable.put(varDeclNode.getVariableName(), value);
    }

    private Map<String, FunctionDeclarationNode> functionTable = new HashMap<>(); // Store functions

    private void interpretFunctionDeclarationNode(FunctionDeclarationNode funcDeclNode) {
        functionTable.put(funcDeclNode.getFunctionName(), funcDeclNode); // Store the function in the function table
    }

    private Object interpretFunctionCallNode(FunctionCallNode funcCallNode) {
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

        // Execute the function body and handle return values
        Object returnValue = null;
        try {
            interpret(funcDecl.getBody());
        } catch (ReturnException e) {
            returnValue = e.getValue();
        }

        // Restore the previous symbol table
        symbolTable = previousSymbolTable;

        return returnValue;
    }

    private void interpretReturnStatementNode(ReturnStatementNode returnNode) {
        Object value = null;
        if (returnNode.getExpression() != null) {
            value = evaluateExpression(returnNode.getExpression());
        }
        throw new ReturnException(value);
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
    } else if (node instanceof BooleanNode) {
        // Handle boolean literals
        return ((BooleanNode) node).getValue();
    } else if (node instanceof InputNode) {
        // Handle user input
        InputNode inputNode = (InputNode) node;
        if (!inputNode.getPrompt().isEmpty()) {
            System.out.print(inputNode.getPrompt());
            outputBuffer.add(inputNode.getPrompt());
        }
        String input = scanner.nextLine();
        return input;
    } else if (node instanceof FunctionCallNode) {
        // Handle function calls in expressions (for return values)
        return interpretFunctionCallNode((FunctionCallNode) node);
    } else if (node instanceof BinaryOperationNode) {
        // Handle binary operations like a + b or relational operators
        BinaryOperationNode binOp = (BinaryOperationNode) node;
        Object left = evaluateExpression(binOp.getLeft());
        Object right = evaluateExpression(binOp.getRight());
        String operator = binOp.getOperator();

        // Handle logical operators
        if (operator.equals("aani")) {  // AND
            return asBoolean(left) && asBoolean(right);
        } else if (operator.equals("kiva")) {  // OR
            return asBoolean(left) || asBoolean(right);
        } else if (operator.equals("nahi")) {  // NOT (unary, but handled as binary for simplicity)
            return !asBoolean(right);
        }

        // Handle string concatenation
        if (left instanceof String || right instanceof String) {
            return left.toString() + right.toString();
        }

        // Handle boolean comparisons
        if (left instanceof Boolean || right instanceof Boolean) {
            if (operator.equals("==")) {
                return left.equals(right);
            } else if (operator.equals("!=")) {
                return !left.equals(right);
            }
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

    // Helper method to cast values to boolean
    private boolean asBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Integer) {
            return (Integer) value != 0;
        } else if (value instanceof Double) {
            return (Double) value != 0.0;
        } else if (value instanceof String) {
            return !((String) value).isEmpty();
        } else {
            throw new RuntimeException("Cannot convert value to boolean: " + value);
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


