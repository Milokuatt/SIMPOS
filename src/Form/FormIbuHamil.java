package form;

import model.IbuHamil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.awt.event.*;

public class FormIbuHamil extends JFrame {

    private JTextField txtId;
    private JTextField txtNik;
    private JTextField txtNama;
    private JTextField txtUsia;
    private JTextField txtTekanan;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersih;

    private JTable tblIbuHamil;
    private JScrollPane scrollPane;

    public FormIbuHamil() {

        setTitle("Data Ibu Hamil");
        setSize(900,600);
        setLocationRelativeTo(null);
        setLayout(null);

        initComponent();

        tampilData();

        setVisible(true);
    }

    private void initComponent() {

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(20,20,100,25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150,20,200,25);
        add(txtId);

        JLabel lblNik = new JLabel("NIK");
        lblNik.setBounds(20,60,100,25);
        add(lblNik);

        txtNik = new JTextField();
        txtNik.setBounds(150,60,200,25);
        add(txtNik);

        JLabel lblNama = new JLabel("Nama Ibu");
        lblNama.setBounds(20,100,100,25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(150,100,200,25);
        add(txtNama);

        JLabel lblUsia = new JLabel("Usia Kehamilan");
        lblUsia.setBounds(20,140,120,25);
        add(lblUsia);

        txtUsia = new JTextField();
        txtUsia.setBounds(150,140,200,25);
        add(txtUsia);

        JLabel lblTekanan = new JLabel("Tekanan Darah");
        lblTekanan.setBounds(20,180,120,25);
        add(lblTekanan);

        txtTekanan = new JTextField();
        txtTekanan.setBounds(150,180,200,25);
        add(txtTekanan);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(20,250,100,35);
        add(btnSimpan);

        btnUbah = new JButton("Ubah");
        btnUbah.setBounds(130,250,100,35);
        add(btnUbah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(240,250,100,35);
        add(btnHapus);

        btnBersih = new JButton("Bersih");
        btnBersih.setBounds(350,250,100,35);
        add(btnBersih);

        tblIbuHamil = new JTable();

        scrollPane = new JScrollPane(tblIbuHamil);
        scrollPane.setBounds(380,20,480,300);

        add(scrollPane);

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersih());

        tblIbuHamil.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int row = tblIbuHamil.getSelectedRow();

                txtId.setText(
                        tblIbuHamil.getValueAt(row,0).toString());

                txtNik.setText(
                        tblIbuHamil.getValueAt(row,1).toString());

                txtNama.setText(
                        tblIbuHamil.getValueAt(row,2).toString());

                txtUsia.setText(
                        tblIbuHamil.getValueAt(row,3).toString());

                txtTekanan.setText(
                        tblIbuHamil.getValueAt(row,4).toString());
            }
        });
    }

    private void simpanData() {

        IbuHamil i = new IbuHamil();

        i.setNik(txtNik.getText());
        i.setNamaIbu(txtNama.getText());

        i.setUsiaKehamilan(
                Integer.parseInt(txtUsia.getText()));

        i.setTekananDarah(txtTekanan.getText());

        if(i.simpan()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Disimpan");

            tampilData();
            bersih();
        }
    }

    private void ubahData() {

        IbuHamil i = new IbuHamil();

        i.setIdIbu(
                Integer.parseInt(txtId.getText()));

        i.setNik(txtNik.getText());
        i.setNamaIbu(txtNama.getText());

        i.setUsiaKehamilan(
                Integer.parseInt(txtUsia.getText()));

        i.setTekananDarah(txtTekanan.getText());

        if(i.ubah()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Diubah");

            tampilData();
            bersih();
        }
    }

    private void hapusData() {

        IbuHamil i = new IbuHamil();

        i.setIdIbu(
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
        model.addColumn("NIK");
        model.addColumn("Nama");
        model.addColumn("Usia");
        model.addColumn("Tekanan");

        try {

            IbuHamil i = new IbuHamil();

            ResultSet rs = i.tampilData();

            while(rs.next()) {

                model.addRow(new Object[]{

                    rs.getString("id_ibu"),
                    rs.getString("nik"),
                    rs.getString("nama_ibu"),
                    rs.getString("usia_kehamilan"),
                    rs.getString("tekanan_darah")
                });
            }

            tblIbuHamil.setModel(model);

        } catch(Exception e) {

            System.out.println(e);
        }
    }

    private void bersih() {

        txtId.setText("");
        txtNik.setText("");
        txtNama.setText("");
        txtUsia.setText("");
        txtTekanan.setText("");
    }
}