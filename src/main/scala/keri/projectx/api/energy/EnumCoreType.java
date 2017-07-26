/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.api.energy;

import codechicken.lib.colour.Colour;

public enum EnumCoreType {

    STRAIGHT_LOW(0, EnumXynergyType.STRAIGHT, EnumXynergyClass.LOW),
    STRAIGHT_MEDIUM(1, EnumXynergyType.STRAIGHT, EnumXynergyClass.MEDIUM),
    STRAIGHT_OMNIPOTENT(2, EnumXynergyType.STRAIGHT, EnumXynergyClass.OMNIPOTENT),
    RADIAL_LOW(3, EnumXynergyType.RADIAL, EnumXynergyClass.LOW),
    RADIAL_MEDIUM(4, EnumXynergyType.RADIAL, EnumXynergyClass.MEDIUM),
    RADIAL_OMNIPOTENT(5, EnumXynergyType.RADIAL, EnumXynergyClass.OMNIPOTENT);

    public static final EnumCoreType[] VALUES = new EnumCoreType[]{
            STRAIGHT_LOW,
            STRAIGHT_MEDIUM,
            STRAIGHT_OMNIPOTENT,
            RADIAL_LOW,
            RADIAL_MEDIUM,
            RADIAL_OMNIPOTENT
    };
    private int index;
    private EnumXynergyType xynergyType;
    private EnumXynergyClass xynergyClass;

    EnumCoreType(int index, EnumXynergyType xynergyType, EnumXynergyClass xynergyClass){
        this.index = index;
        this.xynergyType = xynergyType;
        this.xynergyClass = xynergyClass;
    }

    public static String[] toStringArray() {
        String[] names = new String[VALUES.length];

        for (int i = 0; i < VALUES.length; i++) {
            names[i] = VALUES[i].getName();
        }

        return names;
    }

    public static EnumCoreType getFromClassAndType(EnumXynergyClass xynergyClass, EnumXynergyType xynergyType) {
        for (EnumCoreType type : VALUES) {
            if (type.getXynergyClass() == xynergyClass) {
                if (type.getXynergyType() == xynergyType) {
                    return type;
                }
            }
        }

        return null;
    }

    public int getIndex(){
        return this.index;
    }

    public String getName(){
        return this.xynergyType.getName() + "_" + this.xynergyClass.getName();
    }

    public int getRange(){
        return this.xynergyType.getRange();
    }

    public boolean getIsRadial(){
        return this.xynergyType.getIsRadial();
    }

    public Colour getShellColor(){
        return this.xynergyType.getColor();
    }

    public int getCapacity(){
        return this.xynergyClass.getCapacity();
    }

    public int getMaxIO(){
        return this.xynergyClass.getMaxIO();
    }

    public Colour getCoreColor(){
        return this.xynergyClass.getColor();
    }

    public EnumXynergyType getXynergyType(){
        return this.xynergyType;
    }

    public EnumXynergyClass getXynergyClass(){
        return this.xynergyClass;
    }

}
