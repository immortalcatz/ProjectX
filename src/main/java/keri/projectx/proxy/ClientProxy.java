package keri.projectx.proxy;

import keri.projectx.client.texture.AnimationFX;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProjectXProxy {

    private AnimationFX animation;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.animation = new AnimationFX();
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public TextureAtlasSprite getAnimationIcon() {
        return this.animation.texture;
    }

}
