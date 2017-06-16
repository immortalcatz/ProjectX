package keri.projectx.util;

import net.minecraft.client.resources.I18n;

public class Translations {

    public static final String CONTAINER_FABRICATOR = translate("container", "fabricator");
    public static final String TOOLTIP_PRESS = translate("tooltip", "press");
    public static final String TOOLTIP_INFO = translate("tooltip", "info");
    public static final String TOOLTIP_SHIFT = translate("tooltip", "shift");
    public static final String TOOLTIP_UPGRADE_TYPE = translate("tooltip", "upgrade_type");
    public static final String TOOLTIP_WITHER_PROOF = translate("tooltip", "wither_proof");

    public static final String getUpgradeDescription(EnumUpgradeType type){
        return translate("tooltip", "upgrade_card." + type.getName());
    }

    public static final String getUpgradeName(EnumUpgradeType type){
        return translate("upgrade", type.getName());
    }

    private static String translate(String prefix, String key){
        return I18n.format(prefix + "." + ModPrefs.MODID + "." + key + ".name");
    }

    private static String translate(String prefix, String key, String suffix){
        return I18n.format(prefix + "." + ModPrefs.MODID + "." + key + "." + suffix);
    }

}
