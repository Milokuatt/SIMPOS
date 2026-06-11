package model;

import Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Pengguna {


private String nik;
private String nama;
private String password;
private String nomorHp;
private String alamat;
private String namaIbuWali;
private String tanggalLahir;

Connection conn = Koneksi.getKoneksi();

public String getNik() {
    return nik;
}

public void setNik(String nik) {
    this.nik = nik;
}

public String getNama() {
    return nama;
}

public void setNama(String nama) {
    this.nama = nama;
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

public String getAlamat() {
    return alamat;
}

public void setAlamat(String alamat) {
    this.alamat = alamat;
}

public String getNamaIbuWali() {
    return namaIbuWali;
}

public void setNamaIbuWali(String namaIbuWali) {
    this.namaIbuWali = namaIbuWali;
}

public String getTanggalLahir() {
    return tanggalLahir;
}

public void setTanggalLahir(String tanggalLahir) {
    this.tanggalLahir = tanggalLahir;
}

// LOGIN

public boolean login() {

    try {

        String sql =
        "SELECT * FROM pengguna WHERE nik=? AND password=?";

        PreparedStatement ps =
        conn.prepareStatement(sql);

        ps.setString(1, nik);
        ps.setString(2, password);

        ResultSet rs =
        ps.executeQuery();

        return rs.next();

    } catch(Exception e) {

        e.printStackTrace();
    }

    return false;
}

// SIMPAN

public boolean simpan() {

try {

    if(nik == null || nik.trim().isEmpty()) {
        throw new Exception("NIK tidak boleh kosong");
    }

    if(nama == null || nama.trim().isEmpty()) {
        throw new Exception("Nama tidak boleh kosong");
    }

    if(password == null || password.trim().isEmpty()) {
        throw new Exception("Password tidak boleh kosong");
    }

    if(tanggalLahir == null || tanggalLahir.trim().isEmpty()) {
        throw new Exception("Tanggal lahir tidak boleh kosong");
    }

    if(!tanggalLahir.matches("\\d{4}-\\d{2}-\\d{2}")) {
        throw new Exception(
        "Format tanggal harus YYYY-MM-DD\nContoh: 2009-03-22");
    }

    String sql =
    "INSERT INTO pengguna(nik,nama,password,nomor_hp,alamat,nama_ibu_wali,tanggal_lahir) VALUES(?,?,?,?,?,?,?)";

    PreparedStatement ps =
    conn.prepareStatement(sql);

    ps.setString(1, nik);
    ps.setString(2, nama);
    ps.setString(3, password);
    ps.setString(4, nomorHp);
    ps.setString(5, alamat);
    ps.setString(6, namaIbuWali);
    ps.setString(7, tanggalLahir);

    int hasil = ps.executeUpdate();

    return hasil > 0;

} catch(Exception e) {

    e.printStackTrace();

    javax.swing.JOptionPane.showMessageDialog(
            null,
            "Gagal menyimpan data:\n" +
            e.getMessage());

    return false;
}
}


// UBAH

public boolean ubah() {

    try {

        String sql =
        "UPDATE pengguna SET nama=?, password=?, nomor_hp=?, alamat=?, nama_ibu_wali=?, tanggal_lahir=? WHERE nik=?";

        PreparedStatement ps =
        conn.prepareStatement(sql);

        ps.setString(1, nama);
        ps.setString(2, password);
        ps.setString(3, nomorHp);
        ps.setString(4, alamat);
        ps.setString(5, namaIbuWali);
        ps.setString(6, tanggalLahir);
        ps.setString(7, nik);

        return ps.executeUpdate() > 0;

    } catch(Exception e) {

        e.printStackTrace();
    }

    return false;
}

// HAPUS

public boolean hapus() {

    try {

        String sql =
        "DELETE FROM pengguna WHERE nik=?";

        PreparedStatement ps =
        conn.prepareStatement(sql);

        ps.setString(1, nik);

        return ps.executeUpdate() > 0;

    } catch(Exception e) {

        e.printStackTrace();
    }

    return false;
}

// CARI

public ResultSet cari() {

    try {

        String sql =
        "SELECT * FROM pengguna WHERE nik=?";

        PreparedStatement ps =
        conn.prepareStatement(sql);

        ps.setString(1, nik);

        return ps.executeQuery();

    } catch(Exception e) {

        e.printStackTrace();
    }

    return null;
}

// TAMPIL DATA

public ResultSet tampilData() {

    try {

        String sql =
        "SELECT * FROM pengguna";

        PreparedStatement ps =
        conn.prepareStatement(sql);

        return ps.executeQuery();

    } catch(Exception e) {

        e.printStackTrace();
    }

    return null;
}

// METHOD BARU

public boolean punyaBalita() {

    try {

        String sql =
        "SELECT * FROM balita WHERE nik_pengguna=?";

        PreparedStatement ps =
        conn.prepareStatement(sql);

        ps.setString(1, nik);

        ResultSet rs =
        ps.executeQuery();

        return rs.next();

    } catch(Exception e) {

        e.printStackTrace();
    }

    return false;
}

public boolean punyaIbuHamil() {

    try {

        String sql =
        "SELECT * FROM ibu_hamil WHERE nik_pengguna=?";

        PreparedStatement ps =
        conn.prepareStatement(sql);

        ps.setString(1, nik);

        ResultSet rs =
        ps.executeQuery();

        return rs.next();

    } catch(Exception e) {

        e.printStackTrace();
    }

    return false;
}

    
}
