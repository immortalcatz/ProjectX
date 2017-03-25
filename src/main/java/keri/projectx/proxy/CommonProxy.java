package keri.projectx.proxy;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProjectXProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public TextureAtlasSprite getAnimationIcon() {
        return null;
    }

}
