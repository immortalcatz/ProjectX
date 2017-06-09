package keri.projectx.client.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IAnimationHandler {

    TextureAtlasSprite getAnimationIcon(ItemStack stack, int side);

    TextureAtlasSprite getAnimationIcon(IBlockAccess world, BlockPos pos, int side);

    int getAnimationColor(ItemStack stack, int side);

    int getAnimationColor(IBlockAccess world, BlockPos pos, int side);

    int getAnimationBrightness(ItemStack stack, int side);

    int getAnimationBrightness(IBlockAccess world, BlockPos pos, int side);

}
