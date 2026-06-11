package form;

import javax.swing.*;
import java.awt.*;

public class DashboardKader extends JFrame {

    private JButton btnDashboard;
    private JButton btnBalita;
    private JButton btnIbuHamil;
    private JButton btnImunisasi;
    private JButton btnPemeriksaan;
    private JButton btnJadwal;
    private JButton btnPengumuman;
    private JButton btnLogout;

    public DashboardKader() {

        setTitle("SIMPOS Dashboard Admin");
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // --- ELEMEN 1: HEADER (ATAS) ---
        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(0, 75));
        header.setBackground(Color.WHITE); // Latar belakang putih bersih
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
        // Menambahkan border bawah tipis sebagai pembatas linear yang estetik
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(240, 240, 240)));

        JLabel title = new JLabel("SIMPOS - Dashboard Admin");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(44, 62, 80)); // Warna charcoal gelap elegan
        header.add(title);

        add(header, BorderLayout.NORTH);

        // --- ELEMEN 2: SIDEBAR NAVIGASI (KIRI) ---
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBackground(new Color(192, 57, 43)); // Warna Merah Utama Posyandu
        // Memberi jarak bagian atas agar tombol pertama tidak menempel ke header
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 15));
        sidebar.setLayout(new GridLayout(8, 1, 0, 12)); // Spasi antar tombol vertikal

        // Inisialisasi Tombol Menu
        btnDashboard = createMenuButton("Dashboard");
        btnBalita = createMenuButton("Data Balita");
        btnIbuHamil = createMenuButton("Data Ibu Hamil");
        btnImunisasi = createMenuButton("Imunisasi");
        btnPemeriksaan = createMenuButton("Pemeriksaan");
        btnJadwal = createMenuButton("Jadwal");
        btnPengumuman = createMenuButton("Pengumuman");
        btnLogout = createMenuButton("Logout");

        // Menandai menu aktif saat ini (Dashboard) dengan warna merah lebih terang
        btnDashboard.setBackground(new Color(231, 76, 60));
        btnDashboard.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // Khusus tombol logout diberi warna merah tua/gelap agar kontras
        btnLogout.setBackground(new Color(150, 40, 27));

        sidebar.add(btnDashboard);
        sidebar.add(btnBalita);
        sidebar.add(btnIbuHamil);
        sidebar.add(btnImunisasi);
        sidebar.add(btnPemeriksaan);
        sidebar.add(btnJadwal);
        sidebar.add(btnPengumuman);
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // --- ELEMEN 3: AREA KONTEN (KANAN) ---
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(248, 249, 250)); // Background abu-abu sangat muda (Soft)
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- SUB-ELEMEN: KARTU STATISTIK (ATAS KANAN) ---
        JPanel statistik = new JPanel(new GridLayout(1, 4, 25, 25));
        statistik.setOpaque(false); // Mengikuti background panel induk (content)

        // Membuat kartu statistik berwarna-warni pastel yang hidup
        statistik.add(createCard("Balita", "25", new Color(231, 76, 60)));       // Coral Red
        statistik.add(createCard("Ibu Hamil", "12", new Color(230, 126, 34)));   // Orange Soft
        statistik.add(createCard("Kader", "5", new Color(241, 196, 15)));        // Yellow Soft
        statistik.add(createCard("Pelayanan", "130", new Color(46, 204, 113)));  // Green Soft

        content.add(statistik, BorderLayout.NORTH);

        // --- SUB-ELEMEN: PENGUMUMAN TEXT AREA (BAWAH KANAN) ---
        JPanel pengumumanPanel = new JPanel(new BorderLayout());
        pengumumanPanel.setOpaque(false);
        pengumumanPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

        JLabel lblInfoTitle = new JLabel("Papan Pengumuman");
        lblInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblInfoTitle.setForeground(new Color(44, 62, 80));
        lblInfoTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        pengumumanPanel.add(lblInfoTitle, BorderLayout.NORTH);

        JTextArea informasi = new JTextArea();
        informasi.setEditable(false);
        informasi.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        informasi.setLineWrap(true);
        informasi.setWrapStyleWord(true);
        informasi.setBackground(Color.WHITE);
        informasi.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        informasi.setText(
                "Pengumuman Terbaru\n"
                + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"
                + "• Posyandu bulan ini dilaksanakan serentak pada tanggal 20.\n"
                + "• Stok vaksin dan Imunisasi Campak sudah tersedia di gudang logistik.");

        JScrollPane scrollPane = new JScrollPane(informasi);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        pengumumanPanel.add(scrollPane, BorderLayout.CENTER);

        content.add(pengumumanPanel, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);

        // ==========================================
        // ACTION LISTENERS (Logika Asli Tidak Berubah)
        // ==========================================
        btnBalita.addActionListener(e -> new FormBalita());
        btnIbuHamil.addActionListener(e -> new FormIbuHamil());
        btnImunisasi.addActionListener(e -> new FormImunisasi());
        btnPemeriksaan.addActionListener(e -> new FormPemeriksaan());
        btnJadwal.addActionListener(e -> new FormJadwal());
        btnPengumuman.addActionListener(e -> new FormPengumuman());

        btnLogout.addActionListener(e -> {
            int pilih = JOptionPane.showConfirmDialog(
                    this,
                    "Logout dari sistem?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);

            if (pilih == JOptionPane.YES_OPTION) {
                new Login();
                dispose();
            }
        });

        setVisible(true);
    }

    // --- FUNGSI PEMBANTU: MEMBUAT TOMBOL MENU SIDEBAR ---
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(192, 57, 43)); // Senada dengan background sidebar
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false); // Menghilangkan garis border kaku khas tombol lama
        btn.setContentAreaFilled(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Kursor berubah jadi tangan saat di-hover
        btn.setHorizontalAlignment(SwingConstants.LEFT); // Teks rata kiri khas sidebar modern
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Padding teks dari kiri
        return btn;
    }

    // --- FUNGSI PEMBANTU: MEMBUAT KARTU STATISTIK MODERN ---
    private JPanel createCard(String title, String value, Color accentColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        // Memberi border kiri setebal 5px dengan warna aksen agar tampak hidup
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, accentColor),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(127, 140, 141)); // Abu-abu elegan

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(new Color(44, 62, 80)); // Angka berwarna tebal/gelap

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblValue, BorderLayout.CENTER);

        return panel;
    }
}