package model;

import Koneksi.Koneksi;
import java.sql.*;

public class Balita {

    private int idBalita;
    private String nikPengguna;
    private String namaBalita;
    private String tanggalLahir;
    private String jenisKelamin;
    private double beratBadan;
    private double tinggiBadan;

    Connection conn = Koneksi.getKoneksi();

    public int getIdBalita() {
        return idBalita;
    }

    public void setIdBalita(int idBalita) {
        this.idBalita = idBalita;
    }

    public String getNikPengguna() {
        return nikPengguna;
    }

    public void setNikPengguna(String nik) {
        this.nikPengguna = nikPengguna;
    }

    public String getNamaBalita() {
        return namaBalita;
    }

    public void setNamaBalita(String namaBalita) {
        this.namaBalita = namaBalita;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
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

    public boolean simpan() {
        try {
            String sql = "INSERT INTO balita(nik,nama_balita,tanggal_lahir,jenis_kelamin,berat_badan,tinggi_badan) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nikPengguna);
            ps.setString(2, namaBalita);
            ps.setString(3, tanggalLahir);
            ps.setString(4, jenisKelamin);
            ps.setDouble(5, beratBadan);
            ps.setDouble(6, tinggiBadan);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean ubah() {
        try {
            String sql = "UPDATE balita SET nik=?,nama_balita=?,tanggal_lahir=?,jenis_kelamin=?,berat_badan=?,tinggi_badan=? WHERE id_balita=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nikPengguna);
            ps.setString(2, namaBalita);
            ps.setString(3, tanggalLahir);
            ps.setString(4, jenisKelamin);
            ps.setDouble(5, beratBadan);
            ps.setDouble(6, tinggiBadan);
            ps.setInt(7, idBalita);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean hapus() {
        try {
            String sql = "DELETE FROM balita WHERE id_balita=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idBalita);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ResultSet cari() {
        try {
            String sql = "SELECT * FROM balita WHERE id_balita=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idBalita);

            return ps.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ResultSet tampilData() {
        try {
            String sql = "SELECT * FROM balita";

            PreparedStatement ps = conn.prepareStatement(sql);

            return ps.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void setNik(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}