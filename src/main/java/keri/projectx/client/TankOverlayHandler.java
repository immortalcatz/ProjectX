package keri.projectx.client;

import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Cuboid6;
import keri.projectx.multiblock.MultiblockTankHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class TankOverlayHandler {

    @SubscribeEvent
    public void onDrawBlockHightlight(DrawBlockHighlightEvent event){
        if(event.isCancelable() && event.getResult() == Event.Result.DEFAULT){
            if(event.getPhase() == EventPriority.NORMAL){
                Map<BlockPos, List<BlockPos>> tankMap = MultiblockTankHandler.INSTANCE.getTankMap();
                World world = Minecraft.getMinecraft().theWorld;
                BlockPos pos = event.getTarget().getBlockPos();
                RayTraceResult hit = event.getTarget();

                if(!tankMap.isEmpty()){
                    if(hit.typeOfHit == RayTraceResult.Type.BLOCK){
                        for(Map.Entry<BlockPos, List<BlockPos>> entry : tankMap.entrySet()){
                            for(BlockPos subBlock : entry.getValue()){
                                if(pos.equals(entry.getKey())){
                                    Cuboid6 cuboid = new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D).add(subBlock);
                                    RenderUtils.renderHitBox(event.getPlayer(), cuboid, event.getPartialTicks());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
