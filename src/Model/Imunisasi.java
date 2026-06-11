package model;

import Koneksi.Koneksi;
import java.sql.*;

public class Imunisasi {

    private int idImunisasi;
    private String namaImunisasi;
    private String jenisVaksin;
    private String tanggalImunisasi;

    Connection conn = Koneksi.getKoneksi();

    public int getIdImunisasi() {
        return idImunisasi;
    }

    public void setIdImunisasi(int idImunisasi) {
        this.idImunisasi = idImunisasi;
    }

    public String getNamaImunisasi() {
        return namaImunisasi;
    }

    public void setNamaImunisasi(String namaImunisasi) {
        this.namaImunisasi = namaImunisasi;
    }

    public String getJenisVaksin() {
        return jenisVaksin;
    }

    public void setJenisVaksin(String jenisVaksin) {
        this.jenisVaksin = jenisVaksin;
    }

    public String getTanggalImunisasi() {
        return tanggalImunisasi;
    }

    public void setTanggalImunisasi(String tanggalImunisasi) {
        this.tanggalImunisasi = tanggalImunisasi;
    }

    public boolean simpan() {

        try {

            String sql =
            "INSERT INTO imunisasi(nama_imunisasi,jenis_vaksin,tanggal_imunisasi) VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, namaImunisasi);
            ps.setString(2, jenisVaksin);
            ps.setString(3, tanggalImunisasi);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean ubah() {

        try {

            String sql =
            "UPDATE imunisasi SET nama_imunisasi=?, jenis_vaksin=?, tanggal_imunisasi=? WHERE id_imunisasi=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, namaImunisasi);
            ps.setString(2, jenisVaksin);
            ps.setString(3, tanggalImunisasi);
            ps.setInt(4, idImunisasi);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean hapus() {

        try {

            String sql =
            "DELETE FROM imunisasi WHERE id_imunisasi=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idImunisasi);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public ResultSet cari() {

        try {

            String sql =
            "SELECT * FROM imunisasi WHERE id_imunisasi=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idImunisasi);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }

    public ResultSet tampilData() {

        try {

            String sql =
            "SELECT * FROM imunisasi";

            PreparedStatement ps = conn.prepareStatement(sql);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }
}