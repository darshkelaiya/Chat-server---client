import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ChatClientGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private BufferedWriter writer;
    private BufferedReader reader;
    
    public ChatClientGUI() {
        frame = new JFrame("Chat Client");
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        messageField = new JTextField(30);
        sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        
        connectToServer();
        
        
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void connectToServer() {
        try {
            
            Socket socket = new Socket("127.0.0.1", 8080);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            
            new Thread(new IncomingMessageHandler()).start();
        } catch (IOException e) {
            chatArea.append("Unable to connect to server\n");
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            try {
                writer.write(message + "\n");
                writer.flush();
                messageField.setText("");
            } catch (IOException e) {
                chatArea.append("Failed to send message\n");
                e.printStackTrace();
            }
        }
    }

    private class IncomingMessageHandler implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    chatArea.append("Server: " + message + "\n");
                }
            } catch (IOException e) {
                chatArea.append("Connection to server lost\n");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClientGUI::new);
    }
}
