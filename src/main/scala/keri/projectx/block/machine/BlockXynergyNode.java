/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.block.machine;

import codechicken.lib.math.MathHelper;
import codechicken.lib.util.ItemUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.Vector3;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.ninetaillib.lib.util.VectorUtils;
import keri.projectx.api.block.IDiagnoseableBlock;
import keri.projectx.api.block.ILinkableBlock;
import keri.projectx.api.block.ISwappableBlock;
import keri.projectx.api.block.IWrenchableBlock;
import keri.projectx.api.energy.EnumCoreType;
import keri.projectx.block.BlockProjectX;
import keri.projectx.client.render.RenderXynergyNode;
import keri.projectx.init.ProjectXContent;
import keri.projectx.tile.TileEntityXynergyNode;
import keri.projectx.util.ModPrefs;
import keri.projectx.util.Translations;
import keri.projectx.util.XynergyToolHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockXynergyNode extends BlockProjectX implements IWrenchableBlock, ILinkableBlock, IDiagnoseableBlock, ISwappableBlock {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] texture;

    public BlockXynergyNode() {
        super("xynergy_node", Material.IRON);
        this.setHardness(1.2F);
    }

    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityXynergyNode.class, "tile." + ModPrefs.MODID + ".xynergy_node");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityXynergyNode();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode) world.getTileEntity(pos);

        if (tile != null) {
            ItemStack heldItem = player.getHeldItem(hand);

            if (!tile.getHasCore()) {
                if (heldItem != null && !heldItem.isEmpty()) {
                    if (heldItem.getItem() == ProjectXContent.POWER_CORE) {
                        if (!world.isRemote) {
                            tile.setXynergyClass(EnumCoreType.VALUES[heldItem.getMetadata()].getXynergyClass());
                            tile.setXynergyType(EnumCoreType.VALUES[heldItem.getMetadata()].getXynergyType());
                            tile.setHasCore(true);
                            tile.markDirty();
                            tile.sendUpdatePacket(true);

                            if (!player.capabilities.isCreativeMode) {
                                heldItem.setCount(heldItem.getCount() - 1);
                            }
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing orientation = null;
        int pitch = Math.round(placer.rotationPitch);

        if (pitch >= 40) {
            orientation = EnumFacing.UP;
        } else if (pitch <= -40) {
            orientation = EnumFacing.DOWN;
        } else {
            orientation = EnumFacing.fromAngle(placer.rotationYaw).getOpposite();
        }

        BlockAccessUtils.setBlockMetadata(world, pos, orientation.getIndex(), 3);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode) world.getTileEntity(pos);

        if (tile != null) {
            if (tile.getHasCore()) {
                if (!world.isRemote) {
                    EnumCoreType coreType = EnumCoreType.getFromClassAndType(tile.getXynergyClass(), tile.getXynergyType());
                    ItemStack stack = new ItemStack(ProjectXContent.POWER_CORE, 1, coreType.getIndex());
                    ItemUtils.dropItem(world, pos, stack);
                }
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public boolean canWrench(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand) {
        return true;
    }

    @Override
    public void onWrenchUsed(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                TileEntityXynergyNode tile = (TileEntityXynergyNode) world.getTileEntity(pos);

                List<ItemStack> drops = Lists.newArrayList();
                drops.add(new ItemStack(this, 1, 0));

                if (tile != null && tile.getHasCore()) {
                    EnumCoreType coreType = EnumCoreType.getFromClassAndType(tile.getXynergyClass(), tile.getXynergyType());
                    ItemStack stack = new ItemStack(ProjectXContent.POWER_CORE, 1, coreType.getIndex());
                    drops.add(stack);
                }

                drops.forEach(stack -> ItemUtils.dropItem(world, pos, stack));
                world.setBlockToAir(pos);
            } else {
                TileEntityXynergyNode tile = (TileEntityXynergyNode) world.getTileEntity(pos);

                if (tile != null) {
                    if (tile.getHasCore()) {
                        EnumCoreType coreType = EnumCoreType.getFromClassAndType(tile.getXynergyClass(), tile.getXynergyType());
                        ItemStack stack = new ItemStack(ProjectXContent.POWER_CORE, 1, coreType.getIndex());
                        ItemUtils.dropItem(world, pos, stack);
                        tile.setHasCore(false);
                        tile.markDirty();
                        tile.sendUpdatePacket(true);
                    }
                }
            }
        }
    }

    @Override
    public boolean canLink(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);

        if(tile != null){
            return tile.getHasCore();
        }

        return false;
    }

    @Override
    public void onLinked(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand, BlockPos linkPos) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode)world.getTileEntity(pos);

        if(tile != null && !world.isRemote && linkPos != pos){
            tile.addDevice(linkPos);
            tile.markDirty();
            tile.sendUpdatePacket(false);
        }
    }

    @Override
    public boolean canDiagnose(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand) {
        return true;
    }

    @Override
    public void onDiagnosed(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand) {}

    @Override
    public void addDiagnosticInformation(World world, BlockPos pos, EntityPlayer player, List<String> tooltip) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode) world.getTileEntity(pos);

        if (tile != null) {
            if (tile.getHasCore()) {
                String xynergyClass = Translations.getXynergyClassName(tile.getXynergyClass());
                String xynergyType = Translations.getXynergyTypeName(tile.getXynergyType());
                tooltip.add(Translations.TOOLTIP_XYNERGY_CLASS + ": " + xynergyClass);
                tooltip.add(Translations.TOOLTIP_XYNERGY_TYPE + ": " + xynergyType);
                tooltip.add(Translations.TOOLTIP_ENERGY_STORED + ": " + tile.getEnergyStored() + "/" + tile.getMaxEnergyStored() + " XU");
            } else {
                tooltip.add(Translations.TOOLTIP_NO_CORE_INSTALLED);
                tooltip.add(Translations.TOOLTIP_ENERGY_STORED + ": 0 XU");
            }
        }
    }

    @Override
    public boolean canSwap(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand) {
        return true;
    }

    @Override
    public void onSwapped(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand) {
        if (!world.isRemote) {
            TileEntityXynergyNode tile = (TileEntityXynergyNode) world.getTileEntity(pos);

            if (tile != null) {
                if (tile.getHasCore()) {
                    EnumCoreType coreType = EnumCoreType.getFromClassAndType(tile.getXynergyClass(), tile.getXynergyType());
                    ItemStack stack = new ItemStack(ProjectXContent.POWER_CORE, 1, coreType.getIndex());
                    XynergyToolHelper.prepareToStore(player, hand);
                    XynergyToolHelper.storeItem(player, hand, stack);
                    tile.setHasCore(false);
                    tile.markDirty();
                    tile.sendUpdatePacket(true);
                } else {
                    ItemStack stack = XynergyToolHelper.disposeStoredItem(player, hand);
                    EnumCoreType coreType = EnumCoreType.VALUES[stack.getMetadata()];
                    tile.setHasCore(true);
                    tile.setXynergyClass(coreType.getXynergyClass());
                    tile.setXynergyType(coreType.getXynergyType());
                    tile.markDirty();
                    tile.sendUpdatePacket(true);
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityXynergyNode tile = (TileEntityXynergyNode) world.getTileEntity(pos);
        List<Transformation> transforms = Lists.newArrayList();
        EnumFacing orientation = EnumFacing.getFront(BlockAccessUtils.getBlockMetadata(state));

        switch (orientation) {
            case DOWN:
                transforms.add(new Rotation(180D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
                break;
            case UP:
                break;
            case NORTH:
                transforms.add(new Rotation(-90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
                break;
            case EAST:
                transforms.add(new Rotation(-90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
                transforms.add(new Rotation(-90D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
                break;
            case SOUTH:
                transforms.add(new Rotation(90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
                break;
            case WEST:
                transforms.add(new Rotation(90D * MathHelper.torad, new Vector3(1D, 0D, 0D)).at(Vector3.center));
                transforms.add(new Rotation(-90D * MathHelper.torad, new Vector3(0D, 1D, 0D)).at(Vector3.center));
                break;
        }

        if (tile != null) {
            if (tile.getHasCore()) {
                Cuboid6 bounds = VectorUtils.divide(new Cuboid6(2D, 0D, 2D, 14D, 14D, 14D), 16D);
                transforms.forEach(t -> bounds.apply(t));
                return bounds.aabb();
            } else {
                Cuboid6 bounds = VectorUtils.divide(new Cuboid6(3D, 0D, 3D, 13D, 2D, 13D), 16D);
                transforms.forEach(t -> bounds.apply(t));
                return bounds.aabb();
            }
        }

        return new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, 1D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.texture = new TextureAtlasSprite[3];
        this.texture[0] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xynergy_node_top");
        this.texture[1] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xynergy_node_bottom");
        this.texture[2] = register.registerIcon(ModPrefs.MODID + ":blocks/machine/xynergy_node_side");
    }

    @Override
    public TextureAtlasSprite getIcon(int meta, EnumFacing side) {
        return this.texture[side.getIndex()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderXynergyNode.RENDER_TYPE;
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
