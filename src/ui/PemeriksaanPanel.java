package simpos.ui;

import simpos.dao.BalitaDAO;
import simpos.dao.PemeriksaanDAO;
import simpos.model.Balita;
import simpos.model.Pemeriksaan;
import simpos.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PemeriksaanPanel extends JPanel implements MainFrame.Refreshable {

    private final PemeriksaanDAO dao = new PemeriksaanDAO();
    private final BalitaDAO balitaDAO = new BalitaDAO();
    private DefaultTableModel model;
    private JTable table;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private static final String[] STATUS_GIZI = {
            "Normal", "Gizi Kurang", "Gizi Buruk", "Gizi Lebih", "Stunting", "Obesitas"
    };

    public PemeriksaanPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JButton btnAdd = primaryButton("+ Tambah Pemeriksaan");
        btnAdd.addActionListener(e -> showForm(null));
        toolbar.add(btnAdd, BorderLayout.EAST);
        add(toolbar, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nama Balita", "Tanggal", "BB (kg)", "TB (cm)", "LK (cm)", "LiLA (cm)", "Status Gizi", "Petugas"}, 0) {
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
        List<Pemeriksaan> list = dao.getAll();
        for (Pemeriksaan p : list) {
            model.addRow(new Object[]{p.getId(), p.getNamaBalita(), p.getTanggal(),
                    p.getBeratBadan(), p.getTinggiBadan(), p.getLingkarKepala(), p.getLingkarLengan(),
                    p.getStatusGizi(), p.getPetugas()});
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        Pemeriksaan selected = dao.getAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        showForm(selected);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Hapus data pemeriksaan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadData();
        }
    }

    private void showForm(Pemeriksaan existing) {
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

        JTextField txtTanggal = new JTextField(existing != null ? existing.getTanggal() : "");
        txtTanggal.setToolTipText("Format: yyyy-MM-dd");

        JTextField txtBB = new JTextField(existing != null ? String.valueOf(existing.getBeratBadan()) : "");
        JTextField txtTB = new JTextField(existing != null ? String.valueOf(existing.getTinggiBadan()) : "");
        JTextField txtLK = new JTextField(existing != null ? String.valueOf(existing.getLingkarKepala()) : "");
        JTextField txtLiLA = new JTextField(existing != null ? String.valueOf(existing.getLingkarLengan()) : "");

        JComboBox<String> cboStatus = new JComboBox<>(STATUS_GIZI);
        if (existing != null) cboStatus.setSelectedItem(existing.getStatusGizi());

        JTextField txtPetugas = new JTextField(existing != null ? existing.getPetugas() : Session.getCurrentUser().getNama());
        JTextField txtCatatan = new JTextField(existing != null ? existing.getCatatan() : "");

        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.add(new JLabel("Nama Balita:")); panel.add(cboBalita);
        panel.add(new JLabel("Tanggal (yyyy-MM-dd):")); panel.add(txtTanggal);
        panel.add(new JLabel("Berat Badan (kg):")); panel.add(txtBB);
        panel.add(new JLabel("Tinggi Badan (cm):")); panel.add(txtTB);
        panel.add(new JLabel("Lingkar Kepala (cm):")); panel.add(txtLK);
        panel.add(new JLabel("Lingkar Lengan/LiLA (cm):")); panel.add(txtLiLA);
        panel.add(new JLabel("Status Gizi:")); panel.add(cboStatus);
        panel.add(new JLabel("Petugas:")); panel.add(txtPetugas);
        panel.add(new JLabel("Catatan:")); panel.add(txtCatatan);

        String title = existing == null ? "Tambah Pemeriksaan" : "Edit Pemeriksaan";
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

            double bb, tb, lk, lila;
            try {
                bb = Double.parseDouble(txtBB.getText().trim().isEmpty() ? "0" : txtBB.getText().trim());
                tb = Double.parseDouble(txtTB.getText().trim().isEmpty() ? "0" : txtTB.getText().trim());
                lk = Double.parseDouble(txtLK.getText().trim().isEmpty() ? "0" : txtLK.getText().trim());
                lila = Double.parseDouble(txtLiLA.getText().trim().isEmpty() ? "0" : txtLiLA.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Berat/Tinggi/Lingkar harus berupa angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Balita selectedBalita = (Balita) cboBalita.getSelectedItem();
            Pemeriksaan p = existing != null ? existing : new Pemeriksaan();
            p.setBalitaId(selectedBalita.getId());
            p.setTanggal(tanggal);
            p.setBeratBadan(bb);
            p.setTinggiBadan(tb);
            p.setLingkarKepala(lk);
            p.setLingkarLengan(lila);
            p.setStatusGizi((String) cboStatus.getSelectedItem());
            p.setPetugas(txtPetugas.getText().trim());
            p.setCatatan(txtCatatan.getText().trim());

            boolean success = existing == null ? dao.save(p) : dao.update(p);
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
