package simpos.model;

public class Jadwal {
    private int id;
    private String namaKegiatan, tanggal, waktu, lokasi, penanggungjawab, deskripsi, status;

    public Jadwal() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNamaKegiatan() { return namaKegiatan; }
    public void setNamaKegiatan(String namaKegiatan) { this.namaKegiatan = namaKegiatan; }
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
    public String getPenanggungjawab() { return penanggungjawab; }
    public void setPenanggungjawab(String penanggungjawab) { this.penanggungjawab = penanggungjawab; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
