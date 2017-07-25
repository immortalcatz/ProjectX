package keri.projectx.integration.albedo;

import codechicken.lib.colour.Colour;
import codechicken.lib.vec.Vector3;
import com.google.common.collect.Queues;
import elucent.albedo.event.GatherLightsEvent;
import elucent.albedo.lighting.Light;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Queue;

@SideOnly(Side.CLIENT)
public class AlbedoEventHandler {

    public static final AlbedoEventHandler INSTANCE = new AlbedoEventHandler();
    private static Queue<Light> TO_ADD = Queues.newArrayDeque();
    private static Queue<Light> TO_REMOVE = Queues.newArrayDeque();

    @SubscribeEvent
    public void onGatherLights(GatherLightsEvent event){
        while(!TO_ADD.isEmpty()){
            event.getLightList().add(TO_ADD.remove());
        }

        while(!TO_REMOVE.isEmpty()){
            event.getLightList().remove(TO_REMOVE.remove());
        }
    }

    public void addLight(Vector3 position, Colour color, float radius){
        TO_ADD.add(Light.builder().pos(position.vec3()).color(color.argb(), true).radius(radius).build());
    }

    public void removeLight(Vector3 position, Colour color, float radius){
        TO_REMOVE.add(Light.builder().pos(position.vec3()).color(color.argb(), true).radius(radius).build());
    }

}
