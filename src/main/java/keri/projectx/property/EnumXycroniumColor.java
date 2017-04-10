package keri.projectx.property;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.util.IPropertyProvider;

public enum EnumXycroniumColor implements IPropertyProvider {

    BLUE("blue", 0, new ColourRGBA(0, 100, 255, 255)),
    GREEN("green", 1, new ColourRGBA(16711935)),
    RED("red", 2, new ColourRGBA(-16776961)),
    DARK("dark", 3, new ColourRGBA(30, 30, 30, 255)),
    LIGHT("light", 4, new ColourRGBA(-1));

    private String name;
    private int ID;
    private ColourRGBA color;

    EnumXycroniumColor(String name, int ID, ColourRGBA color){
        this.name = name;
        this.ID = ID;
        this.color = color;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ColourRGBA getColor(){
        return this.color;
    }

    public static String[] toStringArray(){
        String[] names = new String[values().length];

        for(int i = 0; i < values().length; i++){
            names[i] = values()[i].getName();
        }

        return names;
    }

}
