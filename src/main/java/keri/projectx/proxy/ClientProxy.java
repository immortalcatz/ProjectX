package keri.projectx.proxy;

import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.ninetaillib.texture.DefaultIconRegistrar;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.client.RenderingRegistryProjectX;
import keri.projectx.client.render.AnimationFX;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProjectXProxy {

    private static final DefaultIconRegistrar iconRegistrar = new DefaultIconRegistrar();
    private static final RenderingRegistryProjectX renderingRegistry = new RenderingRegistryProjectX();
    private static AnimationFX animationFX;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.iconRegistrar.preInit();
        this.renderingRegistry.preInit();
        this.animationFX = new AnimationFX();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        this.renderingRegistry.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public IRenderingRegistry getRenderingRegistry() {
        return this.renderingRegistry;
    }

    @Override
    public IIconRegistrar getIconRegistrar() {
        return this.iconRegistrar;
    }

    @Override
    public TextureAtlasSprite getAnimationIcon() {
        return this.animationFX.texture;
    }

}
