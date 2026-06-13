package simpos.ui;

import simpos.dao.JadwalDAO;
import simpos.model.Jadwal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class JadwalPanel extends JPanel implements MainFrame.Refreshable {

    private final JadwalDAO dao = new JadwalDAO();
    private DefaultTableModel model;
    private JTable table;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public JadwalPanel(boolean editable) {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        if (editable) {
            JButton btnAdd = primaryButton("+ Tambah Jadwal");
            btnAdd.addActionListener(e -> showForm(null));
            toolbar.add(btnAdd, BorderLayout.EAST);
        }
        add(toolbar, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nama Kegiatan", "Tanggal", "Waktu", "Lokasi", "Penanggung Jawab", "Status"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        add(scroll, BorderLayout.CENTER);

        if (editable) {
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
        b.setPreferredSize(new Dimension(160, 36));
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
        List<Jadwal> list = dao.getAll();
        for (Jadwal j : list) {
            model.addRow(new Object[]{j.getId(), j.getNamaKegiatan(), j.getTanggal(), j.getWaktu(),
                    j.getLokasi(), j.getPenanggungjawab(), j.getStatus()});
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        Jadwal selected = dao.getAll().stream().filter(j -> j.getId() == id).findFirst().orElse(null);
        showForm(selected);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Hapus jadwal ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadData();
        }
    }

    private void showForm(Jadwal existing) {
        JTextField txtNama = new JTextField(existing != null ? existing.getNamaKegiatan() : "");
        JTextField txtTanggal = new JTextField(existing != null ? existing.getTanggal() : "");
        txtTanggal.setToolTipText("Format: yyyy-MM-dd");
        JTextField txtWaktu = new JTextField(existing != null ? existing.getWaktu() : "");
        txtWaktu.setToolTipText("Contoh: 08:00 - 11:00");
        JTextField txtLokasi = new JTextField(existing != null ? existing.getLokasi() : "");
        JTextField txtPJ = new JTextField(existing != null ? existing.getPenanggungjawab() : "");
        JTextField txtDeskripsi = new JTextField(existing != null ? existing.getDeskripsi() : "");
        JComboBox<String> cboStatus = new JComboBox<>(new String[]{"Aktif", "Selesai", "Dibatalkan"});
        if (existing != null) cboStatus.setSelectedItem(existing.getStatus());

        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.add(new JLabel("Nama Kegiatan:")); panel.add(txtNama);
        panel.add(new JLabel("Tanggal (yyyy-MM-dd):")); panel.add(txtTanggal);
        panel.add(new JLabel("Waktu:")); panel.add(txtWaktu);
        panel.add(new JLabel("Lokasi:")); panel.add(txtLokasi);
        panel.add(new JLabel("Penanggung Jawab:")); panel.add(txtPJ);
        panel.add(new JLabel("Deskripsi:")); panel.add(txtDeskripsi);
        panel.add(new JLabel("Status:")); panel.add(cboStatus);

        String title = existing == null ? "Tambah Jadwal Posyandu" : "Edit Jadwal Posyandu";
        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nama = txtNama.getText().trim();
            String tanggal = txtTanggal.getText().trim();

            if (nama.isEmpty() || tanggal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama Kegiatan dan Tanggal wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try { SDF.parse(tanggal); } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Format tanggal tidak valid! Gunakan yyyy-MM-dd.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Jadwal j = existing != null ? existing : new Jadwal();
            j.setNamaKegiatan(nama);
            j.setTanggal(tanggal);
            j.setWaktu(txtWaktu.getText().trim());
            j.setLokasi(txtLokasi.getText().trim());
            j.setPenanggungjawab(txtPJ.getText().trim());
            j.setDeskripsi(txtDeskripsi.getText().trim());
            j.setStatus((String) cboStatus.getSelectedItem());

            boolean success = existing == null ? dao.save(j) : dao.update(j);
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
