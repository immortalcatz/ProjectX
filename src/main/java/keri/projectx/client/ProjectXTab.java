package keri.projectx.client;

import keri.projectx.init.ProjectXContent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ProjectXTab extends CreativeTabs {

    public static final ProjectXTab PROJECTX = new ProjectXTab();
    private int ticks = 0;
    private int color = 0;

    public ProjectXTab() {
        super("projectx.name");
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ProjectXContent.xycroniumOre);
    }

    @Override
    public ItemStack getIconItemStack() {
        if(this.ticks < 32){
            this.ticks++;
        }
        else{
            if(this.color < 4){
                this.color++;
            }
            else{
                this.color = 0;
            }

            this.ticks = 0;
        }

        return new ItemStack(ProjectXContent.xycroniumOre, 1, this.color);
    }

}
