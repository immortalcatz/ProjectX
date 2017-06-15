package keri.projectx.item;

import keri.ninetaillib.lib.item.ItemBlockBase;
import keri.projectx.util.IShiftDescription;
import keri.projectx.util.Translations;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemBlockProjectX extends ItemBlockBase {

    public ItemBlockProjectX(Block block) {
        super(block);
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
                    String key = TextFormatting.YELLOW + Translations.translate("tooltip", "shift");
                    String press = TextFormatting.GRAY + Translations.translate("tooltip", "press");
                    String info = TextFormatting.GRAY + Translations.translate("tooltip", "info");
                    tooltip.add(press + " " + key + " " + info);
                }
            }
        }
    }

}
