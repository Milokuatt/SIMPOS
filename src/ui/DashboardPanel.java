package simpos.ui;

import simpos.dao.*;
import simpos.model.*;
import simpos.util.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel implements MainFrame.Refreshable {

    private JLabel lblTotalBalita, lblTotalImunisasi, lblTotalPemeriksaan, lblTotalJadwal;
    private JPanel pengumumanList;
    private JPanel jadwalList;
    private final boolean isOrtu;

    public DashboardPanel(MainFrame mainFrame) {
        isOrtu = "ortu".equals(Session.getCurrentUser().getRole());

        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel top = new JPanel(new GridLayout(1, 4, 16, 0));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        lblTotalBalita = new JLabel("0");
        lblTotalImunisasi = new JLabel("0");
        lblTotalPemeriksaan = new JLabel("0");
        lblTotalJadwal = new JLabel("0");

        String titleBalita = isOrtu ? "Jumlah Anak" : "Total Balita";
        String titleImunisasi = isOrtu ? "Riwayat Imunisasi" : "Total Imunisasi";
        String titlePemeriksaan = isOrtu ? "Riwayat Pemeriksaan" : "Total Pemeriksaan";

        top.add(statCard(titleBalita, lblTotalBalita, "\uD83D\uDC76", new Color(0, 121, 107)));
        top.add(statCard(titleImunisasi, lblTotalImunisasi, "\uD83D\uDC89", new Color(33, 150, 243)));
        top.add(statCard(titlePemeriksaan, lblTotalPemeriksaan, "\uD83E\uDE7A", new Color(156, 39, 176)));
        top.add(statCard("Jadwal Mendatang", lblTotalJadwal, "\uD83D\uDCC5", new Color(255, 152, 0)));

        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 16, 0));
        center.setOpaque(false);

        // Pengumuman terbaru
        JPanel pengumumanPanel = card("Pengumuman Terbaru");
        pengumumanList = new JPanel();
        pengumumanList.setLayout(new BoxLayout(pengumumanList, BoxLayout.Y_AXIS));
        pengumumanList.setOpaque(false);
        JScrollPane sp1 = new JScrollPane(pengumumanList);
        sp1.setBorder(null);
        sp1.getVerticalScrollBar().setUnitIncrement(16);
        pengumumanPanel.add(sp1, BorderLayout.CENTER);
        center.add(pengumumanPanel);

        // Jadwal mendatang
        JPanel jadwalPanel = card("Jadwal Posyandu Mendatang");
        jadwalList = new JPanel();
        jadwalList.setLayout(new BoxLayout(jadwalList, BoxLayout.Y_AXIS));
        jadwalList.setOpaque(false);
        JScrollPane sp2 = new JScrollPane(jadwalList);
        sp2.setBorder(null);
        sp2.getVerticalScrollBar().setUnitIncrement(16);
        jadwalPanel.add(sp2, BorderLayout.CENTER);
        center.add(jadwalPanel);

        add(center, BorderLayout.CENTER);

        refresh();
    }

    private JPanel statCard(String title, JLabel valueLabel, String icon, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(Theme.FONT_NORMAL);
        lblTitle.setForeground(Color.GRAY);
        lblTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);

        right.add(valueLabel);
        right.add(lblTitle);

        panel.add(lblIcon, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);

        return panel;
    }

    private JPanel card(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(Theme.FONT_HEADER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        return panel;
    }

    @Override
    public void refresh() {
        BalitaDAO balitaDAO = new BalitaDAO();
        ImunisasiDAO imunisasiDAO = new ImunisasiDAO();
        PemeriksaanDAO pemeriksaanDAO = new PemeriksaanDAO();
        JadwalDAO jadwalDAO = new JadwalDAO();
        PengumumanDAO pengumumanDAO = new PengumumanDAO();

        if (isOrtu) {
            String namaOrtu = Session.getCurrentUser().getNama();
            lblTotalBalita.setText(String.valueOf(balitaDAO.getByOrtuNama(namaOrtu).size()));
            lblTotalImunisasi.setText(String.valueOf(imunisasiDAO.getByOrtuNama(namaOrtu).size()));
            lblTotalPemeriksaan.setText(String.valueOf(pemeriksaanDAO.getByOrtuNama(namaOrtu).size()));
        } else {
            lblTotalBalita.setText(String.valueOf(balitaDAO.count()));
            lblTotalImunisasi.setText(String.valueOf(imunisasiDAO.count()));
            lblTotalPemeriksaan.setText(String.valueOf(pemeriksaanDAO.count()));
        }

        List<Jadwal> upcoming = jadwalDAO.getUpcoming(5);
        lblTotalJadwal.setText(String.valueOf(upcoming.size()));

        jadwalList.removeAll();
        if (upcoming.isEmpty()) {
            jadwalList.add(emptyLabel("Tidak ada jadwal mendatang."));
        } else {
            for (Jadwal j : upcoming) {
                jadwalList.add(infoItem(j.getNamaKegiatan(),
                        j.getTanggal() + "  " + (j.getWaktu() == null ? "" : j.getWaktu()) + "  @ " + j.getLokasi()));
            }
        }
        jadwalList.revalidate();
        jadwalList.repaint();

        List<Pengumuman> pengumumanAktif = pengumumanDAO.getActive(5);
        pengumumanList.removeAll();
        if (pengumumanAktif.isEmpty()) {
            pengumumanList.add(emptyLabel("Belum ada pengumuman aktif."));
        } else {
            for (Pengumuman p : pengumumanAktif) {
                pengumumanList.add(infoItem(p.getJudul(), p.getTanggal() + "  -  " + truncate(p.getIsi(), 80)));
            }
        }
        pengumumanList.revalidate();
        pengumumanList.repaint();
    }

    private String truncate(String s, int n) {
        if (s == null) return "";
        return s.length() > n ? s.substring(0, n) + "..." : s;
    }

    private JLabel emptyLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Theme.FONT_NORMAL);
        lbl.setForeground(Color.GRAY);
        lbl.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        return lbl;
    }

    private JPanel infoItem(String title, String subtitle) {
        JPanel item = new JPanel();
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
        item.setOpaque(false);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(8, 0, 8, 0)));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(Theme.FONT_BOLD);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        item.add(lblTitle);
        item.add(lblSub);
        return item;
    }
}
