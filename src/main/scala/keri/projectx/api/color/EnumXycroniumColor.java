/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.api.color;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;

public enum EnumXycroniumColor {

    BLUE("blue", 0, new ColourRGBA(0, 100, 255, 255)),
    GREEN("green", 1, new ColourRGBA(16711935)),
    RED("red", 2, new ColourRGBA(-16776961)),
    DARK("dark", 3, new ColourRGBA(30, 30, 30, 255)),
    LIGHT("light", 4, new ColourRGBA(-1));

    public static final EnumXycroniumColor[] VALUES = new EnumXycroniumColor[]{
            BLUE,
            GREEN,
            RED,
            DARK,
            LIGHT
    };
    private String name;
    private int index;
    private Colour color;

    EnumXycroniumColor(String name, int index, Colour color){
        this.name = name;
        this.index = index;
        this.color = color;
    }

    public static String[] toStringArray() {
        String[] names = new String[VALUES.length];

        for (int i = 0; i < VALUES.length; i++) {
            names[i] = VALUES[i].getName();
        }

        return names;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public Colour getColor(){
        return this.color;
    }

}
