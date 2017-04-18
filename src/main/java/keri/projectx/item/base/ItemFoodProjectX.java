package keri.projectx.item.base;

import keri.ninetaillib.item.ItemFoodBase;
import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.projectx.ProjectX;
import keri.projectx.client.ProjectXTab;
import keri.projectx.util.ModPrefs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFoodProjectX extends ItemFoodBase {

    public ItemFoodProjectX(String itemName, int amount, float saturation, boolean isWolfFood) {
        super(ModPrefs.MODID, itemName, amount, saturation, isWolfFood);
    }

    public ItemFoodProjectX(String itemName, int amount, boolean isWolfFood) {
        super(ModPrefs.MODID, itemName, amount, isWolfFood);
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
