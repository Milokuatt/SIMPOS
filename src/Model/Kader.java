package model;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Kader {

    private int idKader;
    private String nama;
    private String username;
    private String password;
    private String nomorHp;

    Connection conn = Koneksi.getKoneksi();

    // Getter Setter

    public int getIdKader() {
        return idKader;
    }

    public void setIdKader(int idKader) {
        this.idKader = idKader;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public void setNomorHp(String nomorHp) {
        this.nomorHp = nomorHp;
    }

    // SIMPAN

    public boolean simpan() {

        try {

            String sql =
            "INSERT INTO kader(nama,username,password,nomor_hp) VALUES(?,?,?,?)";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setString(1, nama);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, nomorHp);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    // UPDATE

    public boolean ubah() {

        try {

            String sql =
            "UPDATE kader SET nama=?,username=?,password=?,nomor_hp=? WHERE id_kader=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setString(1, nama);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, nomorHp);
            ps.setInt(5, idKader);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    // HAPUS

    public boolean hapus() {

        try {

            String sql =
            "DELETE FROM kader WHERE id_kader=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setInt(1, idKader);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    // CARI

    public ResultSet cari() {

        try {

            String sql =
            "SELECT * FROM kader WHERE id_kader=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setInt(1, idKader);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }

    // TAMPIL SEMUA DATA

public boolean login() {

    try {

        String sql =
        "SELECT * FROM kader WHERE username=? AND password=?";

        PreparedStatement ps =
        conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs =
        ps.executeQuery();

        return rs.next();

    } catch(Exception e) {

        e.printStackTrace();
    }

    return false;
}
}