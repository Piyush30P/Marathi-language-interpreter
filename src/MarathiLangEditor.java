import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MarathiLangEditor extends JFrame {
    private JTextArea codeArea;
    private JTextArea outputArea;
    private JButton runButton, clearButton;
    private JLabel fileLabel, outputLabel;

    public MarathiLangEditor() {
        // Set up the main window
        setTitle("MarathiLang Code Editor");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top section for file name label (like "main.c")
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#F7F9FB"));
        fileLabel = new JLabel("main.mrlang");
        fileLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fileLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.add(fileLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // Set up the code input area
        codeArea = new JTextArea(20, 50);
        codeArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        codeArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        codeArea.setBackground(Color.decode("#F7F9FB"));
        JScrollPane codeScrollPane = new JScrollPane(codeArea);

        // Set up the output area (on the right)
        outputArea = new JTextArea(20, 50);
        outputArea.setEditable(false); // Output area should be read-only
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBackground(Color.decode("#F7F9FB"));
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        // Split pane to separate the code editor and output panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, codeScrollPane, outputScrollPane);
        splitPane.setResizeWeight(0.5); // Give equal space to code and output initially
        add(splitPane, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        runButton = new JButton("Run");
        clearButton = new JButton("Clear");

        // Customizing the buttons (color and font)
        runButton.setBackground(Color.decode("#007BFF"));
        runButton.setForeground(Color.WHITE);
        runButton.setFocusPainted(false);
        runButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        clearButton.setBackground(Color.decode("#F8F9FA"));
        clearButton.setForeground(Color.BLACK);
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Action for the "Run" button
        runButton.addActionListener(new RunCodeListener());

        // Action for the "Clear" button
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText(""); // Clear the output area
            }
        });

        // Add buttons to the bottom panel
        bottomPanel.add(runButton);
        bottomPanel.add(clearButton);

        // Label for the output section
        outputLabel = new JLabel("Output");
        outputLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        outputLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        topPanel.add(outputLabel, BorderLayout.EAST);

        // Add the bottom panel to the main window
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Listener for the Run button
    class RunCodeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String code = codeArea.getText(); // Get the code written in the JTextArea
            try {
                String output = runMarathiInterpreter(code); // Process the code and get output
                outputArea.setText(output); // Display the output in the output area
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage()); // Display any errors
            }
        }
    }

    private String runMarathiInterpreter(String code) throws Exception {
        // Step 1: Tokenize the input code
        MarathiTokenizer tokenizer = new MarathiTokenizer(code);
        List<Token> tokens = tokenizer.tokenize();

        // Step 2: Parse the tokenized input
        MarathiParser parser = new MarathiParser(tokens);
        ASTNode ast = parser.parse();

        // Step 3: Interpret the parsed AST and capture the output
        MarathiInterpreter interpreter = new MarathiInterpreter();
        interpreter.interpret(ast);

        // Return the output captured by the interpreter
        return interpreter.getOutput(); // Use the getOutput() method
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MarathiLangEditor editor = new MarathiLangEditor();
            editor.setVisible(true);
        });
    }
}
