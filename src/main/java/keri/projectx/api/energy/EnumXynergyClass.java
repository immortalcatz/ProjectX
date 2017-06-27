/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api.energy;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;

public enum EnumXynergyClass {

    LOW(0, "low", 10000, 200, new ColourRGBA(255, 255, 255, 255)),
    MEDIUM(1, "medium", 50000, 500, new ColourRGBA(255, 255, 255, 255)),
    OMNIPOTENT(2, "omnipotent", 100000, 1000, new ColourRGBA(255, 255, 255, 255));

    public static final EnumXynergyClass[] VALUES = new EnumXynergyClass[]{
            LOW,
            MEDIUM,
            OMNIPOTENT
    };
    private int index;
    private String name;
    private int capacity;
    private int maxIO;
    private Colour color;

    EnumXynergyClass(int index, String name, int capacity, int maxIO, Colour color){
        this.index = index;
        this.name = name;
        this.capacity = capacity;
        this.maxIO = maxIO;
        this.color = color;
    }

    public int getIndex(){
        return this.index;
    }

    public String getName(){
        return this.name;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public int getMaxIO(){
        return this.maxIO;
    }

    public Colour getColor(){
        return this.color;
    }

    public static String[] toStringArray(){
        String[] names = new String[VALUES.length];

        for(int i = 0; i < VALUES.length; i++){
            names[i] = VALUES[i].getName();
        }

        return names;
    }

}
