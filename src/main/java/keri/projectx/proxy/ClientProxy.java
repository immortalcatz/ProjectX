package keri.projectx.proxy;

import keri.projectx.ProjectX;
import keri.projectx.client.render.AnimatedTextureFX;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {

    private static AnimatedTextureFX ANIMATED_TEXTURE;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ANIMATED_TEXTURE = new AnimatedTextureFX(64);
        ProjectX.MOD_HANDLER.handleClientPreInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        ProjectX.MOD_HANDLER.handleClientInit(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        ProjectX.MOD_HANDLER.handlePostInit(event);
    }

    @Override
    public TextureAtlasSprite getAnimatedTexture() {
        return ANIMATED_TEXTURE.texture;
    }

}
