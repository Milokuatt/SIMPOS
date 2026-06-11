package model;

import Koneksi.Koneksi;
import java.sql.*;

public class JadwalPosyandu {

    private int idJadwal;
    private String tanggalKegiatan;
    private String jamPelayanan;
    private String lokasi;
    private String jenisPelayanan;

    Connection conn = Koneksi.getKoneksi();

    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getTanggalKegiatan() {
        return tanggalKegiatan;
    }

    public void setTanggalKegiatan(String tanggalKegiatan) {
        this.tanggalKegiatan = tanggalKegiatan;
    }

    public String getJamPelayanan() {
        return jamPelayanan;
    }

    public void setJamPelayanan(String jamPelayanan) {
        this.jamPelayanan = jamPelayanan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJenisPelayanan() {
        return jenisPelayanan;
    }

    public void setJenisPelayanan(String jenisPelayanan) {
        this.jenisPelayanan = jenisPelayanan;
    }

    public boolean simpan() {

        try {

            String sql =
            "INSERT INTO jadwal_posyandu(tanggal_kegiatan,jam_pelayanan,lokasi,jenis_pelayanan) VALUES(?,?,?,?)";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setString(1, tanggalKegiatan);
            ps.setString(2, jamPelayanan);
            ps.setString(3, lokasi);
            ps.setString(4, jenisPelayanan);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean ubah() {

        try {

            String sql =
            "UPDATE jadwal_posyandu SET tanggal_kegiatan=?,jam_pelayanan=?,lokasi=?,jenis_pelayanan=? WHERE id_jadwal=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setString(1, tanggalKegiatan);
            ps.setString(2, jamPelayanan);
            ps.setString(3, lokasi);
            ps.setString(4, jenisPelayanan);
            ps.setInt(5, idJadwal);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean hapus() {

        try {

            String sql =
            "DELETE FROM jadwal_posyandu WHERE id_jadwal=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setInt(1, idJadwal);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public ResultSet cari() {

        try {

            String sql =
            "SELECT * FROM jadwal_posyandu WHERE id_jadwal=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setInt(1, idJadwal);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }

    public ResultSet tampilData() {

        try {

            String sql =
            "SELECT * FROM jadwal_posyandu";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }
}