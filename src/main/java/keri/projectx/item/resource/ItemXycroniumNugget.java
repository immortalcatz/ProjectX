package keri.projectx.item.resource;

import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.item.base.ItemProjectX;
import keri.projectx.property.EnumXycroniumColor;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemXycroniumNugget extends ItemProjectX {

    public ItemXycroniumNugget() {
        super("xycronium_nugget", EnumXycroniumColor.toStringArray());
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegistrar registrar) {
        this.texture = new TextureAtlasSprite[EnumXycroniumColor.values().length];

        for(int i = 0; i < EnumXycroniumColor.values().length; i++){
            this.texture[i] = registrar.registerIcon(ModPrefs.MODID + ":items/resource/xycronium_nugget_" + EnumXycroniumColor.values()[i].getName());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

}
