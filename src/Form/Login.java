package form;

import javax.swing.*;
import java.awt.*;

import model.Kader;
import model.Pengguna;
import simpos.Session;

public class Login extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnKeluar;

    public Login() {
        setTitle("SIMPOS - Login");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Mengubah warna background utama menjadi putih bersih
        getContentPane().setBackground(Color.WHITE);

        // --- ELEMEN: JUDUL UTAMA ---
        JLabel lblJudul = new JLabel("SISTEM INFORMASI POSYANDU", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblJudul.setForeground(new Color(192, 57, 43)); // Merah Posyandu Elegan
        lblJudul.setBounds(20, 40, 380, 30);
        add(lblJudul);

        // --- ELEMEN: LABEL USERNAME ---
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUser.setForeground(new Color(127, 140, 141)); // Abu-abu gelap elegan
        lblUser.setBounds(45, 105, 150, 20);
        add(lblUser);

        // --- ELEMEN: FIELD USERNAME ---
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBounds(45, 130, 315, 38); 
        // Menggunakan border garis tipis warna abu-abu agar terlihat bersih
        txtUsername.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(txtUsername);

        // --- ELEMEN: LABEL PASSWORD ---
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPass.setForeground(new Color(127, 140, 141));
        lblPass.setBounds(45, 185, 150, 20);
        add(lblPass);

        // --- ELEMEN: FIELD PASSWORD ---
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBounds(45, 210, 315, 38);
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(txtPassword);

        // --- ELEMEN: TOMBOL LOGIN (PRIMARY BUTTON) ---
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(214, 48, 49)); // Warna Merah Cerah/Hidup
        btnLogin.setForeground(Color.WHITE); // Teks warna putih agar kontras tinggi
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false); // Menghilangkan border 3D bawaan OS yang kaku
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Kursor tangan saat di-hover
        btnLogin.setBounds(45, 275, 315, 42); 
        add(btnLogin);

        // --- ELEMEN: TOMBOL KELUAR (TEXT LINK) ---
        btnKeluar = new JButton("Keluar Aplikasi");
        btnKeluar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnKeluar.setForeground(new Color(149, 165, 166)); // Abu-abu netral
        btnKeluar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKeluar.setBorderPainted(false);
        btnKeluar.setContentAreaFilled(false); // Membuat tombol transparan seperti link web
        btnKeluar.setFocusPainted(false);
        btnKeluar.setBounds(45, 335, 315, 25);
        add(btnKeluar);

        // --- ACTION LISTENERS ---
        btnLogin.addActionListener(e -> login());
        btnKeluar.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void login() {
        String user = txtUsername.getText().trim();
        String pass = String.valueOf(txtPassword.getPassword());

        if(user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password wajib diisi");
            return;
        }

        Kader kader = new Kader();
        kader.setUsername(user);
        kader.setPassword(pass);

        if(kader.login()) {
            JOptionPane.showMessageDialog(this, "Login Admin Berhasil");
            new DashboardKader();
            dispose();
            return;
        }

        Pengguna pengguna = new Pengguna();
        pengguna.setNik(user);
        pengguna.setPassword(pass);

        if(pengguna.login()) {
            Session.nikLogin = pengguna.getNik();
            Session.namaLogin = pengguna.getNama();
            JOptionPane.showMessageDialog(this, "Login Pengguna Berhasil");
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Username atau Password Salah");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}