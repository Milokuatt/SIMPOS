package simpos.util;

import java.sql.*;

public class Database {

    // Sesuaikan host, port, nama database, username, dan password MariaDB/MySQL kamu
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/simpos_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MariaDB tidak ditemukan di classpath: " + e.getMessage());
            }
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public static void initDatabase() {
        // Skema tabel sudah dibuat lewat script SQL (setup_simpos_db_mariadb.sql)
        // Fungsi ini memastikan koneksi berhasil dan akun admin default ada.
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM pengguna WHERE username='admin'");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO pengguna (nama, username, password, role) VALUES ('Administrator','admin','admin123','admin')");
            }

            System.out.println("Database connected successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}