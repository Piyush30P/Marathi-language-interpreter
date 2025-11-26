import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;

public class MarathiLangEditor {
    private JTextPane codeEditor;
    private JTextArea outputConsole;
    private JFrame frame;

    // Modern color scheme
    private static final Color DARK_BLUE = new Color(30, 39, 46);
    private static final Color LIGHT_BLUE = new Color(116, 185, 255);
    private static final Color GREEN_ACCENT = new Color(39, 174, 96);
    private static final Color RED_ACCENT = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(247, 249, 251);
    private static final Color DARK_GRAY = new Color(45, 52, 54);
    private static final Color WHITE = Color.WHITE;
    private static final Color ORANGE_ACCENT = new Color(243, 156, 18);
    private static final Color PURPLE_ACCENT = new Color(142, 68, 173);

    public MarathiLangEditor() {
        createFrame();
        createComponents();
        layoutComponents();
        initSyntaxHighlighting();
        frame.setVisible(true);
    }

    private void createFrame() {
        frame = new JFrame("🚀 MarathiLang Code Editor v2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setBackground(LIGHT_GRAY);
    }

    private void createComponents() {
        // Create code editor with modern styling
        codeEditor = new JTextPane();
        codeEditor.setFont(new Font("Consolas", Font.PLAIN, 16));
        codeEditor.setBackground(WHITE);
        codeEditor.setForeground(DARK_GRAY);
        codeEditor.setBorder(new EmptyBorder(15, 15, 15, 15));
        codeEditor.setCaretColor(DARK_BLUE);

        // Create output console with modern styling
        outputConsole = new JTextArea();
        outputConsole.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputConsole.setEditable(false);
        outputConsole.setBackground(new Color(40, 44, 52));
        outputConsole.setForeground(new Color(171, 178, 191));
        outputConsole.setBorder(new EmptyBorder(15, 15, 15, 15));
        outputConsole.setCaretColor(LIGHT_BLUE);

        // Add placeholder text
        codeEditor.setText(
                "// Welcome to MarathiLang!\n// Type your Marathi code here...\n\nHe aahe x = 10;\nChapa(\"Hello, Marathi!\");");
    }

    private void layoutComponents() {
        frame.setLayout(new BorderLayout(10, 10));

        // Create header panel
        JPanel headerPanel = createHeaderPanel();

        // Create main content panel with split pane
        JSplitPane mainSplitPane = createMainSplitPane();

        // Create footer panel
        JPanel footerPanel = createFooterPanel();

        // Add panels to frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(mainSplitPane, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Title label
        JLabel titleLabel = new JLabel("🇮🇳 MarathiLang Code Editor");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(WHITE);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Program in your native Marathi language");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(LIGHT_BLUE);

        // Button panel
        JPanel buttonPanel = createButtonPanel();

        // Layout header
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(DARK_BLUE);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(DARK_BLUE);

        // Run button with modern styling
        JButton runButton = createStyledButton("▶ Run Code", GREEN_ACCENT);
        runButton.addActionListener(e -> runCode());

        // Clear button with modern styling
        JButton clearButton = createStyledButton("🗑 Clear", RED_ACCENT);
        clearButton.addActionListener(e -> clearConsole());

        // New file button
        JButton newButton = createStyledButton("📄 New", LIGHT_BLUE);
        newButton.addActionListener(e -> newFile());

        // Help button for documentation
        JButton helpButton = createStyledButton("📚 Help", ORANGE_ACCENT);
        helpButton.addActionListener(e -> showDocumentation());

        // Examples button for sample code
        JButton examplesButton = createStyledButton("💡 Examples", PURPLE_ACCENT);
        examplesButton.addActionListener(e -> showExamples());

        buttonPanel.add(helpButton);
        buttonPanel.add(examplesButton);
        buttonPanel.add(newButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(runButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(WHITE);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JSplitPane createMainSplitPane() {
        // Code editor panel
        JPanel codePanel = createCodePanel();

        // Output panel
        JPanel outputPanel = createOutputPanel();

        // Create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, codePanel, outputPanel);
        splitPane.setResizeWeight(0.6); // Give more space to code editor
        splitPane.setDividerSize(8);
        splitPane.setBackground(LIGHT_GRAY);
        splitPane.setBorder(new EmptyBorder(10, 20, 10, 20));

        return splitPane;
    }

    private JPanel createCodePanel() {
        JPanel codePanel = new JPanel(new BorderLayout());
        codePanel.setBackground(WHITE);
        codePanel.setBorder(createTitledBorder("📝 Code Editor", DARK_BLUE));

        JScrollPane codeScrollPane = new JScrollPane(codeEditor);
        codeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        codeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        codeScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add line numbers (simplified)
        JTextArea lineNumbers = new JTextArea("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n");
        lineNumbers.setFont(new Font("Consolas", Font.PLAIN, 14));
        lineNumbers.setBackground(new Color(240, 240, 240));
        lineNumbers.setForeground(Color.GRAY);
        lineNumbers.setEditable(false);
        lineNumbers.setBorder(new EmptyBorder(15, 10, 15, 10));

        codeScrollPane.setRowHeaderView(lineNumbers);
        codePanel.add(codeScrollPane, BorderLayout.CENTER);

        return codePanel;
    }

    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(createTitledBorder("📋 Program Output", new Color(40, 44, 52)));

        JScrollPane outputScrollPane = new JScrollPane(outputConsole);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        return outputPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(250, 250, 250));
        footerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel statusLabel = new JLabel("Ready | MarathiLang Interpreter v1.0 | Made with ❤️ for Marathi programming");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);

        JLabel keywordsLabel = new JLabel("Keywords: He aahe | Jar | Nahitar | Chapa | joparyant | Karya");
        keywordsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        keywordsLabel.setForeground(DARK_BLUE);

        footerPanel.add(statusLabel, BorderLayout.WEST);
        footerPanel.add(keywordsLabel, BorderLayout.EAST);

        return footerPanel;
    }

    private TitledBorder createTitledBorder(String title, Color color) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(color, 2), title);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        border.setTitleColor(color);
        return border;
    }

    private void newFile() {
        int result = JOptionPane.showConfirmDialog(frame,
                "Clear current code and start new file?",
                "New File",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            codeEditor.setText("");
            clearConsole();
        }
    }

    // Syntax highlighting for MarathiLang code
    private void initSyntaxHighlighting() {
        StyledDocument doc = codeEditor.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();

        // Add styles for different syntax elements
        Style varStyle = sc.addStyle("variable", null);
        StyleConstants.setForeground(varStyle, new Color(52, 152, 219));
        StyleConstants.setBold(varStyle, true);

        Style keywordStyle = sc.addStyle("keyword", null);
        StyleConstants.setForeground(keywordStyle, new Color(155, 89, 182));
        StyleConstants.setBold(keywordStyle, true);

        Style numberStyle = sc.addStyle("number", null);
        StyleConstants.setForeground(numberStyle, new Color(230, 126, 34));

        Style stringStyle = sc.addStyle("string", null);
        StyleConstants.setForeground(stringStyle, new Color(39, 174, 96));

        Style commentStyle = sc.addStyle("comment", null);
        StyleConstants.setForeground(commentStyle, Color.GRAY);
    }

    // Function to run the code in the editor
    private void runCode() {
        String code = codeEditor.getText().trim();
        outputConsole.setText(""); // Clear previous output
        outputConsole.setForeground(new Color(171, 178, 191));

        if (code.isEmpty() || code.contains("Type your Marathi code here")) {
            outputConsole.append("⚠️ Error: No code to execute\n");
            outputConsole.append("Please write some Marathi code in the editor.\n");
            return;
        }

        try {
            outputConsole.append("🚀 EXECUTING MARATHI CODE...\n");
            outputConsole.append("═══════════════════════════════\n\n");

            // Tokenization
            MarathiTokenizer tokenizer = new MarathiTokenizer(code);
            java.util.List<Token> tokens = tokenizer.tokenize();

            // Parsing
            MarathiParser parser = new MarathiParser(tokens);
            ASTNode ast = parser.parse();

            // Interpretation
            MarathiInterpreter interpreter = new MarathiInterpreter();
            interpreter.interpret(ast);

            // Get the output from interpreter
            outputConsole.append("📋 PROGRAM OUTPUT:\n");
            outputConsole.append("───────────────────\n");
            String programOutput = interpreter.getOutput();
            if (programOutput.isEmpty()) {
                outputConsole.append("(No output generated)\n");
            } else {
                outputConsole.append(programOutput + "\n");
            }

            outputConsole.append("\n✅ Execution completed successfully!");

        } catch (Exception e) {
            outputConsole.setForeground(new Color(231, 76, 60));
            outputConsole.append("❌ ERROR OCCURRED:\n");
            outputConsole.append("─────────────────\n");
            outputConsole.append(e.getMessage() + "\n\n");
            outputConsole.append("💡 Check your Marathi syntax and try again.");
        }
    }

    // Function to clear the output console
    private void clearConsole() {
        outputConsole.setText("");
        outputConsole.setForeground(new Color(171, 178, 191));
        outputConsole.append("🧹 Output cleared. Ready for new execution.\n");
    }

    private void showDocumentation() {
        JDialog docDialog = new JDialog(frame, "📚 MarathiLang Documentation", true);
        docDialog.setSize(800, 600);
        docDialog.setLocationRelativeTo(frame);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Basic Syntax Tab
        JScrollPane syntaxPanel = createDocumentationTab(getBasicSyntaxDoc());
        tabbedPane.addTab("🔤 Basic Syntax", syntaxPanel);

        // Keywords Tab
        JScrollPane keywordsPanel = createDocumentationTab(getKeywordsDoc());
        tabbedPane.addTab("🔑 Keywords", keywordsPanel);

        // Tutorial Tab
        JScrollPane tutorialPanel = createDocumentationTab(getTutorialDoc());
        tabbedPane.addTab("📖 Quick Tutorial", tutorialPanel);

        // Tips Tab
        JScrollPane tipsPanel = createDocumentationTab(getTipsDoc());
        tabbedPane.addTab("💡 Tips & Tricks", tipsPanel);

        docDialog.add(tabbedPane);
        docDialog.setVisible(true);
    }

    private JScrollPane createDocumentationTab(String content) {
        JTextArea textArea = new JTextArea(content);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBackground(new Color(250, 250, 250));
        textArea.setBorder(new EmptyBorder(20, 20, 20, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private String getBasicSyntaxDoc() {
        return "🇮🇳 MARATHILANG PROGRAMMING GUIDE 🇮🇳\n\n" +
                "Welcome to MarathiLang! Program in your native Marathi language.\n\n" +
                "📝 VARIABLE DECLARATION:\n" +
                "   He aahe variableName = value;\n" +
                "   Examples:\n" +
                "   • He aahe age = 25;\n" +
                "   • He aahe name = \"Rahul\";\n" +
                "   • He aahe price = 99.50;\n\n" +
                "🖨️ PRINT STATEMENTS:\n" +
                "   Chapa(\"your message\");\n" +
                "   Chapa(variableName);\n" +
                "   Chapa(\"Hello \" + name);\n\n" +
                "🔀 CONDITIONAL STATEMENTS:\n" +
                "   Jar (condition) {\n" +
                "       // code if condition is true\n" +
                "   } Nahitar {\n" +
                "       // code if condition is false\n" +
                "   }\n\n" +
                "🔄 LOOPS:\n" +
                "   While Loop: joparyant (condition) { code }\n" +
                "   For Loop: Suruwaat (init; condition; increment) { code }\n\n" +
                "⚙️ FUNCTIONS:\n" +
                "   Karya functionName(parameters) {\n" +
                "       // function body\n" +
                "       return value; (optional)\n" +
                "   }\n\n" +
                "💡 Remember: End each statement with semicolon (;)";
    }

    private String getKeywordsDoc() {
        return "🔑 MARATHILANG KEYWORDS REFERENCE\n\n" +
                "English → Marathi → Usage\n" +
                "═══════════════════════════════════════\n\n" +
                "📌 VARIABLE & DATA:\n" +
                "• Variable Declaration → He aahe → He aahe x = 10;\n" +
                "• Assignment → = → x = 20;\n\n" +
                "📌 CONTROL FLOW:\n" +
                "• If → Jar → Jar (x > 5) { }\n" +
                "• Else → Nahitar → Nahitar { }\n" +
                "• While → joparyant → joparyant (x < 10) { }\n" +
                "• For → Suruwaat → Suruwaat (i=0; i<5; i++) { }\n\n" +
                "📌 FUNCTIONS:\n" +
                "• Function → Karya → Karya myFunction() { }\n" +
                "• Return → return → return value;\n\n" +
                "📌 INPUT/OUTPUT:\n" +
                "• Print → Chapa → Chapa(\"Hello\");\n\n" +
                "📌 OPERATORS:\n" +
                "• Addition → + → x + y\n" +
                "• Subtraction → - → x - y\n" +
                "• Multiplication → * → x * y\n" +
                "• Division → / → x / y\n" +
                "• Equal to → == → x == y\n" +
                "• Not equal → != → x != y\n" +
                "• Greater than → > → x > y\n" +
                "• Less than → < → x < y\n" +
                "• Greater or equal → >= → x >= y\n" +
                "• Less or equal → <= → x <= y\n\n" +
                "📌 DATA TYPES:\n" +
                "• Numbers: 10, 25, 100\n" +
                "• Floats: 3.14, 99.99\n" +
                "• Strings: \"Hello\", \"Namaskar\"\n" +
                "• Booleans: true, false";
    }

    private String getTutorialDoc() {
        return "📖 QUICK START TUTORIAL\n\n" +
                "🚀 Let's create your first MarathiLang program step by step!\n\n" +
                "STEP 1: HELLO WORLD\n" +
                "═══════════════════\n" +
                "Chapa(\"Hello, World!\");\n" +
                "Chapa(\"Namaskar, Jagata!\");\n\n" +
                "STEP 2: VARIABLES\n" +
                "════════════════\n" +
                "He aahe myName = \"Rahul\";\n" +
                "He aahe myAge = 22;\n" +
                "Chapa(\"My name is \" + myName);\n" +
                "Chapa(\"I am \" + myAge + \" years old\");\n\n" +
                "STEP 3: BASIC MATH\n" +
                "═════════════════\n" +
                "He aahe a = 15;\n" +
                "He aahe b = 7;\n" +
                "He aahe sum = a + b;\n" +
                "Chapa(\"Sum: \" + sum);\n\n" +
                "STEP 4: CONDITIONS\n" +
                "════════════════\n" +
                "He aahe marks = 85;\n" +
                "Jar (marks >= 80) {\n" +
                "    Chapa(\"Excellent!\");\n" +
                "} Nahitar {\n" +
                "    Chapa(\"Good job!\");\n" +
                "}\n\n" +
                "STEP 5: LOOPS\n" +
                "═══════════════\n" +
                "He aahe i = 1;\n" +
                "joparyant (i <= 5) {\n" +
                "    Chapa(\"Count: \" + i);\n" +
                "    i = i + 1;\n" +
                "}\n\n" +
                "STEP 6: FUNCTIONS\n" +
                "═══════════════\n" +
                "Karya greet(name) {\n" +
                "    Chapa(\"Hello \" + name + \"!\");\n" +
                "}\n" +
                "greet(\"Priya\");\n" +
                "greet(\"Arjun\");\n\n" +
                "🎉 Congratulations! You've learned the basics!\n" +
                "💡 Try combining these concepts to create amazing programs!";
    }

    private String getTipsDoc() {
        return "💡 TIPS & TRICKS FOR BEGINNERS\n\n" +
                "🎯 CODING BEST PRACTICES:\n" +
                "═══════════════════════════\n" +
                "✅ Always end statements with semicolon (;)\n" +
                "✅ Use meaningful variable names\n" +
                "✅ Add spaces around operators for readability\n" +
                "✅ Use consistent indentation\n" +
                "✅ Test your code frequently\n\n" +
                "🐛 COMMON MISTAKES TO AVOID:\n" +
                "══════════════════════════\n" +
                "❌ Forgetting semicolons → He aahe x = 5\n" +
                "✅ Correct → He aahe x = 5;\n\n" +
                "❌ Missing quotes for strings → Chapa(Hello)\n" +
                "✅ Correct → Chapa(\"Hello\");\n\n" +
                "❌ Wrong bracket matching → Jar (x > 5 { }\n" +
                "✅ Correct → Jar (x > 5) { }\n\n" +
                "❌ Using undefined variables → Chapa(name);\n" +
                "✅ Correct → He aahe name = \"John\"; Chapa(name);\n\n" +
                "💡 HELPFUL SHORTCUTS:\n" +
                "══════════════════════\n" +
                "• Use 📄 New button to start fresh\n" +
                "• Use 🗑 Clear button to clean output\n" +
                "• Use 💡 Examples button for sample code\n" +
                "• Check this documentation whenever stuck\n\n" +
                "🎓 LEARNING PATH:\n" +
                "═════════════════\n" +
                "1. Master basic syntax (variables, print)\n" +
                "2. Learn conditions (Jar/Nahitar)\n" +
                "3. Practice loops (joparyant)\n" +
                "4. Create functions (Karya)\n" +
                "5. Build complete programs\n\n" +
                "🌟 Remember: Programming is like learning a language.\n" +
                "Practice regularly and don't be afraid to make mistakes!\n\n" +
                "🤝 Happy coding in Marathi! शुभेच्छा!";
    }

    private void showExamples() {
        String[] exampleTitles = {
                "🌟 Hello World",
                "🔢 Basic Math",
                "🤔 Conditions",
                "🔄 Loops",
                "⚙️ Functions",
                "🎮 Complete Program"
        };

        String[] examples = {
                getHelloWorldExample(),
                getMathExample(),
                getConditionsExample(),
                getLoopsExample(),
                getFunctionsExample(),
                getCompleteExample()
        };

        String selected = (String) JOptionPane.showInputDialog(
                frame,
                "Choose an example to load in the editor:",
                "💡 MarathiLang Code Examples",
                JOptionPane.QUESTION_MESSAGE,
                null,
                exampleTitles,
                exampleTitles[0]);

        if (selected != null) {
            for (int i = 0; i < exampleTitles.length; i++) {
                if (selected.equals(exampleTitles[i])) {
                    codeEditor.setText(examples[i]);
                    clearConsole();
                    break;
                }
            }
        }
    }

    private String getHelloWorldExample() {
        return "// 🌟 Hello World Example\n" +
                "// Your first MarathiLang program!\n\n" +
                "Chapa(\"Hello, World!\");\n" +
                "Chapa(\"Namaskar, Jagata!\");\n" +
                "Chapa(\"Welcome to MarathiLang!\");";
    }

    private String getMathExample() {
        return "// 🔢 Basic Math Operations\n" +
                "// Learn arithmetic in Marathi\n\n" +
                "He aahe a = 15;\n" +
                "He aahe b = 7;\n\n" +
                "Chapa(\"First number: \" + a);\n" +
                "Chapa(\"Second number: \" + b);\n\n" +
                "Chapa(\"Addition: \" + (a + b));\n" +
                "Chapa(\"Subtraction: \" + (a - b));\n" +
                "Chapa(\"Multiplication: \" + (a * b));\n" +
                "Chapa(\"Division: \" + (a / b));";
    }

    private String getConditionsExample() {
        return "// 🤔 Conditional Statements\n" +
                "// Making decisions in your program\n\n" +
                "He aahe age = 18;\n" +
                "He aahe marks = 85;\n\n" +
                "Jar (age >= 18) {\n" +
                "    Chapa(\"You can vote!\");\n" +
                "} Nahitar {\n" +
                "    Chapa(\"Too young to vote\");\n" +
                "}\n\n" +
                "Jar (marks >= 90) {\n" +
                "    Chapa(\"Grade: A+\");\n" +
                "} Nahitar {\n" +
                "    Jar (marks >= 80) {\n" +
                "        Chapa(\"Grade: A\");\n" +
                "    } Nahitar {\n" +
                "        Chapa(\"Grade: B\");\n" +
                "    }\n" +
                "}";
    }

    private String getLoopsExample() {
        return "// 🔄 Loops Example\n" +
                "// Repeating code efficiently\n\n" +
                "Chapa(\"Counting from 1 to 5:\");\n" +
                "He aahe i = 1;\n" +
                "joparyant (i <= 5) {\n" +
                "    Chapa(\"Count: \" + i);\n" +
                "    i = i + 1;\n" +
                "}\n\n" +
                "Chapa(\"\\nMultiplication table of 3:\");\n" +
                "He aahe j = 1;\n" +
                "joparyant (j <= 10) {\n" +
                "    Chapa(\"3 x \" + j + \" = \" + (3 * j));\n" +
                "    j = j + 1;\n" +
                "}";
    }

    private String getFunctionsExample() {
        return "// ⚙️ Functions Example\n" +
                "// Reusable code blocks\n\n" +
                "Karya greet(name) {\n" +
                "    Chapa(\"Hello, \" + name + \"!\");\n" +
                "    Chapa(\"Welcome to MarathiLang!\");\n" +
                "}\n\n" +
                "Karya calculate(x, y) {\n" +
                "    He aahe sum = x + y;\n" +
                "    Chapa(x + \" + \" + y + \" = \" + sum);\n" +
                "}\n\n" +
                "// Call functions\n" +
                "greet(\"Priya\");\n" +
                "greet(\"Arjun\");\n" +
                "calculate(10, 20);\n" +
                "calculate(5, 15);";
    }

    private String getCompleteExample() {
        return "He aahe num1 = 20;\\n" +
                "He aahe num2 = 5;\\n\\n" +
                "Chapa(num1 + num2);\\n" +
                "Chapa(num1 - num2);\\n" +
                "Chapa(num1 * num2);\\n" +
                "Chapa(num1 / num2);";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MarathiLangEditor(); // Launch the editor
            }
        });
    }
}