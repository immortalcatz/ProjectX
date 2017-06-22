package keri.projectx.block;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.EnumDyeColor;
import keri.projectx.ProjectX;
import keri.projectx.tile.TileEntityXycroniumLight;
import keri.projectx.util.ModPrefs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockXycroniumLight extends BlockAnimationHandler<TileEntityXycroniumLight> {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite texture;

    public BlockXycroniumLight() {
        super("xycronium_light", Material.GLASS);
        this.setHardness(1.2F);
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityXycroniumLight.class, "tile." + ModPrefs.MODID + ".xycronium_light");
    }

    @Nullable
    @Override
    public TileEntityXycroniumLight createNewTileEntity(World world, int meta) {
        return new TileEntityXycroniumLight();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityXycroniumLight tile = (TileEntityXycroniumLight)world.getTileEntity(pos);
        ItemStack heldItem = player.getHeldItem(hand);

        if(!heldItem.isEmpty()){
            if(heldItem.getItem() == Items.DYE){
                if(tile != null){
                    tile.setColor(EnumDyeColor.VALUES[heldItem.getMetadata()].getColor());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = register.registerIcon(ModPrefs.MODID + ":blocks/xycronium_light");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(int meta, int side) {
        return this.texture;
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
        return new ColourRGBA(255, 255, 255, 255).rgba();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationColor(IBlockAccess world, BlockPos pos, int side){
        TileEntityXycroniumLight tile = (TileEntityXycroniumLight)world.getTileEntity(pos);

        if(tile != null){
            return tile.getColor().rgba();
        }
        else{
            return new ColourRGBA(255, 255, 255, 255).rgba();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(ItemStack stack, int side) {
        return 0x00F000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getAnimationBrightness(IBlockAccess world, BlockPos pos, int side){
        return 0x00B0000F;
    }

}
