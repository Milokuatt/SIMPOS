package simpos.model;

public class Pemeriksaan {
    private int id, balitaId;
    private double beratBadan, tinggiBadan, lingkarKepala, lingkarLengan;
    private String namaBalita, tanggal, statusGizi, petugas, catatan;

    public Pemeriksaan() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBalitaId() { return balitaId; }
    public void setBalitaId(int balitaId) { this.balitaId = balitaId; }
    public double getBeratBadan() { return beratBadan; }
    public void setBeratBadan(double beratBadan) { this.beratBadan = beratBadan; }
    public double getTinggiBadan() { return tinggiBadan; }
    public void setTinggiBadan(double tinggiBadan) { this.tinggiBadan = tinggiBadan; }
    public double getLingkarKepala() { return lingkarKepala; }
    public void setLingkarKepala(double lingkarKepala) { this.lingkarKepala = lingkarKepala; }
    public double getLingkarLengan() { return lingkarLengan; }
    public void setLingkarLengan(double lingkarLengan) { this.lingkarLengan = lingkarLengan; }
    public String getNamaBalita() { return namaBalita; }
    public void setNamaBalita(String namaBalita) { this.namaBalita = namaBalita; }
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public String getStatusGizi() { return statusGizi; }
    public void setStatusGizi(String statusGizi) { this.statusGizi = statusGizi; }
    public String getPetugas() { return petugas; }
    public void setPetugas(String petugas) { this.petugas = petugas; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}
