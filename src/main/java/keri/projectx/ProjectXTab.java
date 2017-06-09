package keri.projectx;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProjectXTab extends CreativeTabs {

    public static final ProjectXTab PROJECTX = new ProjectXTab();

    public ProjectXTab() {
        super("projectx.name");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Blocks.DIAMOND_BLOCK, 1, 0);
    }

}
