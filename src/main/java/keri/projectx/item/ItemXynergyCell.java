package keri.projectx.item;

import keri.ninetaillib.render.IItemRenderingHandler;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.ProjectX;
import keri.projectx.client.render.RenderXynergyCell;
import keri.projectx.property.EnumXycroniumColor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemXynergyCell extends ItemProjectX {

    public ItemXynergyCell() {
        super("xynergy_cell", EnumXycroniumColor.toStringArray());
    }

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegistrar registrar) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = registrar.registerIcon("minecraft:blocks/iron_block");
        this.texture[1] = registrar.registerIcon("minecraft:blocks/anvil_base");
        this.texture[2] = ProjectX.PROXY.getAnimationIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IItemRenderingHandler getRenderingHandler() {
        return new RenderXynergyCell();
    }

}
