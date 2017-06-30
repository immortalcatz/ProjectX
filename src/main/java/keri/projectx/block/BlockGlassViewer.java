/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.block;

import keri.ninetaillib.lib.texture.IIconRegister;
import keri.projectx.client.render.connected.ICTMBlock;
import keri.projectx.client.render.connected.RenderConnectedTexture;
import keri.projectx.client.render.connected.TextureHandlerCTM;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGlassViewer extends BlockProjectX implements ICTMBlock {

    @SideOnly(Side.CLIENT)
    private TextureHandlerCTM textureHandler;

    public BlockGlassViewer() {
        super("glass_viewer", Material.GLASS);
        this.setHardness(1.4F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register){
        this.textureHandler = new TextureHandlerCTM(ModPrefs.MODID + ":blocks/glass_viewer/glass_viewer");
        this.textureHandler.registerIcons(register);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureHandlerCTM getTextureHandler(IBlockAccess world, BlockPos pos) {
        return this.textureHandler;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderConnectedTexture.RENDER_TYPE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

}
