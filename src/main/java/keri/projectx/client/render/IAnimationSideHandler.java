package keri.projectx.client.render;

import codechicken.lib.colour.ColourRGBA;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

public interface IAnimationSideHandler {

    TextureAtlasSprite getAnimationIcon(IBlockState state, int side);

    int getAnimationBrightness(IBlockState state, int side);

    ColourRGBA getAnimationColor(IBlockState state, int side);

    TextureAtlasSprite getAnimationIcon(ItemStack stack, int side);

    int getAnimationBrightness(ItemStack stack, int side);

    ColourRGBA getAnimationColor(ItemStack stack, int side);

}
