package keri.projectx.client;

import keri.ninetaillib.render.registry.SimpleRenderingRegistry;
import keri.ninetaillib.texture.IIconRegistrar;
import keri.projectx.ProjectX;
import keri.projectx.util.ModPrefs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderingRegistryProjectX extends SimpleRenderingRegistry {

    @Override
    public String getModid() {
        return ModPrefs.MODID;
    }

    @Override
    public IIconRegistrar getIconRegistrar() {
        return ProjectX.PROXY.getIconRegistrar();
    }

}
