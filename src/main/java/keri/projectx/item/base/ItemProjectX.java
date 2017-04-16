package keri.projectx.item.base;

import keri.ninetaillib.item.ItemBase;
import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.projectx.ProjectX;
import keri.projectx.client.ProjectXTab;
import keri.projectx.util.ModPrefs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemProjectX extends ItemBase {

    public ItemProjectX(String itemName) {
        super(ModPrefs.MODID, itemName);
    }

    public ItemProjectX(String itemName, String... subNames) {
        super(ModPrefs.MODID, itemName, subNames);
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
