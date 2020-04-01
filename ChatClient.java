import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ChatClient {
    private JPanel mainPanel;

    private JTextArea chatTextArea;

    private JButton photoButton;
    private JButton fileButton;
    private JLabel charCount;

    private JTextArea messageTextField;
    private final static String messagePlaceholder = "Aa";
    private final int maxChars = messageTextField.getColumns() * messageTextField.getRows();
    private JButton sendButton;
    private boolean firstMessageSent = false;

    private Chat chat;
    private User user;

    private void sendMessage() {
        String newMessage = messageTextField.getText();
        if (!Pattern.matches("\\s*", newMessage)) {
            newMessage = newMessage.trim();
            chat.addMessage(user, newMessage);
            System.out.println(chat.getChatMessages());
            chatTextArea.setText(chat.getChatMessages());
            messageTextField.setText("");
        }
        updateCharCount();
    }

    private void firstMessageSent() {
        if (!firstMessageSent) {
            messageTextField.setText("");
        }
        firstMessageSent = true;
    }

    private void updateCharCount() {
        charCount.setText(MessageFormat.format("{0}/{1}", messageTextField.getText().length(), maxChars));
    }

    private void Listeners() {
        // send on SEND
        sendButton.addActionListener(actionEvent -> sendMessage());

        messageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // more than the max chars which can fit in the textArea
                String m = messageTextField.getText();
                if (m.length() > maxChars) {
                    messageTextField.setText(m.substring(0, maxChars));
                }
                // copy pasting text with newlines to trick the textArea
                if (messageTextField.getLineCount() > 2) {
                    messageTextField.setText(messageTextField.getText().trim());
                }
                // spamming newlines or tabs
                messageTextField.setText(messageTextField.getText().replaceAll("\n|\t", ""));

                // send on ENTER
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }

                // character count label
                updateCharCount();
            }
        });

        // clear initial placeholder
        messageTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                firstMessageSent();
            }
        });

        ActionListener uploadNotImplemented = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showConfirmDialog((Component) null, "Upload not implemented.","Not Implemented", JOptionPane.DEFAULT_OPTION);
            }
        };

        fileButton.addActionListener(uploadNotImplemented);
        photoButton.addActionListener(uploadNotImplemented);
    }

    private static void logInit() throws IOException {
        String logDir = ".\\logs";
        File log = new File(logDir);
        boolean f = log.mkdirs();

        FileHandler runFileHandler = new FileHandler(".\\logs\\run.log");
        Logger run = java.util.logging.Logger.getLogger(logs.RUN.toString());
        run.addHandler(runFileHandler);

        FileHandler errorFileHandler = new FileHandler(".\\logs\\err.log");
        Logger err = java.util.logging.Logger.getLogger(logs.ERROR.toString());
        err.addHandler(errorFileHandler);

        if (f) {
            run.log(Level.INFO, "Created logs directory: " + log.getCanonicalPath());
        }
    }

    public ChatClient(Chat chat, User user) {
        this.chat = chat;
        this.user = user;
        try {
            logInit();
            java.util.logging.Logger.getLogger(logs.RUN.toString()).log(Level.INFO, "Initializing app.");

            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            // Configurables
            String title = "Post It - Chat Client";
            int width = 500;
            int height = 500;

            JFrame frame = new JFrame(title);
            mainPanel.setPreferredSize(new Dimension(width, height));
            frame.setContentPane(mainPanel);
            messageTextField.setText(messagePlaceholder);

            chatTextArea.setEditable(false);

            Listeners();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setMinimumSize(new Dimension(width, height + (int)(height * 0.1)));
            frame.pack();
            Dimension resolution =  Toolkit.getDefaultToolkit().getScreenSize();

            // Height set slightly above center of screen
            int heightPlacement = (((int)resolution.getHeight() - height ) / 2 ) - (int)(height * 0.2);
            int widthPlacement = ((int)resolution.getWidth() - width ) / 2;
            frame.setLocation(widthPlacement, heightPlacement);

            frame.setVisible(true);

            java.util.logging.Logger.getLogger(logs.RUN.toString()).log(Level.INFO, "Done initializing app.");

        } catch ( IOException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException
                ex) {
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(logs.ERROR.toString()).log(Level.SEVERE, ex.toString());
        }

    }

}
