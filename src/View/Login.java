package View;

import controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Login extends JFrame {
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin;

    public Login() {
        setTitle("Đăng nhập hệ thống");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Tên đăng nhập:"));
        tfUsername = new JTextField();
        add(tfUsername);

        add(new JLabel("Mật khẩu:"));
        tfPassword = new JPasswordField();
        add(tfPassword);

        btnLogin = new JButton("Đăng nhập");
        add(btnLogin);

        // ô trống để căn lề
        add(new JLabel());

        btnLogin.addActionListener(e -> {
            String username = tfUsername.getText();
            String password = new String(tfPassword.getPassword());

            int result = AuthController.kiemTraDangNhap(username, password);

            if (result == 1) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công (Admin)");
                new Main().setVisible(true); // ví dụ admin vào quản lý nhân viên
                this.dispose();
            } else if (result == 2) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công (Nhân viên)");
                new NVienView().setVisible(true); // ví dụ nhân viên vào xe máy
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           new Login().setVisible(true);
       });
    }
}
