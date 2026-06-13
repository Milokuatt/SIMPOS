package simpos.dao;

import simpos.model.Pemeriksaan;
import simpos.util.Database;
import java.sql.*;
import java.util.*;

public class PemeriksaanDAO {

    public List<Pemeriksaan> getAll() {
        List<Pemeriksaan> list = new ArrayList<>();
        String sql = "SELECT p.*, b.nama as nama_balita FROM pemeriksaan p JOIN balita b ON p.balita_id=b.id ORDER BY p.tanggal DESC";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Pemeriksaan> getByOrtuNama(String namaOrtu) {
        List<Pemeriksaan> list = new ArrayList<>();
        String sql = "SELECT p.*, b.nama as nama_balita FROM pemeriksaan p JOIN balita b ON p.balita_id=b.id " +
                "WHERE b.nama_ibu = ? OR b.nama_ayah = ? ORDER BY p.tanggal DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaOrtu);
            ps.setString(2, namaOrtu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean save(Pemeriksaan p) {
        String sql = "INSERT INTO pemeriksaan (balita_id, tanggal, berat_badan, tinggi_badan, lingkar_kepala, lingkar_lengan, status_gizi, petugas, catatan) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getBalitaId());
            ps.setString(2, p.getTanggal());
            ps.setDouble(3, p.getBeratBadan());
            ps.setDouble(4, p.getTinggiBadan());
            ps.setDouble(5, p.getLingkarKepala());
            ps.setDouble(6, p.getLingkarLengan());
            ps.setString(7, p.getStatusGizi());
            ps.setString(8, p.getPetugas());
            ps.setString(9, p.getCatatan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Pemeriksaan p) {
        String sql = "UPDATE pemeriksaan SET balita_id=?, tanggal=?, berat_badan=?, tinggi_badan=?, lingkar_kepala=?, lingkar_lengan=?, status_gizi=?, petugas=?, catatan=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getBalitaId());
            ps.setString(2, p.getTanggal());
            ps.setDouble(3, p.getBeratBadan());
            ps.setDouble(4, p.getTinggiBadan());
            ps.setDouble(5, p.getLingkarKepala());
            ps.setDouble(6, p.getLingkarLengan());
            ps.setString(7, p.getStatusGizi());
            ps.setString(8, p.getPetugas());
            ps.setString(9, p.getCatatan());
            ps.setInt(10, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM pemeriksaan WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int count() {
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM pemeriksaan")) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { e.printStackTrace(); return 0; }
    }

    private Pemeriksaan map(ResultSet rs) throws SQLException {
        Pemeriksaan p = new Pemeriksaan();
        p.setId(rs.getInt("id"));
        p.setBalitaId(rs.getInt("balita_id"));
        p.setNamaBalita(rs.getString("nama_balita"));
        p.setTanggal(rs.getString("tanggal"));
        p.setBeratBadan(rs.getDouble("berat_badan"));
        p.setTinggiBadan(rs.getDouble("tinggi_badan"));
        p.setLingkarKepala(rs.getDouble("lingkar_kepala"));
        p.setLingkarLengan(rs.getDouble("lingkar_lengan"));
        p.setStatusGizi(rs.getString("status_gizi"));
        p.setPetugas(rs.getString("petugas"));
        p.setCatatan(rs.getString("catatan"));
        return p;
    }
}
