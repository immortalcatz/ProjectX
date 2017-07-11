/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api.upgrade;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import keri.projectx.api.ProjectXAPI;
import keri.projectx.api.color.EnumXycroniumColor;

public enum EnumUpgradeType {

    BLANK(0, "blank", new ColourRGBA(255, 255, 255, 255), "blank"),
    MACHINE_IO(1, "machine_io", EnumXycroniumColor.BLUE.getColor(), "machine_io"),
    SPEED(2, "speed", EnumXycroniumColor.RED.getColor(), "speed"),
    CAPACITY(3, "capacity", EnumXycroniumColor.GREEN.getColor(), "capacity");

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
        return ProjectXAPI.MODID + ":items/upgrade/upgrade_" + this.texture;
    }

    public static String[] toStringArray(){
        String[] names = new String[VALUES.length];

        for(int i = 0; i < VALUES.length; i++){
            names[i] = VALUES[i].getName();
        }

        return names;
    }

}
