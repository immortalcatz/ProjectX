package keri.projectx.util;

import net.minecraft.client.resources.I18n;

public class Translations {

    public static String translate(String prefix, String key){
        return I18n.format(prefix + "." + ModPrefs.MODID + "." + key + ".name");
    }

    public static String translate(String prefix, String key, String suffix){
        return I18n.format(prefix + "." + ModPrefs.MODID + "." + key + "." + suffix);
    }

}
