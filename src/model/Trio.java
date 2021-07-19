package model;

public class Trio {
    private Hex elementary;
    private Hex finite;
    private int take;

    public Trio(Hex elementary, Hex finite, int take) {
        this.elementary = elementary;
        this.finite = finite;
        this.take = take;
    }

    public Hex getElementary() {
        return elementary;
    }

    public Hex getFinite() {
        return finite;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public void setElementary(Hex hex) {
        this.elementary = hex;
    }

    public void setFinite(Hex finite) {
        this.finite = finite;
    }
}
