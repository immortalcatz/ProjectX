package keri.projectx.tile;

public enum ItemIOState {
    OUT, IN;

    public ItemIOState nextState() {
        if (this.ordinal() == values().length - 1) {
            return OUT;
        }
        return values()[ordinal() + 1];
    }
}
