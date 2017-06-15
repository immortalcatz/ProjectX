package keri.projectx.item;

import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.projectx.client.render.item.RenderUpgradeCard;
import keri.projectx.util.EnumUpgradeType;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemUpgradeCard extends ItemProjectX {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public ItemUpgradeCard() {
        super("upgrade_card", EnumUpgradeType.toStringArray());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register){
        this.texture = new TextureAtlasSprite[EnumUpgradeType.VALUES.length + 1];

        for(EnumUpgradeType type : EnumUpgradeType.VALUES){
            this.texture[type.getIndex()] = register.registerIcon(type.getTexture());
        }

        this.texture[EnumUpgradeType.VALUES.length + 1] = register.registerIcon(ModPrefs.MODID + "items/upgrade/upgrade_back");
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
