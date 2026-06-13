package simpos.ui;

import simpos.dao.PenggunaDAO;
import simpos.model.Pengguna;
import simpos.util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("SIMPOS - Login");
        setSize(1000, 600);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildUI();
    }

    private void buildUI() {

        // =========================
        // PANEL KIRI
        // =========================
        JPanel left = new JPanel();
        left.setBackground(Theme.PRIMARY_DARK);
        left.setPreferredSize(new Dimension(420, 0));
        left.setLayout(new GridBagLayout());

        JPanel leftContent = new JPanel();
        leftContent.setOpaque(false);
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("🏥");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("SIMPOS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Sistem Informasi Posyandu");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(new Color(220, 240, 235));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel desc = new JLabel(
                "<html><div style='text-align:center; width:280px;'>"
                + "Kelola data balita, imunisasi, pemeriksaan, jadwal, dan pengumuman posyandu dengan mudah."
                + "</div></html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        desc.setForeground(new Color(200, 230, 220));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftContent.add(logo);
        leftContent.add(Box.createVerticalStrut(15));
        leftContent.add(title);
        leftContent.add(Box.createVerticalStrut(5));
        leftContent.add(subtitle);
        leftContent.add(Box.createVerticalStrut(20));
        leftContent.add(desc);
        leftContent.add(Box.createVerticalStrut(35));

        String[] fitur = {
            "✓ Data Balita",
            "✓ Imunisasi",
            "✓ Pemeriksaan",
            "✓ Jadwal Posyandu",
            "✓ Pengumuman"
        };

        for (String f : fitur) {
            JLabel lbl = new JLabel(f);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            lbl.setForeground(Color.WHITE);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

            leftContent.add(lbl);
            leftContent.add(Box.createVerticalStrut(10));
        }

        leftContent.add(Box.createVerticalStrut(25));

        JLabel footer = new JLabel("© 2026 SIMPOS");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(new Color(180, 220, 210));
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftContent.add(footer);

        left.add(leftContent);

        // =========================
        // PANEL KANAN
        // =========================
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(new Color(248, 249, 250));

        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBackground(Color.WHITE);

        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        1, 1, 3, 3,
                        new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        JLabel formTitle = new JLabel("Selamat Datang 👋");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        formTitle.setForeground(Theme.TEXT_DARK);

        JLabel formSubtitle = new JLabel("Masuk untuk mengelola data posyandu");
        formSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        formSubtitle.setForeground(Color.GRAY);

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));

        txtUsername = new JTextField();
        styleField(txtUsername);

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 15));

        txtPassword = new JPasswordField();
        styleField(txtPassword);

        JButton btnLogin = new JButton("Masuk");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setBackground(Theme.PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setPreferredSize(new Dimension(0, 45));

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(Theme.PRIMARY_DARK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(Theme.PRIMARY);
            }
        });

        btnLogin.addActionListener(this::doLogin);

        JLabel hint = new JLabel("Posyandu Sehat Ceria");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        hint.setForeground(Color.GRAY);

        gbc.gridy = 0;
        form.add(formTitle, gbc);

        gbc.gridy = 1;
        form.add(formSubtitle, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(25, 0, 4, 0);
        form.add(lblUser, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 8, 0);
        form.add(txtUsername, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 4, 0);
        form.add(lblPass, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 8, 0);
        form.add(txtPassword, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(20, 0, 8, 0);
        form.add(btnLogin, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        form.add(hint, gbc);

        form.setPreferredSize(new Dimension(380, 400));

        txtPassword.addActionListener(this::doLogin);

        right.add(form);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.CENTER);
    }

    private void styleField(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 42));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(
                        5, 10, 5, 10)));

        field.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.PRIMARY, 2),
                        BorderFactory.createEmptyBorder(
                                4, 9, 4, 9)));
            }

            @Override
            public void focusLost(FocusEvent e) {

                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(
                                5, 10, 5, 10)));
            }
        });
    }

    private void doLogin(ActionEvent e) {

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username dan password wajib diisi!",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        PenggunaDAO dao = new PenggunaDAO();
        Pengguna user = dao.login(username, password);

        if (user != null) {

            Session.setCurrentUser(user);

            dispose();

            SwingUtilities.invokeLater(() ->
                    new MainFrame().setVisible(true));

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Username atau password salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);

            txtPassword.setText("");
        }
    }
}