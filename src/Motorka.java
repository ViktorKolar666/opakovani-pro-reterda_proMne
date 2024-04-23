import java.math.BigDecimal;
import java.time.LocalDate;

public class Motorka {
    private String znacka;
    private int vykon;
    private LocalDate rokVyroby;
    private BigDecimal cena;

    public Motorka(String znacka, int vykon, LocalDate rokVyroby, BigDecimal cena) {
        this.znacka = znacka;
        this.vykon = vykon;
        this.rokVyroby = rokVyroby;
        this.cena = cena;
    }

    public String getZnacka() {
        return znacka;
    }

    public void setZnacka(String znacka) {
        this.znacka = znacka;
    }

    public int getVykon() {
        return vykon;
    }

    public void setVykon(int vykon) {
        this.vykon = vykon;
    }

    public LocalDate getRokVyroby() {
        return rokVyroby;
    }

    public void setRokVyroby(LocalDate rokVyroby) {
        this.rokVyroby = rokVyroby;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }
}
