package form;

import model.Balita;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.sql.ResultSet;
import java.awt.*;
import java.awt.event.*;

public class FormBalita extends JFrame {

    private JTextField txtId;
    private JTextField txtNik;
    private JTextField txtNama;
    private JTextField txtTanggal;
    private JTextField txtBB;
    private JTextField txtTB;

    private JComboBox<String> cmbJK;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersih;

    private JTable tblBalita;
    private JScrollPane scrollPane;

    public FormBalita() {
        // Mengaktifkan tema FlatLaf agar UI terasa modern, berbayang, dan hidup
        try {
           
        } catch (Exception ex) {
            System.err.println("Gagal menginisialisasi FlatLaf");
        }

        setTitle("SIMPOS - Data Balita");
        setSize(960, 520); // Sedikit disesuaikan agar tata letak tombol & tabel lebih lega
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // Background utama putih bersih
        getContentPane().setBackground(Color.WHITE);

        initComponent();
        tampilData();

        setVisible(true);
    }

    private void initComponent() {
        // Pengaturan font global untuk konsistensi UI
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 13);
        Color colorLabel = new Color(75, 85, 99); // Abu-abu gelap kontras
        Color colorBorder = new Color(209, 213, 219); // Border abu-abu halus ala web modern

        // --- FIELD 1: ID ---
        JLabel lblId = new JLabel("ID Balita");
        lblId.setFont(fontLabel);
        lblId.setForeground(colorLabel);
        lblId.setBounds(30, 25, 100, 20);
        add(lblId);

        txtId = new JTextField();
        txtId.setFont(fontInput);
        txtId.setBounds(140, 20, 180, 32);
        txtId.setEditable(false); 
        txtId.setBackground(new Color(243, 244, 246)); // Penanda visual disabled yang lembut
        add(txtId);

        // --- FIELD 2: NIK ---
        JLabel lblNik = new JLabel("NIK");
        lblNik.setFont(fontLabel);
        lblNik.setForeground(colorLabel);
        lblNik.setBounds(30, 70, 100, 20);
        add(lblNik);

        txtNik = new JTextField();
        txtNik.setFont(fontInput);
        txtNik.setBounds(140, 65, 180, 32);
        add(txtNik);

        // --- FIELD 3: NAMA ---
        JLabel lblNama = new JLabel("Nama Balita");
        lblNama.setFont(fontLabel);
        lblNama.setForeground(colorLabel);
        lblNama.setBounds(30, 115, 100, 20);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setFont(fontInput);
        txtNama.setBounds(140, 110, 180, 32);
        add(txtNama);

        // --- FIELD 4: TANGGAL LAHIR ---
        JLabel lblTanggal = new JLabel("Tanggal Lahir");
        lblTanggal.setFont(fontLabel);
        lblTanggal.setForeground(colorLabel);
        lblTanggal.setBounds(30, 160, 100, 20);
        add(lblTanggal);

        txtTanggal = new JTextField();
        txtTanggal.setFont(fontInput);
        txtTanggal.setBounds(140, 155, 180, 32);
        add(txtTanggal);

        // --- FIELD 5: JENIS KELAMIN ---
        JLabel lblJK = new JLabel("Jenis Kelamin");
        lblJK.setFont(fontLabel);
        lblJK.setForeground(colorLabel);
        lblJK.setBounds(30, 205, 100, 20);
        add(lblJK);

        cmbJK = new JComboBox<>();
        cmbJK.addItem("Laki-Laki");
        cmbJK.addItem("Perempuan");
        cmbJK.setFont(fontInput);
        cmbJK.setBackground(Color.WHITE);
        cmbJK.setBounds(140, 200, 180, 32);
        ;

        // --- FIELD 6: BERAT BADAN ---
        JLabel lblBB = new JLabel("Berat Badan (Kg)");
        lblBB.setFont(fontLabel);
        lblBB.setForeground(colorLabel);
        lblBB.setBounds(30, 250, 110, 20);
        add(lblBB);

        txtBB = new JTextField();
        txtBB.setFont(fontInput);
        txtBB.setBounds(140, 245, 180, 32);
        
        add(txtBB);

        // --- FIELD 7: TINGGI BADAN ---
        JLabel lblTB = new JLabel("Tinggi Badan (Cm)");
        lblTB.setFont(fontLabel);
        lblTB.setForeground(colorLabel);
        lblTB.setBounds(30, 295, 110, 20);
        add(lblTB);

        txtTB = new JTextField();
        txtTB.setFont(fontInput);
        txtTB.setBounds(140, 290, 180, 32);
        
        add(txtTB);

        // ==========================================
        // PENGATURAN TOMBOL AKSI (MODERN & BERGAYA)
        // ==========================================
        
        // Tombol Simpan (Primary - Merah Cerah Semangat)
        btnSimpan = createStyledButton("Simpan", new Color(214, 48, 49), Color.WHITE);
        btnSimpan.setBounds(30, 360, 110, 38);
        add(btnSimpan);

        // Tombol Ubah (Secondary - Orange Berenergi)
        btnUbah = createStyledButton("Ubah", new Color(230, 126, 34), Color.WHITE);
        btnUbah.setBounds(150, 360, 110, 38);
        add(btnUbah);

        // Tombol Hapus (Danger - Maroon Tegas)
        btnHapus = createStyledButton("Hapus", new Color(192, 57, 43), Color.WHITE);
        btnHapus.setBounds(270, 360, 110, 38);
        add(btnHapus);

        // Tombol Bersih (Neutral - Putih Bersih Minimalis)
        btnBersih = createStyledButton("Bersih", Color.WHITE, new Color(75, 85, 99));
        btnBersih.setBounds(390, 360, 110, 38);
        add(btnBersih);

        // ==========================================
        // PENGATURAN TABEL DATA (ELEGAN & JELAS)
        // ==========================================
        tblBalita = new JTable();
        tblBalita.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblBalita.setRowHeight(30); // Baris lebih tinggi agar data tampak lega dan profesional
        tblBalita.setGridColor(new Color(243, 244, 246));
        tblBalita.setSelectionBackground(new Color(214, 48, 49)); // Seleksi warna merah serasi tema utama
        tblBalita.setSelectionForeground(Color.WHITE);
        tblBalita.setShowVerticalLines(false); // Desain modern tanpa garis vertikal pemisah

        // Desain Header Tabel
        JTableHeader header = tblBalita.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(44, 62, 80)); // Navy gelap
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 35)); // Header lebih tebal

        // Membuat data teks di tabel sejajar ke tengah (Center Alignment) agar rapi
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblBalita.setDefaultRenderer(Object.class, centerRenderer);

        scrollPane = new JScrollPane(tblBalita);
        scrollPane.setBounds(350, 20, 560, 302);
        
        add(scrollPane);

        // --- EVENT HANDLERS ---
        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersih());

        tblBalita.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblBalita.getSelectedRow();
                if (row != -1) {
                    txtId.setText(getCellValue(row, 0));
                    txtNik.setText(getCellValue(row, 1));
                    txtNama.setText(getCellValue(row, 2));
                    txtTanggal.setText(getCellValue(row, 3));
                    cmbJK.setSelectedItem(getCellValue(row, 4));
                    txtBB.setText(getCellValue(row, 5));
                    txtTB.setText(getCellValue(row, 6));
                }
            }
        });
    }

    // Fungsi pembantu ekstraksi nilai tabel yang aman dari NullPointer
    private String getCellValue(int row, int col) {
        Object val = tblBalita.getValueAt(row, col);
        return (val == null) ? "" : val.toString();
    }

    // Fungsi kustomisasi Button bergaya Flat modern
    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }

    // --- LOGIKA KRITIKAL: VALIDASI INPUT UNTUK MEMASTIKAN DATA MAU TERINPUT ---
    private boolean validasiForm() {
        if (txtNik.getText().trim().isEmpty() || 
            txtNama.getText().trim().isEmpty() || 
            txtTanggal.getText().trim().isEmpty() || 
            txtBB.getText().trim().isEmpty() || 
            txtTB.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Semua kolom data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void simpanData() {
        if (!validasiForm()) return;

        try {
            Balita b = new Balita();
            b.setNik(txtNik.getText().trim());
            b.setNamaBalita(txtNama.getText().trim());
            b.setTanggalLahir(txtTanggal.getText().trim());
            b.setJenisKelamin(cmbJK.getSelectedItem().toString());
            
            // Mengamankan konversi angka desimal agar tidak memicu NumberFormatException crash
            try {
                b.setBeratBadan(Double.parseDouble(txtBB.getText().trim()));
                b.setTinggiBadan(Double.parseDouble(txtTB.getText().trim()));
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Format Berat/Tinggi badan salah! Gunakan angka (titik untuk desimal).", "Format Salah", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (b.simpan()) {
                JOptionPane.showMessageDialog(this, "Data Balita Berhasil Disimpan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampilData();
                bersih();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data ke database. Cek class Model Anda.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubahData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu yang ingin diubah!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validasiForm()) return;

        try {
            Balita b = new Balita();
            b.setIdBalita(Integer.parseInt(txtId.getText()));
            b.setNik(txtNik.getText().trim());
            b.setNamaBalita(txtNama.getText().trim());
            b.setTanggalLahir(txtTanggal.getText().trim());
            b.setJenisKelamin(cmbJK.getSelectedItem().toString());
            
            try {
                b.setBeratBadan(Double.parseDouble(txtBB.getText().trim()));
                b.setTinggiBadan(Double.parseDouble(txtTB.getText().trim()));
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Format Berat/Tinggi badan salah!", "Format Salah", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (b.ubah()) {
                JOptionPane.showMessageDialog(this, "Data Balita Berhasil Diubah", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampilData();
                bersih();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try {
            Balita b = new Balita();
            b.setIdBalita(Integer.parseInt(txtId.getText()));

            if (b.hapus()) {
                JOptionPane.showMessageDialog(this, "Data Balita Berhasil Dihapus", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampilData();
                bersih();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tampilData() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        model.addColumn("ID");
        model.addColumn("NIK");
        model.addColumn("Nama Balita");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Berat (Kg)");
        model.addColumn("Tinggi (Cm)");

        try {
            Balita b = new Balita();
            ResultSet rs = b.tampilData();

            if (rs != null) {
                while (rs.next()) {
                    model.addRow(new Object[] {
                        rs.getString("id_balita"),
                        rs.getString("nik"),
                        rs.getString("nama_balita"),
                        rs.getString("tanggal_lahir"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("berat_badan"),
                        rs.getString("tinggi_badan")
                    });
                }
            }
            tblBalita.setModel(model);
        } catch (Exception e) {
            System.out.println("Gagal memuat data ke JTable: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void bersih() {
        txtId.setText("");
        txtNik.setText("");
        txtNama.setText("");
        txtTanggal.setText("");
        txtBB.setText("");
        txtTB.setText("");
        cmbJK.setSelectedIndex(0);
        tblBalita.clearSelection();
    }
}