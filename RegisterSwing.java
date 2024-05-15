package MH;

import javax.swing.*;

import MH.Signin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.sql.*;

public class RegisterSwing extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnRegister;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public RegisterSwing() {
        setTitle("Đăng ký tài khoản");
        setSize(704, 444);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        tfUsername = new JTextField();
        tfUsername.setBackground(new Color(0, 172, 44));
        tfUsername.setBounds(164, 237, 268, 31);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblPassword.setBounds(164, 249, 87, 60);
        pfPassword = new JPasswordField();
        pfPassword.setBackground(new Color(0, 172, 44));
        pfPassword.setBounds(164, 297, 268, 31);
        btnRegister = new JButton("Đăng ký");
        btnRegister.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnRegister.setBackground(new Color(255, 128, 128));
        btnRegister.setBounds(462, 296, 146, 31);
        panel.setLayout(null);
        panel.add(tfUsername);
        panel.add(lblPassword);
        panel.add(pfPassword);
        JLabel label = new JLabel();
        label.setBounds(0, 206, 251, 103);
        panel.add(label);
        panel.add(btnRegister);

        getContentPane().add(panel);
        JLabel lblUsername_1 = new JLabel("Click to register");
        lblUsername_1.setForeground(Color.BLACK);
        lblUsername_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblUsername_1.setBounds(462, 262, 251, 44);
        panel.add(lblUsername_1);
        JLabel lblUsername_1_1 = new JLabel("Password more than 3 characters");
        lblUsername_1_1.setForeground(Color.BLACK);
        lblUsername_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblUsername_1_1.setBounds(462, 236, 251, 31);
        panel.add(lblUsername_1_1);
        JLabel lblUsername_2 = new JLabel("Username");
        lblUsername_2.setForeground(Color.BLACK);
        lblUsername_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblUsername_2.setBounds(117, 175, 251, 49);
        panel.add(lblUsername_2);
        JLabel lblUsername_1_1_1 = new JLabel("Do not enter special values");
        lblUsername_1_1_1.setForeground(Color.BLACK);
        lblUsername_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblUsername_1_1_1.setBounds(462, 206, 251, 31);
        panel.add(lblUsername_1_1_1);
        
        
        
        
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ASUS\\Pictures\\Screenshots\\Screenshot 2024-01-23 160734.png"));
        lblNewLabel.setBounds(41, 41, 680, 397);
        panel.add(lblNewLabel);
        
       
        
        
        
        
        

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = new String(pfPassword.getPassword());

                // Kiểm tra username và password ở đây
                if (isValidUsername(username) && isValidPassword(password)) {
                    if (isUsernameAvailable(username)) {
                        if (registerAccount(username, password)) {
                            JOptionPane.showMessageDialog(null, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            openLoginSwing();
                        } else {
                            JOptionPane.showMessageDialog(null, "Đăng ký không thành công. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại. Vui lòng chọn tên đăng nhập khác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Đăng ký không hợp lệ. Vui lòng kiểm tra lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            // Thiết lập kết nối tới cơ sở dữ liệu
            String url = "jdbc:mysql://localhost:3306/managementusers";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);

            preparedStatement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isUsernameAvailable(String username) {
        try {
            // Kiểm tra xem tên đăng nhập đã tồn tại trong cơ sở dữ liệu chưa
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count == 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean registerAccount(String username, String password) {
        try {
            // Mã hoá mật khẩu
            String hashedPassword = hashPassword(password);

            // Thêm tài khoản vào cơ sở dữ liệu
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            // Sử dụng MD5 để mã hoá mật khẩu
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();

            // Chuyển đổi byte array sang dạng hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isValidUsername(String username) {
        // Kiểm tra username theo các quy tắc của bạn
        return !username.isEmpty() && username.length() >= 3;
    }

    private boolean isValidPassword(String password) {
        // Kiểm tra password theo các quy tắc của bạn
        return !password.isEmpty() && password.length() >= 6;
    }

    private void openLoginSwing() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Signin loginSwing = new Signin();
                loginSwing.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegisterSwing registerSwing = new RegisterSwing();
                registerSwing.setVisible(true);
            }
        });
    }
}