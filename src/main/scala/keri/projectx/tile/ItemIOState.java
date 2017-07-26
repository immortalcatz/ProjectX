/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

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
