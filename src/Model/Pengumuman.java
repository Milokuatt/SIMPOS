package model;

import Koneksi.Koneksi;
import java.sql.*;

public class Pengumuman {

    private int idPengumuman;
    private String judul;
    private String isiPengumuman;
    private String tanggalPengumuman;

    Connection conn = Koneksi.getKoneksi();

    public int getIdPengumuman() {
        return idPengumuman;
    }

    public void setIdPengumuman(int idPengumuman) {
        this.idPengumuman = idPengumuman;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsiPengumuman() {
        return isiPengumuman;
    }

    public void setIsiPengumuman(String isiPengumuman) {
        this.isiPengumuman = isiPengumuman;
    }

    public String getTanggalPengumuman() {
        return tanggalPengumuman;
    }

    public void setTanggalPengumuman(String tanggalPengumuman) {
        this.tanggalPengumuman = tanggalPengumuman;
    }

    public boolean simpan() {

        try {

            String sql =
            "INSERT INTO pengumuman(judul,isi_pengumuman,tanggal_pengumuman) VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, judul);
            ps.setString(2, isiPengumuman);
            ps.setString(3, tanggalPengumuman);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean ubah() {

        try {

            String sql =
            "UPDATE pengumuman SET judul=?, isi_pengumuman=?, tanggal_pengumuman=? WHERE id_pengumuman=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, judul);
            ps.setString(2, isiPengumuman);
            ps.setString(3, tanggalPengumuman);
            ps.setInt(4, idPengumuman);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean hapus() {

        try {

            String sql =
            "DELETE FROM pengumuman WHERE id_pengumuman=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idPengumuman);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public ResultSet cari() {

        try {

            String sql =
            "SELECT * FROM pengumuman WHERE id_pengumuman=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idPengumuman);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }

    public ResultSet tampilData() {

        try {

            String sql =
            "SELECT * FROM pengumuman";

            PreparedStatement ps = conn.prepareStatement(sql);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }
}