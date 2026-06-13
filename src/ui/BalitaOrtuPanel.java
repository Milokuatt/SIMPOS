package simpos.ui;

import simpos.dao.BalitaDAO;
import simpos.model.Balita;
import simpos.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BalitaOrtuPanel extends JPanel implements MainFrame.Refreshable {

    private final BalitaDAO dao = new BalitaDAO();
    private DefaultTableModel model;
    private JTable table;

    public BalitaOrtuPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel info = new JLabel("Data anak yang terhubung dengan akun Anda (berdasarkan nama Orang Tua/Wali).");
        info.setFont(Theme.FONT_NORMAL);
        info.setForeground(Color.GRAY);
        info.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        add(info, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nama", "NIK", "Tgl Lahir", "JK", "Nama Ibu", "Nama Ayah", "Alamat", "No.Telp"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        add(scroll, BorderLayout.CENTER);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setFont(Theme.FONT_NORMAL);
        table.getTableHeader().setFont(Theme.FONT_BOLD);
        table.getTableHeader().setBackground(Theme.PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(224, 242, 241));
        table.getColumnModel().getColumn(0).setMaxWidth(50);
    }

    private void loadData() {
        model.setRowCount(0);
        String namaOrtu = Session.getCurrentUser().getNama();
        List<Balita> list = dao.getByOrtuNama(namaOrtu);
        for (Balita b : list) {
            model.addRow(new Object[]{b.getId(), b.getNama(), b.getNik(), b.getTanggalLahir(),
                    b.getJenisKelamin(), b.getNamaIbu(), b.getNamaAyah(), b.getAlamat(), b.getNoTelp()});
        }
        if (list.isEmpty()) {
            model.addRow(new Object[]{"-", "Tidak ada data anak yang cocok dengan nama Anda", "", "", "", "", "", "", ""});
        }
    }

    @Override
    public void refresh() {
        loadData();
    }
}
