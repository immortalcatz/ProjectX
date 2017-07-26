/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration.albedo;

import elucent.albedo.event.GatherLightsEvent;
import elucent.albedo.lighting.Light;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AlbedoEventHandler {

    @SubscribeEvent
    public void onGatherLights(GatherLightsEvent event){
        AlbedoLightHandler.INSTANCE.getLights().forEach((k, v) -> event.getLightList().add(this.toLight(v)));
    }

    private Light toLight(AlbedoLight light){
        return Light.builder().pos(light.getPosition().vec3()).color(light.getColor().argb(), true).radius(light.getRadius()).build();
    }

}
