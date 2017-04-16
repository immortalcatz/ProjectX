package keri.projectx.item.base;

import keri.ninetaillib.item.ItemToolHelper;
import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.projectx.ProjectX;
import keri.projectx.client.ProjectXTab;
import keri.projectx.util.ModPrefs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemToolHelperProjectX extends ItemToolHelper {

    public ItemToolHelperProjectX() {
        super(ModPrefs.MODID);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return ProjectXTab.PROJECTX;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IRenderingRegistry getRenderingRegistry() {
        return ProjectX.PROXY.getRenderingRegistry();
    }

}
