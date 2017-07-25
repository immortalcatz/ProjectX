/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.proxy;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.texture.TextureUtils;
import keri.projectx.ProjectX;
import keri.projectx.client.IconHandler;
import keri.projectx.client.event.ClientEventHandler;
import keri.projectx.client.event.MultiBlockOverlayRenderer$;
import keri.projectx.client.playereffect.PlayerEffects;
import keri.projectx.client.render.AnimatedTextureFX;
import keri.projectx.client.render.tesr.TESREngineeringTable;
import keri.projectx.client.render.tesr.TESRXynergyNode;
import keri.projectx.featurehack.FeatureHack;
import keri.projectx.integration.IntegrationHandler;
import keri.projectx.network.ProjectXCPH;
import keri.projectx.tile.TileEntityEngineeringTable;
import keri.projectx.tile.TileEntityXynergyNode;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    private static AnimatedTextureFX ANIMATED_TEXTURE;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        int animatinResolution = (Integer)ProjectX.CONFIG.getProperty("animationResolution").getValue();
        ANIMATED_TEXTURE = new AnimatedTextureFX(animatinResolution);
        ProjectX.MOD_HANDLER.handleClientPreInit(event);
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.INSTANCE);
        this.registerRenderers();
        IconHandler.INSTANCE.preInit();
        PlayerEffects.preInit();
        IntegrationHandler.INSTANCE.preInitClient(event);
        MinecraftForge.EVENT_BUS.register(MultiBlockOverlayRenderer$.MODULE$);
        TextureUtils.addIconRegister(MultiBlockOverlayRenderer$.MODULE$);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ProjectX.MOD_HANDLER.handleClientInit(event);
        PacketCustom.assignHandler(ProjectX.INSTANCE, new ProjectXCPH());
        IntegrationHandler.INSTANCE.initClient(event);
        FeatureHack.enableRenderHook();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ProjectX.MOD_HANDLER.handlePostInit(event);
        IntegrationHandler.INSTANCE.postInitClient(event);
    }

    @Override
    public TextureAtlasSprite getAnimatedTexture() {
        return ANIMATED_TEXTURE.texture;
    }

    @Override
    public void resetTooltip(int tooltip, Object... params) {
        ClientEventHandler.INSTANCE.resetTooltip(tooltip, params);
    }

    private void registerRenderers(){
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFabricator.class, new TESRFabricator());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityXynergyNode.class, new TESRXynergyNode());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEngineeringTable.class, new TESREngineeringTable());
    }

}
