package si.fri.prpo.skupina4.dtos;

public class LoveCalcApiOdjemalecDto {
    private String ime1;
    private String ime2;
    private Double procent;
    private String komentar;

    public String getIme1() {
        return ime1;
    }

    public void setIme1(String ime1) {
        this.ime1 = ime1;
    }

    public String getIme2() {
        return ime2;
    }

    public void setIme2(String ime2) {
        this.ime2 = ime2;
    }

    public Double getProcent() {
        return procent;
    }

    public void setProcent(Double procent) {
        this.procent = procent;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
