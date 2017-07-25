package keri.projectx.api.block;

import codechicken.lib.colour.Colour;
import codechicken.lib.vec.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IColorableBlock {

    Colour getStoredColor(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand, Vector3 hit);

    void setStoredColor(World world, BlockPos pos, EntityPlayer player, EnumFacing side, EnumHand hand, Vector3 hit, Colour color);

}
