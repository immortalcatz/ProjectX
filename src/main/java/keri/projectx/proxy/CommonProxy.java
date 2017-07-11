/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.proxy;

import codechicken.lib.packet.PacketCustom;
import keri.projectx.ProjectX;
import keri.projectx.multiblock.MultiBlockCommonProxy;
import keri.projectx.network.ProjectXSPH;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MultiBlockCommonProxy.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        MultiBlockCommonProxy.init(event);
        PacketCustom.assignHandler(ProjectX.INSTANCE, new ProjectXSPH());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        MultiBlockCommonProxy.postInit(event);
    }

    @Override
    public TextureAtlasSprite getAnimatedTexture() {
        return null;
    }

    @Override
    public void resetTooltip(int tooltip, Object... params) {

    }

}
