package simpos.model;

public class Balita {
    private int id;
    private String nama, nik, tanggalLahir, jenisKelamin, namaIbu, namaAyah, alamat, noTelp;

    public Balita() {}
    public Balita(int id, String nama, String nik, String tanggalLahir, String jenisKelamin,
                  String namaIbu, String namaAyah, String alamat, String noTelp) {
        this.id = id; this.nama = nama; this.nik = nik;
        this.tanggalLahir = tanggalLahir; this.jenisKelamin = jenisKelamin;
        this.namaIbu = namaIbu; this.namaAyah = namaAyah;
        this.alamat = alamat; this.noTelp = noTelp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    public String getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(String tanggalLahir) { this.tanggalLahir = tanggalLahir; }
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public String getNamaIbu() { return namaIbu; }
    public void setNamaIbu(String namaIbu) { this.namaIbu = namaIbu; }
    public String getNamaAyah() { return namaAyah; }
    public void setNamaAyah(String namaAyah) { this.namaAyah = namaAyah; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }

    @Override public String toString() { return nama; }
}
