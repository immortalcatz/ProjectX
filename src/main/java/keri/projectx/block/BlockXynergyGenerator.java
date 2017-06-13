package keri.projectx.block;

import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.projectx.ProjectX;
import keri.projectx.client.render.IAnimationHandler;
import keri.projectx.client.render.RenderSimpleGlow;
import keri.projectx.tile.TileEntityXynergyGenerator;
import keri.projectx.util.EnumXycroniumColor;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockXynergyGenerator extends BlockProjectX<TileEntityXynergyGenerator> implements IAnimationHandler {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockXynergyGenerator() {
        super("xynergy_generator", Material.IRON);
        this.setHardness(1.4F);
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityXynergyGenerator.class, "tileentity." + ModPrefs.MODID + ".xynergy_generator");
    }

    @Nullable
    @Override
    public TileEntityXynergyGenerator createNewTileEntity(World world, int meta) {
        return new TileEntityXynergyGenerator();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        int orientation = MathHelper.floor(placer.rotationYaw * 4D / 360D + 0.5D) & 3;
        BlockAccessUtils.setBlockMetadata(world, pos, orientation, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/xynergy_generator/xynergy_generator_side");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/xynergy_generator/xynergy_generator_bottom");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":blocks/xynergy_generator/xynergy_generator_front");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        switch(meta){
            case 0:
                switch(side){
                    case 0: return this.texture[1];
                    case 1: return this.texture[1];
                    case 2: return this.texture[2];
                    case 3: return this.texture[0];
                    case 4: return this.texture[0];
                    case 5: return this.texture[0];
                }

                break;
            case 1:
                switch(side){
                    case 0: return this.texture[1];
                    case 1: return this.texture[1];
                    case 2: return this.texture[0];
                    case 3: return this.texture[0];
                    case 4: return this.texture[0];
                    case 5: return this.texture[2];
                }

                break;
            case 2:
                switch(side){
                    case 0: return this.texture[1];
                    case 1: return this.texture[1];
                    case 2: return this.texture[0];
                    case 3: return this.texture[2];
                    case 4: return this.texture[0];
                    case 5: return this.texture[0];
                }

                break;
            case 3:
                switch(side){
                    case 0: return this.texture[1];
                    case 1: return this.texture[1];
                    case 2: return this.texture[0];
                    case 3: return this.texture[0];
                    case 4: return this.texture[2];
                    case 5: return this.texture[0];
                }

                break;
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(ItemStack stack, int side) {
        return ProjectX.PROXY.getAnimatedTexture();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getAnimationIcon(IBlockAccess world, BlockPos pos, int side) {
        return ProjectX.PROXY.getAnimatedTexture();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(ItemStack stack, int side) {
        return EnumXycroniumColor.RED.getColor().rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side) {
        return EnumXycroniumColor.RED.getColor().rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(ItemStack stack, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(IBlockAccess world, BlockPos pos, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderSimpleGlow.RENDER_TYPE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT_MIPPED;
    }

}
