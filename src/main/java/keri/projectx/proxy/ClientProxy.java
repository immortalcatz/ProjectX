package keri.projectx.proxy;

import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.ninetaillib.texture.DefaultIconRegistrar;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.ProjectX;
import keri.projectx.client.ProjectXModels;
import keri.projectx.client.RenderingRegistryProjectX;
import keri.projectx.client.handler.BoundingBoxHandler;
import keri.projectx.client.render.AnimationFX;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProjectXProxy {

    private static final DefaultIconRegistrar iconRegistrar = new DefaultIconRegistrar();
    private static final RenderingRegistryProjectX renderingRegistry = new RenderingRegistryProjectX();
    private static AnimationFX animationFX;

    @Override
    public void loadModels(FMLPreInitializationEvent event) {
        ProjectXModels.preInit(event);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new BoundingBoxHandler());
        this.animationFX = new AnimationFX(ProjectX.CONFIG.animationResolution.getValue());
        this.iconRegistrar.preInit();
        this.renderingRegistry.preInit();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        this.renderingRegistry.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        this.renderingRegistry.postInit();
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
