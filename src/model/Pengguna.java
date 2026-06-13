package simpos.model;

public class Pengguna {
    private int id;
    private String nama, username, password, role;

    public Pengguna() {}
    public Pengguna(int id, String nama, String username, String password, String role) {
        this.id = id; this.nama = nama; this.username = username;
        this.password = password; this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override public String toString() { return nama; }
}
