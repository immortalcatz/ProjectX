package keri.projectx.item;

import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.property.EnumXycroniumColor;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemXycroniumCrystal extends ItemProjectX {

    public ItemXycroniumCrystal() {
        super("xycronium_crystal", EnumXycroniumColor.toStringArray());
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegistrar registrar) {
        this.texture = new TextureAtlasSprite[EnumXycroniumColor.values().length];

        for(int i = 0; i < EnumXycroniumColor.values().length; i++){
            this.texture[i] = registrar.registerIcon(ModPrefs.MODID + ":items/resource/xycronium_crystal_" + EnumXycroniumColor.values()[i].getName());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

}
