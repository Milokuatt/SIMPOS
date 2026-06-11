package form;

import model.Pengumuman;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.awt.event.*;

public class FormPengumuman extends JFrame {

    private JTextField txtId;
    private JTextField txtJudul;
    private JTextField txtTanggal;

    private JTextArea txtIsi;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersih;

    private JTable tblPengumuman;

    private JScrollPane scrollTable;
    private JScrollPane scrollIsi;

    public FormPengumuman() {

        setTitle("Data Pengumuman");
        setSize(1000,600);
        setLocationRelativeTo(null);
        setLayout(null);

        initComponent();

        tampilData();

        setVisible(true);
    }

    private void initComponent() {

        JLabel lblId =
                new JLabel("ID Pengumuman");

        lblId.setBounds(20,20,120,25);

        add(lblId);

        txtId = new JTextField();

        txtId.setBounds(150,20,200,25);

        add(txtId);

        JLabel lblJudul =
                new JLabel("Judul");

        lblJudul.setBounds(20,60,120,25);

        add(lblJudul);

        txtJudul = new JTextField();

        txtJudul.setBounds(150,60,250,25);

        add(txtJudul);

        JLabel lblIsi =
                new JLabel("Isi Pengumuman");

        lblIsi.setBounds(20,100,120,25);

        add(lblIsi);

        txtIsi = new JTextArea();

        scrollIsi =
                new JScrollPane(txtIsi);

        scrollIsi.setBounds(150,100,250,120);

        add(scrollIsi);

        JLabel lblTanggal =
                new JLabel("Tanggal");

        lblTanggal.setBounds(20,240,120,25);

        add(lblTanggal);

        txtTanggal = new JTextField();

        txtTanggal.setBounds(150,240,250,25);

        add(txtTanggal);

        btnSimpan =
                new JButton("Simpan");

        btnSimpan.setBounds(20,300,100,35);

        add(btnSimpan);

        btnUbah =
                new JButton("Ubah");

        btnUbah.setBounds(130,300,100,35);

        add(btnUbah);

        btnHapus =
                new JButton("Hapus");

        btnHapus.setBounds(240,300,100,35);

        add(btnHapus);

        btnBersih =
                new JButton("Bersih");

        btnBersih.setBounds(350,300,100,35);

        add(btnBersih);

        tblPengumuman =
                new JTable();

        scrollTable =
                new JScrollPane(tblPengumuman);

        scrollTable.setBounds(450,20,500,350);

        add(scrollTable);

        btnSimpan.addActionListener(
                e -> simpanData());

        btnUbah.addActionListener(
                e -> ubahData());

        btnHapus.addActionListener(
                e -> hapusData());

        btnBersih.addActionListener(
                e -> bersih());

        tblPengumuman.addMouseListener(
                new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int row =
                        tblPengumuman.getSelectedRow();

                txtId.setText(
                        tblPengumuman.getValueAt(row,0).toString());

                txtJudul.setText(
                        tblPengumuman.getValueAt(row,1).toString());

                txtIsi.setText(
                        tblPengumuman.getValueAt(row,2).toString());

                txtTanggal.setText(
                        tblPengumuman.getValueAt(row,3).toString());
            }
        });
    }

    private void simpanData() {

        Pengumuman p =
                new Pengumuman();

        p.setJudul(
                txtJudul.getText());

        p.setIsiPengumuman(
                txtIsi.getText());

        p.setTanggalPengumuman(
                txtTanggal.getText());

        if(p.simpan()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Disimpan");

            tampilData();

            bersih();
        }
    }

    private void ubahData() {

        Pengumuman p =
                new Pengumuman();

        p.setIdPengumuman(
                Integer.parseInt(
                        txtId.getText()));

        p.setJudul(
                txtJudul.getText());

        p.setIsiPengumuman(
                txtIsi.getText());

        p.setTanggalPengumuman(
                txtTanggal.getText());

        if(p.ubah()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data Berhasil Diubah");

            tampilData();

            bersih();
        }
    }

    private void hapusData() {

        Pengumuman p =
                new Pengumuman();

        p.setIdPengumuman(
                Integer.parseInt(
                        txtId.getText()));

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
        model.addColumn("Judul");
        model.addColumn("Isi");
        model.addColumn("Tanggal");

        try {

            Pengumuman p =
                    new Pengumuman();

            ResultSet rs =
                    p.tampilData();

            while(rs.next()) {

                model.addRow(new Object[] {

                    rs.getString("id_pengumuman"),
                    rs.getString("judul"),
                    rs.getString("isi_pengumuman"),
                    rs.getString("tanggal_pengumuman")
                });
            }

            tblPengumuman.setModel(model);

        } catch(Exception e) {

            System.out.println(e);
        }
    }

    private void bersih() {

        txtId.setText("");
        txtJudul.setText("");
        txtIsi.setText("");
        txtTanggal.setText("");
    }
}