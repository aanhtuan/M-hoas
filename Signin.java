package MH;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.codec.digest.DigestUtils;



public class Signin extends JFrame {
	private JCheckBox cbShowPassword;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JButton btnLogin;
	

	public Signin() {
		
		setBackground(new Color(225, 179, 206));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS\\Desktop\\2250207.png"));
		setTitle("Đăng nhập");
		setSize(719, 528);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblUsername = new JLabel("User Name:");
		lblUsername.setForeground(new Color(0, 0, 64));
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(191, 236, 100, 27);
		panel.add(lblUsername);

		tfUsername = new JTextField(20);
		tfUsername.setForeground(new Color(0, 0, 0));
		tfUsername.setBackground(new Color(225, 179, 206));
		tfUsername.setBounds(191, 284, 378, 33);
		panel.add(tfUsername);

		JLabel lblPassword = new JLabel("Pasword:");
		lblPassword.setForeground(new Color(0, 0, 64));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(191, 343, 85, 27);
		panel.add(lblPassword);

		pfPassword = new JPasswordField(20);
		pfPassword.setBackground(new Color(128, 255, 128));
		pfPassword.setBounds(190, 392, 379, 33);
		panel.add(pfPassword);

		cbShowPassword = new JCheckBox("Show the password");
		cbShowPassword.setForeground(new Color(255, 255, 255));
		cbShowPassword.setFont(new Font("Tahoma", Font.BOLD, 10));
		cbShowPassword.setBackground(new Color(0, 0, 0));
		cbShowPassword.setBounds(196, 429, 150, 19);
		panel.add(cbShowPassword);

		btnLogin = new JButton("Đăng nhập");
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(0, 117, 90));
		btnLogin.setBounds(206, 454, 140, 27);
		panel.add(btnLogin);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\ASUS\\Desktop\\key-sharp-icon.png"));
		lblNewLabel_1.setBounds(140, 384, 50, 48);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ASUS\\Desktop\\people-icon.png"));
		lblNewLabel.setBounds(140, 276, 45, 48);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_4 = new JLabel("SIGN IN");
		lblNewLabel_4.setBackground(new Color(0, 0, 64));
		lblNewLabel_4.setForeground(new Color(128, 0, 0));
		lblNewLabel_4.setFont(new Font("Wide Latin", Font.BOLD | Font.ITALIC, 26));
		lblNewLabel_4.setIcon(null);
		lblNewLabel_4.setBounds(272, 170, 219, 80);
		panel.add(lblNewLabel_4);
		JButton btnngK = new JButton("Đăng kí");
		btnngK.setForeground(new Color(255, 255, 255));
		btnngK.setBackground(new Color(0, 117, 90));
		btnngK.setBounds(406, 454, 140, 27);
		panel.add(btnngK);
		btnngK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegisterSwing menuFrame = new RegisterSwing();
                menuFrame.setVisible(true);
            }
        });

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBackground(new Color(240, 240, 240));
		lblNewLabel_3.setIcon(
				new ImageIcon("C:\\Users\\ASUS\\Desktop\\online-user-login-icon-in-blue-and-green-color-vector.jpg"));
		lblNewLabel_3.setBounds(-144, -144, 924, 606);
		panel.add(lblNewLabel_3);
		
		
		cbShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbShowPassword.isSelected()) {
					pfPassword.setEchoChar((char) 0); // Hiển thị mật khẩu
				} else {
					pfPassword.setEchoChar('\u2022'); // Ẩn mật khẩu
				}
			}
		});


        // Xử lý sự kiện đăng nhập
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = new String(pfPassword.getPassword());
                boolean success = login(username, password);
                if (success) {
                	 openWelcome();
                	 dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Signin loginSwing = new Signin();
				loginSwing.setVisible(true);
			}
		});
	}
	private void openWelcome() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Welcome welcome = new Welcome();
				welcome.setVisible(true);
			}
		});
	}



	private boolean login(String username, String password) {
	    Connection connection = null;
	    try {
	        // Kết nối tới cơ sở dữ liệu
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/managementusers?useSSL=false", "root", "");

	        // Mã hóa mật khẩu trước khi so sánh
	        String hashedPassword = DigestUtils.md5Hex(password);

	        // Tạo câu truy vấn SQL
	        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, username);
	        statement.setString(2, hashedPassword);

	        // Thực thi câu truy vấn
	        ResultSet resultSet = statement.executeQuery();

	        // Kiểm tra kết quả trả về
	        if (resultSet.next()) {
	            // Người dùng tồn tại trong cơ sở dữ liệu
	            return true;
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } finally {
	        // Đảm bảo đóng kết nối sau khi sử dụng
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	    return false;
	}
}