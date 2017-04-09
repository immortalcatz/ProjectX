package keri.projectx.init;

import keri.projectx.item.ItemXycroniumCrystal;
import keri.projectx.item.ItemXycroniumDust;
import keri.projectx.item.ItemXycroniumIngot;
import keri.projectx.item.ItemXycroniumNugget;
import net.minecraft.item.Item;

public class ProjectXContent {

    public static Item xycroniumCrystal;
    public static Item xycroniumIngot;
    public static Item xycroniumNugget;
    public static Item xycroniumDust;

    public static void preInit(){
        xycroniumCrystal = new ItemXycroniumCrystal();
        xycroniumIngot = new ItemXycroniumIngot();
        xycroniumNugget = new ItemXycroniumNugget();
        xycroniumDust = new ItemXycroniumDust();
    }

    public static void init(){

    }

}
