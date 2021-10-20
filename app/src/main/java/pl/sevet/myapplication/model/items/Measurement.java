package pl.sevet.myapplication.model.items;

public enum Measurement {
    PIECES(0),
    AMOUNT(1);

    private final int value;

    Measurement(int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}
