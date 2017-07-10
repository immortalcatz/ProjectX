/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.util;

import keri.projectx.api.energy.EnumXynergyClass;
import keri.projectx.api.energy.EnumXynergyType;
import keri.projectx.api.upgrade.EnumUpgradeType;
import net.minecraft.client.resources.I18n;

public class Translations {

    public static final String CONTAINER_INVENTORY = I18n.format("container.inventory");
    public static final String CONTAINER_FABRICATOR = translate("container", "fabricator");
    public static final String CONTAINER_UPGRADES = translate("container", "upgrades");
    public static final String TOOLTIP_UPGRADE_TYPE = translate("tooltip", "upgrade_type");
    public static final String TOOLTIP_WITHER_PROOF = translate("tooltip", "wither_proof");
    public static final String TOOLTIP_XYNERGY_TYPE = translate("tooltip", "xynergy_type");
    public static final String TOOLTIP_XYNERGY_CLASS = translate("tooltip", "xynergy_class");
    public static final String TOOLTIP_TOOL_MODE = translate("tooltip", "tool_mode");
    public static final String TOOLTIP_MODE_WRENCH = translate("tooltip", "mode_wrench");
    public static final String TOOLTIP_MODE_LINK = translate("tooltip", "mode_link");
    public static final String TOOLTIP_MODE_DIAGNOSTIC = translate("tooltip", "mode_diagnostic");
    public static final String TOOLTIP_MODE_SWAP = translate("tooltip", "mode_swap");
    public static final String TOOLTIP_ENERGY_STORED = translate("tooltip", "energy_stored");
    public static final String TOOLTIP_NO_CORE_INSTALLED = translate("tooltip", "no_core_installed");
    public static final String TOOLTIP_DIAGNOSTIC_ERROR = translate("tooltip", "diagnostic_error");

    public static final String getXynergyTypeName(EnumXynergyType xynergyType){
        return translate("xynergy", "type." + xynergyType.getName());
    }

    public static final String getXynergyClassName(EnumXynergyClass xynergyClass){
        return translate("xynergy", "class." + xynergyClass.getName());
    }

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
