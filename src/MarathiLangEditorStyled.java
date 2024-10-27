import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarathiLangEditorStyled {
    private JTextPane codeEditor;
    private JTextArea outputConsole;
    private JFrame frame;

    public MarathiLangEditorStyled() {
        // Create the main frame
        frame = new JFrame("MarathiLang Code Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);

        // Create the code editor area with styled document for syntax highlighting
        codeEditor = new JTextPane();
        codeEditor.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane codeScrollPane = new JScrollPane(codeEditor);
        codeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        codeScrollPane.setPreferredSize(new Dimension(450, 400));

        // Create the output console area
        outputConsole = new JTextArea();
        outputConsole.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputConsole.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputConsole);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setPreferredSize(new Dimension(450, 400));

        // Create the Run button
        JButton runButton = new JButton("Run");
        runButton.setBackground(Color.BLUE);
        runButton.setForeground(Color.WHITE);
        runButton.setPreferredSize(new Dimension(80, 30));
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCode();
            }
        });

        // Create the Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(80, 30));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearConsole();
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(runButton);
        buttonPanel.add(clearButton);

        // Set up the layout of the frame
        frame.setLayout(new BorderLayout());

        // Top panel: For the "Run" button and code editor
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(codeScrollPane, BorderLayout.CENTER);

        // Add top panel and output console to the frame
        frame.add(topPanel, BorderLayout.WEST);
        frame.add(outputScrollPane, BorderLayout.EAST);

        // Initialize the syntax highlighting
        initSyntaxHighlighting();

        // Make the frame visible
        frame.setVisible(true);
    }

    // Syntax highlighting for MarathiLang code
    private void initSyntaxHighlighting() {
        StyledDocument doc = codeEditor.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();

        // Add styles for different syntax elements
        Style varStyle = sc.addStyle("variable", null);
        StyleConstants.setForeground(varStyle, Color.BLUE);

        Style keywordStyle = sc.addStyle("keyword", null);
        StyleConstants.setForeground(keywordStyle, new Color(128, 0, 128)); // Purple

        Style numberStyle = sc.addStyle("number", null);
        StyleConstants.setForeground(numberStyle, new Color(255, 165, 0)); // Orange

        Style stringStyle = sc.addStyle("string", null);
        StyleConstants.setForeground(stringStyle, Color.GREEN.darker());

        Style commentStyle = sc.addStyle("comment", null);
        StyleConstants.setForeground(commentStyle, Color.GRAY);
    }

    // Function to run the code in the editor
    private void runCode() {
        String code = codeEditor.getText();
        try {
            // This is where you'd run the Marathi code through your interpreter
            // Right now, let's mock an output.
            outputConsole.append("Running code...\n");
            outputConsole.append("Output:\n");
            outputConsole.append("Program completed successfully.\n");
        } catch (Exception e) {
            outputConsole.append("Error: " + e.getMessage() + "\n");
        }
    }

    // Function to clear the output console
    private void clearConsole() {
        outputConsole.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MarathiLangEditorStyled();  // Launch the editor
            }
        });
    }
}
