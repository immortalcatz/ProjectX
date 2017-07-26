/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.proxy;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.world.WorldExtensionManager;
import keri.projectx.ProjectX;
import keri.projectx.data.ProjectXWorldExtensionInstantiator$;
import keri.projectx.featurehack.FeatureHack;
import keri.projectx.network.ProjectXSPH;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        PacketCustom.assignHandler(ProjectX.INSTANCE, new ProjectXSPH());
        WorldExtensionManager.registerWorldExtension(ProjectXWorldExtensionInstantiator$.MODULE$);
        FeatureHack.enableUpdateHook();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public TextureAtlasSprite getAnimatedTexture() {
        return null;
    }

    @Override
    public void resetTooltip(int tooltip, Object... params) {

    }

}
