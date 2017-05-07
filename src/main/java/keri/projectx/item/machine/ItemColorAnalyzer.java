package keri.projectx.item.machine;

import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.item.base.ItemProjectX;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemColorAnalyzer extends ItemProjectX {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public ItemColorAnalyzer() {
        super("color_analyzer");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegistrar registrar) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = registrar.registerIcon(ModPrefs.MODID + ":items/tool/color_analyzer/color_analyzer_base");
        this.texture[1] = registrar.registerIcon(ModPrefs.MODID + ":items/tool/color_analyzer/color_analyzer_frame");
        this.texture[2] = registrar.registerIcon(ModPrefs.MODID + ":items/tool/color_analyzer/color_analyzer_button");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta) {
        return this.texture[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isModelRenderer() {
        return true;
    }

    /**
    @Override
    @SideOnly(Side.CLIENT)
    public IItemRenderingHandler getRenderingHandler() {
        return new RenderColorAnalyzer();
    }
    */

}
