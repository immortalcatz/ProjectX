package keri.projectx.proxy;

import codechicken.lib.packet.PacketCustom;
import keri.projectx.ProjectX;
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
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public TextureAtlasSprite getAnimatedTexture() {
        return null;
    }

}
