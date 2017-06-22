package keri.projectx.proxy;

import codechicken.lib.packet.PacketCustom;
import keri.projectx.ProjectX;
import keri.projectx.client.IconHandler;
import keri.projectx.client.playereffect.PlayerEffects;
import keri.projectx.client.render.AnimatedTextureFX;
import keri.projectx.client.render.tesr.TESRFabricator;
import keri.projectx.network.ProjectXCPH;
import keri.projectx.tile.TileEntityFabricator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {

    private static AnimatedTextureFX ANIMATED_TEXTURE;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        int animatinResolution = (Integer)ProjectX.CONFIG.getProperty("animationResolution").getValue();
        ANIMATED_TEXTURE = new AnimatedTextureFX(animatinResolution);
        ProjectX.MOD_HANDLER.handleClientPreInit(event);
        this.registerRenderers();
        IconHandler.INSTANCE.preInit();
        PlayerEffects.preInit();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        ProjectX.MOD_HANDLER.handleClientInit(event);
        PacketCustom.assignHandler(ProjectX.INSTANCE, new ProjectXCPH());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        ProjectX.MOD_HANDLER.handlePostInit(event);
    }

    @Override
    public TextureAtlasSprite getAnimatedTexture() {
        return ANIMATED_TEXTURE.texture;
    }

    private void registerRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFabricator.class, new TESRFabricator());
    }

}
