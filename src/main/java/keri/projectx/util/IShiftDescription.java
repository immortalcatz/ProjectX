package keri.projectx.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IShiftDescription {

    boolean shouldAddTooltip(ItemStack stack, EntityPlayer player);

    void addDescription(List<String> list, ItemStack stack, EntityPlayer player);

}
