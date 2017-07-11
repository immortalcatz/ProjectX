/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api.energy;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import net.minecraft.nbt.NBTTagCompound;

public enum EnumXynergyType {

    STRAIGHT(0, "straight", 12, false, new ColourRGBA(255, 255, 255, 255)),
    RADIAL(1, "radial", 12, true, new ColourRGBA(255, 20, 200, 255));

    public static final EnumXynergyType[] VALUES = new EnumXynergyType[]{
            STRAIGHT,
            RADIAL
    };
    private int index;
    private String name;
    private int range;
    private boolean isRadial;
    private Colour color;

    EnumXynergyType(int index, String name, int range, boolean isRadial, Colour color){
        this.index = index;
        this.name = name;
        this.range = range;
        this.isRadial = isRadial;
        this.color = color;
    }

    public static String[] toStringArray(){
        String[] names = new String[VALUES.length];

        for(int i = 0; i < VALUES.length; i++){
            names[i] = VALUES[i].getName();
        }

        return names;
    }

    public static EnumXynergyType readFromNBT(NBTTagCompound tag){
        return VALUES[tag.getInteger("xynergy_type")];
    }

    public static NBTTagCompound writeToNBT(NBTTagCompound tag, EnumXynergyType xynergyType){
        tag.setInteger("xynergy_type", xynergyType.getIndex());
        return tag;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public int getRange() {
        return this.range;
    }

    public boolean getIsRadial() {
        return this.isRadial;
    }

    public Colour getColor() {
        return this.color;
    }

}
