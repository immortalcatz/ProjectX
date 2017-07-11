/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.IShiftDescription;
import keri.projectx.api.energy.EnumCoreType;
import keri.projectx.client.render.item.RenderPowerCore;
import keri.projectx.util.Translations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPowerCore extends ItemProjectX implements IShiftDescription {

    public ItemPowerCore() {
        super("power_core", EnumCoreType.toStringArray());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register){}

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldAddDescription(ItemStack stack, EntityPlayer player) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addDescription(ItemStack stack, EntityPlayer player, List<String> tooltip) {
        EnumCoreType coreType = EnumCoreType.VALUES[stack.getMetadata()];
        tooltip.add(Translations.TOOLTIP_XYNERGY_TYPE + ": " + Translations.getXynergyTypeName(coreType.getXynergyType()));
        tooltip.add(Translations.TOOLTIP_XYNERGY_CLASS + ": " + Translations.getXynergyClassName(coreType.getXynergyClass()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumItemRenderType getRenderType() {
        return RenderPowerCore.RENDER_TYPE;
    }

}
