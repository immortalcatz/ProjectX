package keri.projectx.client.handler;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GameOverlayHandler {

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event){
        if(event.isCancelable() && event.getPhase() == EventPriority.NORMAL){
            if(event.getType() == RenderGameOverlayEvent.ElementType.AIR){

            }
        }
    }

}
