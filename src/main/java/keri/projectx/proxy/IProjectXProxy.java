package keri.projectx.proxy;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProjectXProxy {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    TextureAtlasSprite getAnimationIcon();

}
