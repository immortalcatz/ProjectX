/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.util;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;

public enum EnumUpgradeType {

    BLANK(0, "blank", new ColourRGBA(255, 255, 255, 255), "blank"),
    MACHINE_IO(1, "machine_io", EnumXycroniumColor.GREEN.getColor(), "machine_io"),
    SPEED(2, "speed", EnumXycroniumColor.RED.getColor(), "speed"),
    CAPACITY(3, "capacity", EnumXycroniumColor.LIGHT.getColor(), "capacity");

    public static final EnumUpgradeType[] VALUES = new EnumUpgradeType[]{
            BLANK,
            MACHINE_IO,
            SPEED,
            CAPACITY
    };
    private int index;
    private String name;
    private Colour color;
    private String texture;

    EnumUpgradeType(int index, String name, Colour color, String texture){
        this.index = index;
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public int getIndex(){
        return this.index;
    }

    public String getName(){
        return this.name;
    }

    public Colour getColor(){
        return this.color;
    }

    public String getTexture(){
        return ModPrefs.MODID + ":items/upgrade/upgrade_" + this.texture;
    }

    public static String[] toStringArray(){
        String[] names = new String[values().length];

        for(int i = 0; i < values().length; i++){
            names[i] = values()[i].getName();
        }

        return names;
    }

}
