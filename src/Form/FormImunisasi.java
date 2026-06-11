package form;

import model.Imunisasi;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.awt.event.*;

public class FormImunisasi extends JFrame {

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtJenis;
    private JTextField txtTanggal;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersih;

    private JTable tblImunisasi;
    private JScrollPane scrollPane;

    public FormImunisasi() {

        setTitle("Data Imunisasi");
        setSize(900,550);
        setLocationRelativeTo(null);
        setLayout(null);

        initComponent();

        tampilData();

        setVisible(true);
    }

    private void initComponent() {

        JLabel lblId = new JLabel("ID Imunisasi");
        lblId.setBounds(20,20,120,25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150,20,220,25);
        add(txtId);

        JLabel lblNama = new JLabel("Nama Imunisasi");
        lblNama.setBounds(20,60,120,25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(150,60,220,25);
        add(txtNama);

        JLabel lblJenis = new JLabel("Jenis Vaksin");
        lblJenis.setBounds(20,100,120,25);
        add(lblJenis);

        txtJenis = new JTextField();
        txtJenis.setBounds(150,100,220,25);
        add(txtJenis);

        JLabel lblTanggal = new JLabel("Tanggal");
        lblTanggal.setBounds(20,140,120,25);
        add(lblTanggal);

        txtTanggal = new JTextField();
        txtTanggal.setBounds(150,140,220,25);
        add(txtTanggal);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(20,220,100,35);
        add(btnSimpan);

        btnUbah = new JButton("Ubah");
        btnUbah.setBounds(130,220,100,35);
        add(btnUbah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(240,220,100,35);
        add(btnHapus);

        btnBersih = new JButton("Bersih");
        btnBersih.setBounds(350,220,100,35);
        add(btnBersih);

        tblImunisasi = new JTable();

        scrollPane = new JScrollPane(tblImunisasi);
        scrollPane.setBounds(420,20,430,300);

        add(scrollPane);

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersih());

        tblImunisasi.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int row = tblImunisasi.getSelectedRow();

                txtId.setText(
                        tblImunisasi.getValueAt(row,0).toString());

                txtNama.setText(
                        tblImunisasi.getValueAt(row,1).toString());

                txtJenis.setText(
                        tblImunisasi.getValueAt(row,2).toString());

                txtTanggal.setText(
                        tblImunisasi.getValueAt(row,3).toString());
            }
        });
    }

    private void simpanData() {

        Imunisasi i = new Imunisasi();

        i.setNamaImunisasi(txtNama.getText());
        i.setJenisVaksin(txtJenis.getText());
        i.setTanggalImunisasi(txtTanggal.getText());

        if(i.simpan()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Disimpan");

            tampilData();
            bersih();
        }
    }

    private void ubahData() {

        Imunisasi i = new Imunisasi();

        i.setIdImunisasi(
                Integer.parseInt(txtId.getText()));

        i.setNamaImunisasi(txtNama.getText());
        i.setJenisVaksin(txtJenis.getText());
        i.setTanggalImunisasi(txtTanggal.getText());

        if(i.ubah()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Diubah");

            tampilData();
            bersih();
        }
    }

    private void hapusData() {

        Imunisasi i = new Imunisasi();

        i.setIdImunisasi(
                Integer.parseInt(txtId.getText()));

        if(i.hapus()) {

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
        model.addColumn("Nama Imunisasi");
        model.addColumn("Jenis Vaksin");
        model.addColumn("Tanggal");

        try {

            Imunisasi i = new Imunisasi();

            ResultSet rs = i.tampilData();

            while(rs.next()) {

                model.addRow(new Object[] {

                    rs.getString("id_imunisasi"),
                    rs.getString("nama_imunisasi"),
                    rs.getString("jenis_vaksin"),
                    rs.getString("tanggal_imunisasi")
                });
            }

            tblImunisasi.setModel(model);

        } catch(Exception e) {

            System.out.println(e);
        }
    }

    private void bersih() {

        txtId.setText("");
        txtNama.setText("");
        txtJenis.setText("");
        txtTanggal.setText("");
    }
}