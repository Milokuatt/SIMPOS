package form;

import model.JadwalPosyandu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.awt.event.*;

public class FormJadwal extends JFrame {

    private JTextField txtId;
    private JTextField txtTanggal;
    private JTextField txtJam;
    private JTextField txtLokasi;
    private JTextField txtJenis;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersih;

    private JTable tblJadwal;
    private JScrollPane scrollPane;

    public FormJadwal() {

        setTitle("Jadwal Posyandu");
        setSize(950,600);
        setLocationRelativeTo(null);
        setLayout(null);

        initComponent();

        tampilData();

        setVisible(true);
    }

    private void initComponent() {

        JLabel lblId = new JLabel("ID Jadwal");
        lblId.setBounds(20,20,120,25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150,20,200,25);
        add(txtId);

        JLabel lblTanggal = new JLabel("Tanggal");
        lblTanggal.setBounds(20,60,120,25);
        add(lblTanggal);

        txtTanggal = new JTextField();
        txtTanggal.setBounds(150,60,200,25);
        add(txtTanggal);

        JLabel lblJam = new JLabel("Jam");
        lblJam.setBounds(20,100,120,25);
        add(lblJam);

        txtJam = new JTextField();
        txtJam.setBounds(150,100,200,25);
        add(txtJam);

        JLabel lblLokasi = new JLabel("Lokasi");
        lblLokasi.setBounds(20,140,120,25);
        add(lblLokasi);

        txtLokasi = new JTextField();
        txtLokasi.setBounds(150,140,200,25);
        add(txtLokasi);

        JLabel lblJenis = new JLabel("Jenis Pelayanan");
        lblJenis.setBounds(20,180,120,25);
        add(lblJenis);

        txtJenis = new JTextField();
        txtJenis.setBounds(150,180,200,25);
        add(txtJenis);

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

        tblJadwal = new JTable();

        scrollPane = new JScrollPane(tblJadwal);
        scrollPane.setBounds(400,20,500,300);

        add(scrollPane);

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersih());

        tblJadwal.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int row = tblJadwal.getSelectedRow();

                txtId.setText(
                        tblJadwal.getValueAt(row,0).toString());

                txtTanggal.setText(
                        tblJadwal.getValueAt(row,1).toString());

                txtJam.setText(
                        tblJadwal.getValueAt(row,2).toString());

                txtLokasi.setText(
                        tblJadwal.getValueAt(row,3).toString());

                txtJenis.setText(
                        tblJadwal.getValueAt(row,4).toString());
            }
        });
    }

    private void simpanData() {

        JadwalPosyandu j = new JadwalPosyandu();

        j.setTanggalKegiatan(txtTanggal.getText());
        j.setJamPelayanan(txtJam.getText());
        j.setLokasi(txtLokasi.getText());
        j.setJenisPelayanan(txtJenis.getText());

        if(j.simpan()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Disimpan");

            tampilData();
            bersih();
        }
    }

    private void ubahData() {

        JadwalPosyandu j = new JadwalPosyandu();

        j.setIdJadwal(
                Integer.parseInt(txtId.getText()));

        j.setTanggalKegiatan(txtTanggal.getText());
        j.setJamPelayanan(txtJam.getText());
        j.setLokasi(txtLokasi.getText());
        j.setJenisPelayanan(txtJenis.getText());

        if(j.ubah()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Diubah");

            tampilData();
            bersih();
        }
    }

    private void hapusData() {

        JadwalPosyandu j = new JadwalPosyandu();

        j.setIdJadwal(
                Integer.parseInt(txtId.getText()));

        if(j.hapus()) {

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
        model.addColumn("Tanggal");
        model.addColumn("Jam");
        model.addColumn("Lokasi");
        model.addColumn("Jenis");

        try {

            JadwalPosyandu j =
                    new JadwalPosyandu();

            ResultSet rs =
                    j.tampilData();

            while(rs.next()) {

                model.addRow(new Object[] {

                    rs.getString("id_jadwal"),
                    rs.getString("tanggal_kegiatan"),
                    rs.getString("jam_pelayanan"),
                    rs.getString("lokasi"),
                    rs.getString("jenis_pelayanan")
                });
            }

            tblJadwal.setModel(model);

        } catch(Exception e) {

            System.out.println(e);
        }
    }

    private void bersih() {

        txtId.setText("");
        txtTanggal.setText("");
        txtJam.setText("");
        txtLokasi.setText("");
        txtJenis.setText("");
    }
}