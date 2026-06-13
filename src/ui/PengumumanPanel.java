package simpos.ui;

import simpos.dao.PengumumanDAO;
import simpos.model.Pengumuman;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PengumumanPanel extends JPanel implements MainFrame.Refreshable {

    private final PengumumanDAO dao = new PengumumanDAO();
    private DefaultTableModel model;
    private JTable table;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public PengumumanPanel(boolean editable) {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        if (editable) {
            JButton btnAdd = primaryButton("+ Tambah Pengumuman");
            btnAdd.addActionListener(e -> showForm(null));
            toolbar.add(btnAdd, BorderLayout.EAST);
        }
        add(toolbar, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Judul", "Isi", "Tanggal", "Status"}, 0) {
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
        b.setPreferredSize(new Dimension(180, 36));
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
        List<Pengumuman> list = dao.getAll();
        for (Pengumuman p : list) {
            String isi = p.getIsi();
            if (isi != null && isi.length() > 60) isi = isi.substring(0, 60) + "...";
            model.addRow(new Object[]{p.getId(), p.getJudul(), isi, p.getTanggal(), p.getStatus()});
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        Pengumuman selected = dao.getAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        showForm(selected);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Hapus pengumuman ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadData();
        }
    }

    private void showForm(Pengumuman existing) {
        JTextField txtJudul = new JTextField(existing != null ? existing.getJudul() : "");
        JTextArea txtIsi = new JTextArea(existing != null ? existing.getIsi() : "", 5, 20);
        txtIsi.setLineWrap(true);
        txtIsi.setWrapStyleWord(true);
        JScrollPane spIsi = new JScrollPane(txtIsi);

        JTextField txtTanggal = new JTextField(existing != null ? existing.getTanggal() : SDF.format(new Date()));
        txtTanggal.setToolTipText("Format: yyyy-MM-dd");

        JComboBox<String> cboStatus = new JComboBox<>(new String[]{"Aktif", "Nonaktif"});
        if (existing != null) cboStatus.setSelectedItem(existing.getStatus());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Judul:")); panel.add(txtJudul);
        panel.add(Box.createVerticalStrut(6));
        panel.add(new JLabel("Isi Pengumuman:")); panel.add(spIsi);
        panel.add(Box.createVerticalStrut(6));
        panel.add(new JLabel("Tanggal (yyyy-MM-dd):")); panel.add(txtTanggal);
        panel.add(Box.createVerticalStrut(6));
        panel.add(new JLabel("Status:")); panel.add(cboStatus);

        String title = existing == null ? "Tambah Pengumuman" : "Edit Pengumuman";
        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String judul = txtJudul.getText().trim();
            String isi = txtIsi.getText().trim();
            String tanggal = txtTanggal.getText().trim();

            if (judul.isEmpty() || isi.isEmpty() || tanggal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul, Isi, dan Tanggal wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try { SDF.parse(tanggal); } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Format tanggal tidak valid! Gunakan yyyy-MM-dd.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Pengumuman p = existing != null ? existing : new Pengumuman();
            p.setJudul(judul);
            p.setIsi(isi);
            p.setTanggal(tanggal);
            p.setStatus((String) cboStatus.getSelectedItem());

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
