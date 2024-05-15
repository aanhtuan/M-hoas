package MH;

import javax.swing.*;

public class Welcome extends javax.swing.JFrame {

    public Welcome() {
        initComponents();
    }

    private void initComponents() {
        // Các thành phần giao diện được khởi tạo ở đây
        JLabel welcomeLabel = new JLabel("Welcome to our application!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome");
        setSize(300, 200);
        setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Welcome().setVisible(true);
            }
        });
    }
}