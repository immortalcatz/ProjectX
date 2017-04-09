package keri.projectx.property;

import keri.ninetaillib.util.IPropertyProvider;

public enum EnumXycroniumColor implements IPropertyProvider {

    BLUE("blue", 0),
    GREEN("green", 1),
    RED("red", 2),
    DARK("dark", 3),
    LIGHT("light", 4);

    private String name;
    private int ID;

    EnumXycroniumColor(String name, int ID){
        this.name = name;
        this.ID = ID;
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

    public static String[] toStringArray(){
        String[] names = new String[values().length];

        for(int i = 0; i < values().length; i++){
            names[i] = values()[i].getName();
        }

        return names;
    }

}
