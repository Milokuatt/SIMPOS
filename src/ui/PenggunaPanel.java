package simpos.ui;

import simpos.dao.PenggunaDAO;
import simpos.model.Pengguna;
import simpos.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PenggunaPanel extends JPanel implements MainFrame.Refreshable {

    private final PenggunaDAO dao = new PenggunaDAO();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;

    public PenggunaPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Toolbar
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 36));
        txtSearch.putClientProperty("JTextField.placeholderText", "Cari nama / username...");

        JButton btnAdd = primaryButton("+ Tambah Pengguna");
        btnAdd.addActionListener(e -> showForm(null));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        left.setOpaque(false);
        left.add(txtSearch);

        toolbar.add(left, BorderLayout.WEST);
        toolbar.add(btnAdd, BorderLayout.EAST);
        add(toolbar, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Nama", "Username", "Role"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        add(scroll, BorderLayout.CENTER);

        // Action buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        actions.setOpaque(false);

        JButton btnEdit = secondaryButton("Edit");
        btnEdit.addActionListener(e -> editSelected());

        JButton btnDelete = dangerButton("Hapus");
        btnDelete.addActionListener(e -> deleteSelected());

        actions.add(btnEdit);
        actions.add(btnDelete);
        add(actions, BorderLayout.SOUTH);

        txtSearch.addActionListener(e -> loadData());
    }

    private void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setFont(Theme.FONT_NORMAL);
        table.getTableHeader().setFont(Theme.FONT_BOLD);
        table.getTableHeader().setBackground(Theme.PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(224, 242, 241));
        table.setSelectionForeground(Theme.TEXT_DARK);
        table.getColumnModel().getColumn(0).setMaxWidth(60);
    }

    private JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(Theme.PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFont(Theme.FONT_BOLD);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(160, 36));
        return b;
    }

    private JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(33, 150, 243));
        b.setForeground(Color.WHITE);
        b.setFont(Theme.FONT_BOLD);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(100, 36));
        return b;
    }

    private JButton dangerButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(Theme.DANGER);
        b.setForeground(Color.WHITE);
        b.setFont(Theme.FONT_BOLD);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(100, 36));
        return b;
    }

    private void loadData() {
        model.setRowCount(0);
        String keyword = txtSearch.getText().trim().toLowerCase();
        List<Pengguna> list = dao.getAll();
        for (Pengguna p : list) {
            if (!keyword.isEmpty()
                    && !p.getNama().toLowerCase().contains(keyword)
                    && !p.getUsername().toLowerCase().contains(keyword)) {
                continue;
            }
            model.addRow(new Object[]{p.getId(), p.getNama(), p.getUsername(), p.getRole()});
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        Pengguna selected = dao.getAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        showForm(selected);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);

        if (id == Session.getCurrentUser().getId()) {
            JOptionPane.showMessageDialog(this, "Tidak dapat menghapus akun yang sedang digunakan!", "Ditolak", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Hapus data pengguna ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadData();
        }
    }

    private void showForm(Pengguna existing) {
        JTextField txtNama = new JTextField(existing != null ? existing.getNama() : "");
        JTextField txtUsername = new JTextField(existing != null ? existing.getUsername() : "");
        JPasswordField txtPassword = new JPasswordField(existing != null ? existing.getPassword() : "");
        JComboBox<String> cboRole = new JComboBox<>(new String[]{"admin", "kader", "ortu"});
        if (existing != null) cboRole.setSelectedItem(existing.getRole());

        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.add(new JLabel("Nama Lengkap:"));
        panel.add(txtNama);
        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);
        panel.add(new JLabel("Role:"));
        panel.add(cboRole);

        JLabel hint = new JLabel("<html><i>Catatan: untuk role 'ortu', Nama Lengkap harus sama persis<br>dengan Nama Ibu/Nama Ayah pada data Balita agar data anak muncul.</i></html>");
        hint.setForeground(Color.GRAY);
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        panel.add(hint);

        String title = existing == null ? "Tambah Pengguna" : "Edit Pengguna";
        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nama = txtNama.getText().trim();
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String role = (String) cboRole.getSelectedItem();

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int excludeId = existing != null ? existing.getId() : -1;
            if (dao.isUsernameExists(username, excludeId)) {
                JOptionPane.showMessageDialog(this, "Username sudah digunakan!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Pengguna p = existing != null ? existing : new Pengguna();
            p.setNama(nama);
            p.setUsername(username);
            p.setPassword(password);
            p.setRole(role);

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
