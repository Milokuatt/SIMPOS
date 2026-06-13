package simpos.dao;

import simpos.model.Pengguna;
import simpos.util.Database;
import java.sql.*;
import java.util.*;

public class PenggunaDAO {

    public Pengguna login(String username, String password) {
        String sql = "SELECT * FROM pengguna WHERE username=? AND password=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Pengguna> getAll() {
        List<Pengguna> list = new ArrayList<>();
        String sql = "SELECT * FROM pengguna ORDER BY nama";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean save(Pengguna p) {
        String sql = "INSERT INTO pengguna (nama, username, password, role) VALUES (?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Pengguna p) {
        String sql = "UPDATE pengguna SET nama=?, username=?, password=?, role=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getRole());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM pengguna WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean isUsernameExists(String username, int excludeId) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM pengguna WHERE username=? AND id!=?")) {
            ps.setString(1, username);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Pengguna map(ResultSet rs) throws SQLException {
        return new Pengguna(rs.getInt("id"), rs.getString("nama"),
            rs.getString("username"), rs.getString("password"), rs.getString("role"));
    }
}
