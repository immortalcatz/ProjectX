package keri.projectx.item;

import keri.ninetaillib.lib.item.ItemBase;
import keri.projectx.client.ProjectXTab;
import keri.projectx.util.IShiftDescription;
import keri.projectx.util.Translations;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemProjectX extends ItemBase {

    public ItemProjectX(String itemName) {
        super(itemName);
    }

    public ItemProjectX(String itemName, String... subNames) {
        super(itemName, subNames);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if(this instanceof IShiftDescription){
            IShiftDescription iface = (IShiftDescription)this;

            if(iface.shouldAddTooltip(stack, player)){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
                    iface.addDescription(tooltip, stack, player);
                }
                else{
                    String key = TextFormatting.YELLOW + Translations.TOOLTIP_SHIFT;
                    String press = TextFormatting.GRAY + Translations.TOOLTIP_PRESS;
                    String info = TextFormatting.GRAY + Translations.TOOLTIP_INFO;
                    tooltip.add(press + " " + key + " " + info);
                }
            }
        }
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return ProjectXTab.PROJECTX;
    }

}
