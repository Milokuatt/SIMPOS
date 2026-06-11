package form;

import model.Pemeriksaan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.awt.event.*;

public class FormPemeriksaan extends JFrame {

    private JTextField txtId;
    private JTextField txtBerat;
    private JTextField txtTinggi;
    private JTextField txtVitamin;
    private JTextField txtHasil;
    private JTextField txtTanggal;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersih;

    private JTable tblPemeriksaan;
    private JScrollPane scrollPane;

    public FormPemeriksaan() {

        setTitle("Data Pemeriksaan");
        setSize(1000,600);
        setLocationRelativeTo(null);
        setLayout(null);

        initComponent();

        tampilData();

        setVisible(true);
    }

    private void initComponent() {

        JLabel lblId = new JLabel("ID Pemeriksaan");
        lblId.setBounds(20,20,120,25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150,20,220,25);
        add(txtId);

        JLabel lblBerat = new JLabel("Berat Badan");
        lblBerat.setBounds(20,60,120,25);
        add(lblBerat);

        txtBerat = new JTextField();
        txtBerat.setBounds(150,60,220,25);
        add(txtBerat);

        JLabel lblTinggi = new JLabel("Tinggi Badan");
        lblTinggi.setBounds(20,100,120,25);
        add(lblTinggi);

        txtTinggi = new JTextField();
        txtTinggi.setBounds(150,100,220,25);
        add(txtTinggi);

        JLabel lblVitamin = new JLabel("Vitamin");
        lblVitamin.setBounds(20,140,120,25);
        add(lblVitamin);

        txtVitamin = new JTextField();
        txtVitamin.setBounds(150,140,220,25);
        add(txtVitamin);

        JLabel lblHasil = new JLabel("Hasil Pemeriksaan");
        lblHasil.setBounds(20,180,120,25);
        add(lblHasil);

        txtHasil = new JTextField();
        txtHasil.setBounds(150,180,220,25);
        add(txtHasil);

        JLabel lblTanggal = new JLabel("Tanggal");
        lblTanggal.setBounds(20,220,120,25);
        add(lblTanggal);

        txtTanggal = new JTextField();
        txtTanggal.setBounds(150,220,220,25);
        add(txtTanggal);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(20,300,100,35);
        add(btnSimpan);

        btnUbah = new JButton("Ubah");
        btnUbah.setBounds(130,300,100,35);
        add(btnUbah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(240,300,100,35);
        add(btnHapus);

        btnBersih = new JButton("Bersih");
        btnBersih.setBounds(350,300,100,35);
        add(btnBersih);

        tblPemeriksaan = new JTable();

        scrollPane = new JScrollPane(tblPemeriksaan);
        scrollPane.setBounds(420,20,540,350);

        add(scrollPane);

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersih());

        tblPemeriksaan.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int row = tblPemeriksaan.getSelectedRow();

                txtId.setText(
                        tblPemeriksaan.getValueAt(row,0).toString());

                txtBerat.setText(
                        tblPemeriksaan.getValueAt(row,1).toString());

                txtTinggi.setText(
                        tblPemeriksaan.getValueAt(row,2).toString());

                txtVitamin.setText(
                        tblPemeriksaan.getValueAt(row,3).toString());

                txtHasil.setText(
                        tblPemeriksaan.getValueAt(row,4).toString());

                txtTanggal.setText(
                        tblPemeriksaan.getValueAt(row,5).toString());
            }
        });
    }

    private void simpanData() {

        Pemeriksaan p = new Pemeriksaan();

        p.setBeratBadan(
                Double.parseDouble(txtBerat.getText()));

        p.setTinggiBadan(
                Double.parseDouble(txtTinggi.getText()));

        p.setVitamin(txtVitamin.getText());
        p.setHasilPemeriksaan(txtHasil.getText());
        p.setTanggalPemeriksaan(txtTanggal.getText());

        if(p.simpan()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Disimpan");

            tampilData();
            bersih();
        }
    }

    private void ubahData() {

        Pemeriksaan p = new Pemeriksaan();

        p.setIdPemeriksaan(
                Integer.parseInt(txtId.getText()));

        p.setBeratBadan(
                Double.parseDouble(txtBerat.getText()));

        p.setTinggiBadan(
                Double.parseDouble(txtTinggi.getText()));

        p.setVitamin(txtVitamin.getText());
        p.setHasilPemeriksaan(txtHasil.getText());
        p.setTanggalPemeriksaan(txtTanggal.getText());

        if(p.ubah()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Diubah");

            tampilData();
            bersih();
        }
    }

    private void hapusData() {

        Pemeriksaan p = new Pemeriksaan();

        p.setIdPemeriksaan(
                Integer.parseInt(txtId.getText()));

        if(p.hapus()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Dihapus");

            tampilData();
            bersih();
        }
    }

    private void tampilData() {

        DefaultTableModel model =
                new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Berat");
        model.addColumn("Tinggi");
        model.addColumn("Vitamin");
        model.addColumn("Hasil");
        model.addColumn("Tanggal");

        try {

            Pemeriksaan p = new Pemeriksaan();

            ResultSet rs = p.tampilData();

            while(rs.next()) {

                model.addRow(new Object[] {

                    rs.getString("id_pemeriksaan"),
                    rs.getString("berat_badan"),
                    rs.getString("tinggi_badan"),
                    rs.getString("vitamin"),
                    rs.getString("hasil_pemeriksaan"),
                    rs.getString("tanggal_pemeriksaan")
                });
            }

            tblPemeriksaan.setModel(model);

        } catch(Exception e) {

            System.out.println(e);
        }
    }

    private void bersih() {

        txtId.setText("");
        txtBerat.setText("");
        txtTinggi.setText("");
        txtVitamin.setText("");
        txtHasil.setText("");
        txtTanggal.setText("");
    }
}