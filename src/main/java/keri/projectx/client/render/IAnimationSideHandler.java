package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface IAnimationSideHandler {

    TextureAtlasSprite getAnimationIcon(int meta, int side);

    int getAnimationBrightness(int meta, int side);

    ColourRGBA getAnimationColor(int meta, int side);

}
