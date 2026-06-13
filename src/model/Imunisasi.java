package simpos.model;

public class Imunisasi {
    private int id, balitaId, usiaBulan;
    private String namaBalita, jenisImunisasi, tanggal, petugas, keterangan;

    public Imunisasi() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBalitaId() { return balitaId; }
    public void setBalitaId(int balitaId) { this.balitaId = balitaId; }
    public int getUsiaBulan() { return usiaBulan; }
    public void setUsiaBulan(int usiaBulan) { this.usiaBulan = usiaBulan; }
    public String getNamaBalita() { return namaBalita; }
    public void setNamaBalita(String namaBalita) { this.namaBalita = namaBalita; }
    public String getJenisImunisasi() { return jenisImunisasi; }
    public void setJenisImunisasi(String jenisImunisasi) { this.jenisImunisasi = jenisImunisasi; }
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public String getPetugas() { return petugas; }
    public void setPetugas(String petugas) { this.petugas = petugas; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
