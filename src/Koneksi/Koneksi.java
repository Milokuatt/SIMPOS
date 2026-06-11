package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection koneksi;

    public static Connection getKoneksi() {

        try {

            if (koneksi == null) {

                String url = "jdbc:mysql://localhost:3306/simpos";
                String user = "root";
                String password = "";

                Class.forName("com.mysql.jdbc.Driver");

                koneksi = DriverManager.getConnection(
                        url, user, password);

                System.out.println("Koneksi Berhasil");
            }

        } catch (Exception e) {

            System.out.println("Koneksi Gagal : " + e.getMessage());
        }

        return koneksi;
    }
}