package keri.projectx.client.handler;

import codechicken.lib.math.MathHelper;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.util.CommonUtils;
import keri.projectx.ProjectX;
import keri.projectx.block.decorative.BlockXycroniumLadder;
import keri.projectx.integration.tinkers.block.BlockXycroniumToolForge;
import keri.projectx.tile.TileEntityXycroniumLadder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BoundingBoxHandler {

    @SubscribeEvent
    public void drawBlockHighlight(DrawBlockHighlightEvent event){
        if(ProjectX.CONFIG.fancyBoundingBoxes){
            if(event.isCancelable() && event.getResult() == Event.Result.DEFAULT){
                if(event.getPhase() == EventPriority.NORMAL){
                    World world = Minecraft.getMinecraft().theWorld;
                    BlockPos pos = event.getTarget().getBlockPos();
                    RayTraceResult hit = event.getTarget();

                    if(hit.typeOfHit == RayTraceResult.Type.BLOCK && hit instanceof CuboidRayTraceResult && !((CuboidRayTraceResult)hit).disableAutoHitboxRender) {
                        this.drawLadderHitbox(world, pos, event.getPlayer(), event.getPartialTicks());
                        this.drawToolForgeHitbox(world, pos, event.getPlayer(), event.getPartialTicks());
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    private void drawLadderHitbox(World world, BlockPos pos, EntityPlayer player, float partialTicks){
        if(world.getBlockState(pos).getBlock() instanceof BlockXycroniumLadder){
            TileEntityXycroniumLadder tile = (TileEntityXycroniumLadder)world.getTileEntity(pos);

            if(tile != null){
                Vector3 axis = new Vector3(0D, 1D, 0D);
                double angle = 0D;

                switch(tile.getOrientation()){
                    case NORTH:
                        angle = 0D;
                        break;
                    case EAST:
                        angle = 270D;
                        break;
                    case SOUTH:
                        angle = 180D;
                        break;
                    case WEST:
                        angle = 90D;
                        break;
                }

                for(int index = 0; index < BlockXycroniumLadder.BLOCK_BOUNDS.length; index++){
                    Cuboid6 cuboid = CommonUtils.devide(BlockXycroniumLadder.BLOCK_BOUNDS[index], 16D).apply(new Rotation(angle * MathHelper.torad, axis).at(Vector3.center));
                    RenderUtils.renderHitBox(player, cuboid.add(pos), partialTicks);
                }
            }
        }
    }

    private void drawToolForgeHitbox(World world, BlockPos pos, EntityPlayer player, float partialTicks){
        if(world.getBlockState(pos).getBlock() instanceof BlockXycroniumToolForge){
            for(int index = 0; index < BlockXycroniumToolForge.BLOCK_BOUNDS.length; index++){
                Cuboid6 cuboid = new Cuboid6(BlockXycroniumToolForge.BLOCK_BOUNDS[index]);
                RenderUtils.renderHitBox(player, cuboid.add(pos), partialTicks);
            }
        }
    }

}
