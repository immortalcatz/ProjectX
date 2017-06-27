/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.IShiftDescription;
import keri.projectx.api.upgrade.EnumUpgradeType;
import keri.projectx.client.render.item.RenderUpgradeCard;
import keri.projectx.util.ModPrefs;
import keri.projectx.util.Translations;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemUpgradeCard extends ItemProjectX implements IShiftDescription {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public ItemUpgradeCard() {
        super("upgrade_card", EnumUpgradeType.toStringArray());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldAddDescription(ItemStack stack, EntityPlayer player) {
        return stack.getMetadata() > 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addDescription(ItemStack stack, EntityPlayer player, List<String> tooltip) {
        EnumUpgradeType type = EnumUpgradeType.VALUES[stack.getMetadata()];
        String upgradeName = Translations.getUpgradeName(type);
        tooltip.add(TextFormatting.AQUA + Translations.TOOLTIP_UPGRADE_TYPE + ": " + upgradeName);
        tooltip.add(Translations.getUpgradeDescription(type));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register){
        this.texture = new TextureAtlasSprite[EnumUpgradeType.VALUES.length + 1];

        for(int i = 0; i < EnumUpgradeType.VALUES.length; i++){
            this.texture[i] = register.registerIcon(EnumUpgradeType.VALUES[i].getTexture());
        }

        this.texture[EnumUpgradeType.VALUES.length] = register.registerIcon(ModPrefs.MODID + ":items/upgrade/upgrade_back");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumItemRenderType getRenderType() {
        return RenderUpgradeCard.RENDER_TYPE;
    }

}
