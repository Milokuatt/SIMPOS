package model;

import Koneksi.Koneksi;
import java.sql.*;

public class Pemeriksaan {

    private int idPemeriksaan;
    private double beratBadan;
    private double tinggiBadan;
    private String vitamin;
    private String hasilPemeriksaan;
    private String tanggalPemeriksaan;

    Connection conn = Koneksi.getKoneksi();

    public int getIdPemeriksaan() {
        return idPemeriksaan;
    }

    public void setIdPemeriksaan(int idPemeriksaan) {
        this.idPemeriksaan = idPemeriksaan;
    }

    public double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(double beratBadan) {
        this.beratBadan = beratBadan;
    }

    public double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public String getVitamin() {
        return vitamin;
    }

    public void setVitamin(String vitamin) {
        this.vitamin = vitamin;
    }

    public String getHasilPemeriksaan() {
        return hasilPemeriksaan;
    }

    public void setHasilPemeriksaan(String hasilPemeriksaan) {
        this.hasilPemeriksaan = hasilPemeriksaan;
    }

    public String getTanggalPemeriksaan() {
        return tanggalPemeriksaan;
    }

    public void setTanggalPemeriksaan(String tanggalPemeriksaan) {
        this.tanggalPemeriksaan = tanggalPemeriksaan;
    }

    public boolean simpan() {

        try {

            String sql =
            "INSERT INTO pemeriksaan(berat_badan,tinggi_badan,vitamin,hasil_pemeriksaan,tanggal_pemeriksaan) VALUES(?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, beratBadan);
            ps.setDouble(2, tinggiBadan);
            ps.setString(3, vitamin);
            ps.setString(4, hasilPemeriksaan);
            ps.setString(5, tanggalPemeriksaan);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean ubah() {

        try {

            String sql =
            "UPDATE pemeriksaan SET berat_badan=?, tinggi_badan=?, vitamin=?, hasil_pemeriksaan=?, tanggal_pemeriksaan=? WHERE id_pemeriksaan=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, beratBadan);
            ps.setDouble(2, tinggiBadan);
            ps.setString(3, vitamin);
            ps.setString(4, hasilPemeriksaan);
            ps.setString(5, tanggalPemeriksaan);
            ps.setInt(6, idPemeriksaan);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean hapus() {

        try {

            String sql =
            "DELETE FROM pemeriksaan WHERE id_pemeriksaan=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idPemeriksaan);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public ResultSet cari() {

        try {

            String sql =
            "SELECT * FROM pemeriksaan WHERE id_pemeriksaan=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idPemeriksaan);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }

    public ResultSet tampilData() {

        try {

            String sql =
            "SELECT * FROM pemeriksaan";

            PreparedStatement ps = conn.prepareStatement(sql);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }
}