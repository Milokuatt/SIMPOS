package simpos.ui;

import simpos.dao.BalitaDAO;
import simpos.model.Balita;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class BalitaPanel extends JPanel implements MainFrame.Refreshable {

    private final BalitaDAO dao = new BalitaDAO();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public BalitaPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(280, 36));
        txtSearch.putClientProperty("JTextField.placeholderText", "Cari nama / NIK / nama ibu...");

        JButton btnAdd = primaryButton("+ Tambah Balita");
        btnAdd.addActionListener(e -> showForm(null));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        left.setOpaque(false);
        left.add(txtSearch);

        toolbar.add(left, BorderLayout.WEST);
        toolbar.add(btnAdd, BorderLayout.EAST);
        add(toolbar, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nama", "NIK", "Tgl Lahir", "JK", "Nama Ibu", "Alamat", "No.Telp"}, 0) {
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

        txtSearch.addActionListener(e -> loadData());
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
        String keyword = txtSearch.getText().trim();
        List<Balita> list = keyword.isEmpty() ? dao.getAll() : dao.search(keyword);
        for (Balita b : list) {
            model.addRow(new Object[]{b.getId(), b.getNama(), b.getNik(), b.getTanggalLahir(),
                    b.getJenisKelamin(), b.getNamaIbu(), b.getAlamat(), b.getNoTelp()});
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        Balita selected = dao.getAll().stream().filter(b -> b.getId() == id).findFirst().orElse(null);
        showForm(selected);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Hapus data balita ini? Data imunisasi dan pemeriksaan terkait juga akan terhapus.",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(id);
            loadData();
        }
    }

    private void showForm(Balita existing) {
        JTextField txtNama = new JTextField(existing != null ? existing.getNama() : "");
        JTextField txtNik = new JTextField(existing != null ? existing.getNik() : "");

        JTextField dateField = new JTextField(existing != null ? existing.getTanggalLahir() : "");
        dateField.setToolTipText("Format: yyyy-MM-dd, contoh: 2023-05-17");

        JComboBox<String> cboJK = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        if (existing != null) cboJK.setSelectedItem(existing.getJenisKelamin());

        JTextField txtIbu = new JTextField(existing != null ? existing.getNamaIbu() : "");
        JTextField txtAyah = new JTextField(existing != null ? existing.getNamaAyah() : "");
        JTextField txtAlamat = new JTextField(existing != null ? existing.getAlamat() : "");
        JTextField txtTelp = new JTextField(existing != null ? existing.getNoTelp() : "");

        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.add(new JLabel("Nama Balita:")); panel.add(txtNama);
        panel.add(new JLabel("NIK:")); panel.add(txtNik);
        panel.add(new JLabel("Tanggal Lahir (yyyy-MM-dd):")); panel.add(dateField);
        panel.add(new JLabel("Jenis Kelamin:")); panel.add(cboJK);
        panel.add(new JLabel("Nama Ibu:")); panel.add(txtIbu);
        panel.add(new JLabel("Nama Ayah:")); panel.add(txtAyah);
        panel.add(new JLabel("Alamat:")); panel.add(txtAlamat);
        panel.add(new JLabel("No. Telp:")); panel.add(txtTelp);

        String title = existing == null ? "Tambah Balita" : "Edit Balita";
        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nama = txtNama.getText().trim();
            String namaIbu = txtIbu.getText().trim();
            String tglLahir = dateField.getText().trim();

            if (nama.isEmpty() || namaIbu.isEmpty() || tglLahir.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama, Nama Ibu, dan Tanggal Lahir wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                SDF.parse(tglLahir);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Format tanggal lahir tidak valid! Gunakan yyyy-MM-dd.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Balita b = existing != null ? existing : new Balita();
            b.setNama(nama);
            b.setNik(txtNik.getText().trim());
            b.setTanggalLahir(tglLahir);
            b.setJenisKelamin((String) cboJK.getSelectedItem());
            b.setNamaIbu(namaIbu);
            b.setNamaAyah(txtAyah.getText().trim());
            b.setAlamat(txtAlamat.getText().trim());
            b.setNoTelp(txtTelp.getText().trim());

            boolean success = existing == null ? dao.save(b) : dao.update(b);
            if (success) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data! Pastikan NIK belum terdaftar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void refresh() {
        loadData();
    }
}
