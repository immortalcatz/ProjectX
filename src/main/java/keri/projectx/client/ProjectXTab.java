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
    private int meta = 0;

    public ProjectXTab() {
        super("projectx.name");
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ProjectXContent.xycroniumBricks);
    }

    @Override
    public ItemStack getIconItemStack(){
        if(this.ticks < 64){
            this.ticks++;
        }
        else{
            if(this.meta < 4){
                this.meta++;
            }
            else{
                this.meta = 0;
            }

            this.ticks = 0;
        }

        return new ItemStack(ProjectXContent.xycroniumBricks, 1, this.meta);
    }

}
