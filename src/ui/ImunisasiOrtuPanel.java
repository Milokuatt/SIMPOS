package simpos.ui;

import simpos.dao.ImunisasiDAO;
import simpos.model.Imunisasi;
import simpos.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ImunisasiOrtuPanel extends JPanel implements MainFrame.Refreshable {

    private final ImunisasiDAO dao = new ImunisasiDAO();
    private DefaultTableModel model;
    private JTable table;

    public ImunisasiOrtuPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel info = new JLabel("Riwayat imunisasi anak Anda.");
        info.setFont(Theme.FONT_NORMAL);
        info.setForeground(Color.GRAY);
        info.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        add(info, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nama Anak", "Jenis Imunisasi", "Tanggal", "Usia (bln)", "Petugas", "Keterangan"}, 0) {
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
        List<Imunisasi> list = dao.getByOrtuNama(namaOrtu);
        for (Imunisasi im : list) {
            model.addRow(new Object[]{im.getId(), im.getNamaBalita(), im.getJenisImunisasi(),
                    im.getTanggal(), im.getUsiaBulan(), im.getPetugas(), im.getKeterangan()});
        }
        if (list.isEmpty()) {
            model.addRow(new Object[]{"-", "Belum ada data imunisasi", "", "", "", "", ""});
        }
    }

    @Override
    public void refresh() {
        loadData();
    }
}
