package keri.projectx.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ProjectXTab extends CreativeTabs {

    public static final ProjectXTab PROJECTX = new ProjectXTab();

    public ProjectXTab() {
        super("projectx.name");
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.EMERALD_BLOCK);
    }

}
