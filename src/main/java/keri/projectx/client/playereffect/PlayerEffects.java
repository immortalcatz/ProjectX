/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.playereffect;

import keri.ninetaillib.mod.playereffect.PlayerEffectHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class PlayerEffects {

    public static void preInit(){
        //KitsuneAlex
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d"), new PlayerEffectQuartzCrystal());
        //xTheLe9enD
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("a6e0fd26-e2d3-4809-8834-e86afd967553"), new PlayerEffectQuartzCrystal());
        //zMarlon
        PlayerEffectHandler.INSTANCE.register(UUID.fromString("64ccc7f5-56a8-45ab-9b2c-38def6a727b0"), new PlayerEffectQuartzCrystal());
    }

}
