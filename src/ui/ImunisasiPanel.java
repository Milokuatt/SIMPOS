package simpos.ui;

import simpos.dao.BalitaDAO;
import simpos.dao.ImunisasiDAO;
import simpos.model.Balita;
import simpos.model.Imunisasi;
import simpos.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ImunisasiPanel extends JPanel implements MainFrame.Refreshable {

    private final ImunisasiDAO dao = new ImunisasiDAO();
    private final BalitaDAO balitaDAO = new BalitaDAO();
    private DefaultTableModel model;
    private JTable table;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private static final String[] JENIS_IMUNISASI = {
            "BCG", "Hepatitis B", "Polio", "DPT-HB-Hib", "Campak", "MR", "IPV", "Lainnya"
    };

    public ImunisasiPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JButton btnAdd = primaryButton("+ Tambah Data Imunisasi");
        btnAdd.addActionListener(e -> showForm(null));
        toolbar.add(btnAdd, BorderLayout.EAST);
        add(toolbar, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nama Balita", "Jenis Imunisasi", "Tanggal", "Usia (bln)", "Petugas", "Keterangan"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        add(scroll, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        actions.setOpaque(false);
        JButton btnEdit = secondaryButton("Edit");
        btnEdit.addActionListener(e -> editSelected());
        JButton btnDelete = dangerButton("Hapus");
        btnDelete.addActionListener(e -> deleteSelected());
        actions.add(btnEdit);
        actions.add(btnDelete);
        add(actions, BorderLayout.SOUTH);
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

    private JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(Theme.PRIMARY); b.setForeground(Color.WHITE);
        b.setFont(Theme.FONT_BOLD); b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(200, 36));
        return b;
    }

    private JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(33, 150, 243)); b.setForeground(Color.WHITE);
        b.setFont(Theme.FONT_BOLD); b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(100, 36));
        return b;
    }

    private JButton dangerButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(Theme.DANGER); b.setForeground(Color.WHITE);
        b.setFont(Theme.FONT_BOLD); b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(100, 36));
        return b;
    }

    private void loadData() {
        model.setRowCount(0);
        List<Imunisasi> list = dao.getAll();
        for (Imunisasi im : list) {
            model.addRow(new Object[]{im.getId(), im.getNamaBalita(), im.getJenisImunisasi(),
                    im.getTanggal(), im.getUsiaBulan(), im.getPetugas(), im.getKeterangan()});
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        Imunisasi selected = dao.getAll().stream().filter(i -> i.getId() == id).findFirst().orElse(null);
        showForm(selected);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Hapus data imunisasi ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadData();
        }
    }

    private void showForm(Imunisasi existing) {
        List<Balita> balitaList = balitaDAO.getAll();
        if (balitaList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada data balita. Tambahkan data balita terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JComboBox<Balita> cboBalita = new JComboBox<>(balitaList.toArray(new Balita[0]));
        if (existing != null) {
            for (Balita b : balitaList) {
                if (b.getId() == existing.getBalitaId()) { cboBalita.setSelectedItem(b); break; }
            }
        }

        JComboBox<String> cboJenis = new JComboBox<>(JENIS_IMUNISASI);
        cboJenis.setEditable(true);
        if (existing != null) cboJenis.setSelectedItem(existing.getJenisImunisasi());

        JTextField txtTanggal = new JTextField(existing != null ? existing.getTanggal() : "");
        txtTanggal.setToolTipText("Format: yyyy-MM-dd");

        JSpinner spnUsia = new JSpinner(new SpinnerNumberModel(existing != null ? existing.getUsiaBulan() : 0, 0, 60, 1));

        JTextField txtPetugas = new JTextField(existing != null ? existing.getPetugas() : Session.getCurrentUser().getNama());
        JTextField txtKeterangan = new JTextField(existing != null ? existing.getKeterangan() : "");

        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.add(new JLabel("Nama Balita:")); panel.add(cboBalita);
        panel.add(new JLabel("Jenis Imunisasi:")); panel.add(cboJenis);
        panel.add(new JLabel("Tanggal (yyyy-MM-dd):")); panel.add(txtTanggal);
        panel.add(new JLabel("Usia (bulan):")); panel.add(spnUsia);
        panel.add(new JLabel("Petugas:")); panel.add(txtPetugas);
        panel.add(new JLabel("Keterangan:")); panel.add(txtKeterangan);

        String title = existing == null ? "Tambah Data Imunisasi" : "Edit Data Imunisasi";
        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String tanggal = txtTanggal.getText().trim();
            if (tanggal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tanggal wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try { SDF.parse(tanggal); } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Format tanggal tidak valid! Gunakan yyyy-MM-dd.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Balita selectedBalita = (Balita) cboBalita.getSelectedItem();
            Imunisasi im = existing != null ? existing : new Imunisasi();
            im.setBalitaId(selectedBalita.getId());
            im.setJenisImunisasi((String) cboJenis.getSelectedItem());
            im.setTanggal(tanggal);
            im.setUsiaBulan((Integer) spnUsia.getValue());
            im.setPetugas(txtPetugas.getText().trim());
            im.setKeterangan(txtKeterangan.getText().trim());

            boolean success = existing == null ? dao.save(im) : dao.update(im);
            if (success) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void refresh() {
        loadData();
    }
}
