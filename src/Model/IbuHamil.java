package model;

import Koneksi.Koneksi;
import java.sql.*;

public class IbuHamil {

    private int idIbu;
    private String nikPengguna;
    private String namaIbu;
    private int usiaKehamilan;
    private String tekananDarah;

    Connection conn = Koneksi.getKoneksi();

    public int getIdIbu() {
        return idIbu;
    }

    public void setIdIbu(int idIbu) {
        this.idIbu = idIbu;
    }

    public String getNikPengguna() {
        return nikPengguna;
    }

    public void setNikPengguna(String nik) {
        this.nikPengguna = nikPengguna;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public int getUsiaKehamilan() {
        return usiaKehamilan;
    }

    public void setUsiaKehamilan(int usiaKehamilan) {
        this.usiaKehamilan = usiaKehamilan;
    }

    public String getTekananDarah() {
        return tekananDarah;
    }

    public void setTekananDarah(String tekananDarah) {
        this.tekananDarah = tekananDarah;
    }

    public boolean simpan() {

        try {

            String sql =
            "INSERT INTO ibu_hamil(nik,nama_ibu,usia_kehamilan,tekanan_darah) VALUES(?,?,?,?)";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setString(1, nikPengguna);
            ps.setString(2, namaIbu);
            ps.setInt(3, usiaKehamilan);
            ps.setString(4, tekananDarah);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean ubah() {

        try {

            String sql =
            "UPDATE ibu_hamil SET nik=?,nama_ibu=?,usia_kehamilan=?,tekanan_darah=? WHERE id_ibu=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setString(1, nikPengguna);
            ps.setString(2, namaIbu);
            ps.setInt(3, usiaKehamilan);
            ps.setString(4, tekananDarah);
            ps.setInt(5, idIbu);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public boolean hapus() {

        try {

            String sql =
            "DELETE FROM ibu_hamil WHERE id_ibu=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setInt(1, idIbu);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(e);
        }

        return false;
    }

    public ResultSet cari() {

        try {

            String sql =
            "SELECT * FROM ibu_hamil WHERE id_ibu=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setInt(1, idIbu);

            return ps.executeQuery();

        } catch (Exception e) {

            System.out.println(e);
        }

        return null;
    }

    public ResultSet tampilData() {

        try {

            String sql =
            "SELECT * FROM ibu_hamil";

            PreparedStatement ps =
            conn.prepareStatement(sql);

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