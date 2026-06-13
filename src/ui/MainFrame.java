package simpos.ui;

import simpos.util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JLabel lblActiveMenu;

    public MainFrame() {
        setTitle("SIMPOS - Sistem Informasi Posyandu");
        setSize(1200, 750);
        setMinimumSize(new Dimension(1024, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildSidebar(), BorderLayout.WEST);
        add(buildHeader(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        showPanel("dashboard");
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        header.setPreferredSize(new Dimension(0, 56));

        lblActiveMenu = new JLabel("  Dashboard");
        lblActiveMenu.setFont(Theme.FONT_HEADER);
        lblActiveMenu.setForeground(Theme.TEXT_DARK);
        header.add(lblActiveMenu, BorderLayout.WEST);

        JLabel lblUser = new JLabel(Session.getCurrentUser().getNama() + " (" + Session.getCurrentUser().getRole() + ")  ");
        lblUser.setFont(Theme.FONT_NORMAL);
        lblUser.setForeground(Color.GRAY);
        header.add(lblUser, BorderLayout.EAST);

        return header;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Theme.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setBorder(BorderFactory.createEmptyBorder(20, 16, 20, 16));
        JLabel brandTitle = new JLabel("\uD83C\uDFE5  SIMPOS");
        brandTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        brandTitle.setForeground(Color.WHITE);
        JLabel brandSub = new JLabel("Posyandu Sehat Ceria");
        brandSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        brandSub.setForeground(new Color(180, 220, 210));
        brand.add(brandTitle);
        brand.add(Box.createVerticalStrut(4));
        brand.add(brandSub);

        sidebar.add(brand);
        sidebar.add(Box.createVerticalStrut(10));

        String role = Session.getCurrentUser().getRole();

        sidebar.add(menuItem("dashboard", "\u2302  Dashboard"));

        if (role.equals("admin")) {
            sidebar.add(menuItem("pengguna", "\uD83D\uDC65  Data Pengguna"));
        }

        if (role.equals("admin") || role.equals("kader")) {
            sidebar.add(menuItem("balita", "\uD83D\uDC76  Data Balita"));
            sidebar.add(menuItem("imunisasi", "\uD83D\uDC89  Imunisasi"));
            sidebar.add(menuItem("pemeriksaan", "\uD83E\uDE7A  Pemeriksaan"));
        } else if (role.equals("ortu")) {
            sidebar.add(menuItem("balita", "\uD83D\uDC76  Data Anak"));
            sidebar.add(menuItem("imunisasi", "\uD83D\uDC89  Riwayat Imunisasi"));
            sidebar.add(menuItem("pemeriksaan", "\uD83E\uDE7A  Riwayat Pemeriksaan"));
        }

        sidebar.add(menuItem("jadwal", "\uD83D\uDCC5  Jadwal Posyandu"));
        sidebar.add(menuItem("pengumuman", "\uD83D\uDCE2  Pengumuman"));

        sidebar.add(Box.createVerticalGlue());

        JButton btnLogout = new JButton("\u23FB  Logout");
        btnLogout.setFont(Theme.FONT_MENU);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(Theme.DANGER);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        btnLogout.setFocusPainted(false);
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> doLogout());

        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(10));

        return sidebar;
    }

    private JButton menuItem(String key, String label) {
        JButton btn = new JButton(label);
        btn.setFont(Theme.FONT_MENU);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Theme.SIDEBAR);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(Theme.SIDEBAR_HOVER); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Theme.SIDEBAR); }
        });

        btn.addActionListener(e -> {
            showPanel(key);
            lblActiveMenu.setText("  " + label.substring(label.indexOf(' ') + 1).trim());
        });

        return btn;
    }

    private JPanel buildContent() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Theme.BG);

        String role = Session.getCurrentUser().getRole();

        contentPanel.add(new DashboardPanel(this), "dashboard");

        if (role.equals("admin")) {
            contentPanel.add(new PenggunaPanel(), "pengguna");
        }

        if (role.equals("admin") || role.equals("kader")) {
            contentPanel.add(new BalitaPanel(), "balita");
            contentPanel.add(new ImunisasiPanel(), "imunisasi");
            contentPanel.add(new PemeriksaanPanel(), "pemeriksaan");
            contentPanel.add(new JadwalPanel(true), "jadwal");
            contentPanel.add(new PengumumanPanel(true), "pengumuman");
        } else if (role.equals("ortu")) {
            contentPanel.add(new BalitaOrtuPanel(), "balita");
            contentPanel.add(new ImunisasiOrtuPanel(), "imunisasi");
            contentPanel.add(new PemeriksaanOrtuPanel(), "pemeriksaan");
            contentPanel.add(new JadwalPanel(false), "jadwal");
            contentPanel.add(new PengumumanPanel(false), "pengumuman");
        } else {
            contentPanel.add(new JadwalPanel(false), "jadwal");
            contentPanel.add(new PengumumanPanel(false), "pengumuman");
        }

        return contentPanel;
    }

    public void showPanel(String key) {
        cardLayout.show(contentPanel, key);
        for (Component c : contentPanel.getComponents()) {
            if (c.isVisible() && c instanceof Refreshable) {
                ((Refreshable) c).refresh();
            }
        }
    }

    private void doLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin logout?", "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.logout();
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        }
    }

    public interface Refreshable {
        void refresh();
    }
}
