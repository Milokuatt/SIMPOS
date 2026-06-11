package form;

import model.Pengguna;

import javax.swing.*;
import java.awt.*;

public class DashboardPengguna extends JFrame {

    private JLabel lblNama;
    private JButton btnBalita;
    private JButton btnIbuHamil;
    private JButton btnRiwayat;
    private JButton btnJadwal;
    private JButton btnLogout;

    public DashboardPengguna() {

        setTitle("SIMPOS - Dashboard Keluarga");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        buatHeader();
        buatSidebar();
        buatKonten();

        setVisible(true);
    }

    private void buatHeader() {

        JPanel header = new JPanel();

        header.setPreferredSize(
                new Dimension(0,70));

        lblNama = new JLabel(
                "Dashboard Keluarga");

        lblNama.setFont(
                new Font(
                "Arial",
                Font.BOLD,
                24));

        header.add(lblNama);

        add(header, BorderLayout.NORTH);
    }

    private void buatSidebar() {

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(
                new Dimension(250,0));

        sidebar.setLayout(
                new GridLayout(6,1,10,10));

        btnBalita =
                new JButton("Data Balita");

        btnIbuHamil =
                new JButton("Data Ibu Hamil");

        btnRiwayat =
                new JButton("Riwayat Pemeriksaan");

        btnJadwal =
                new JButton("Jadwal Posyandu");

        btnLogout =
                new JButton("Logout");

        sidebar.add(btnBalita);
        sidebar.add(btnIbuHamil);
        sidebar.add(btnRiwayat);
        sidebar.add(btnJadwal);
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        btnLogout.addActionListener(e -> {

            new Login();

            dispose();
        });
    }

    private void buatKonten() {

        JPanel content =
                new JPanel(
                new GridLayout(2,2,20,20));

        content.add(
                createCard(
                "Jadwal Posyandu",
                "20 Juni 2026"));

        content.add(
                createCard(
                "Pengumuman",
                "Imunisasi Campak tersedia"));

        content.add(
                createCard(
                "Data Balita",
                "Lihat Data Balita"));

        content.add(
                createCard(
                "Data Ibu Hamil",
                "Lihat Data Ibu Hamil"));

        add(content, BorderLayout.CENTER);
    }

    private JPanel createCard(
            String title,
            String value) {

        JPanel panel =
                new JPanel(
                new BorderLayout());

        panel.setBorder(
                BorderFactory.createTitledBorder(
                title));

        JLabel lbl =
                new JLabel(
                value,
                SwingConstants.CENTER);

        lbl.setFont(
                new Font(
                "Arial",
                Font.BOLD,
                18));

        panel.add(lbl);

        return panel;
    }
}