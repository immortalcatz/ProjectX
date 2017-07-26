/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration.jei;

import com.google.common.collect.Lists;
import keri.projectx.container.ContainerFabricator;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.Slot;

import java.util.List;

public class FabricatorRecipeTransferInfo implements IRecipeTransferInfo<ContainerFabricator> {

    @Override
    public Class<ContainerFabricator> getContainerClass() {
        return ContainerFabricator.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public boolean canHandle(ContainerFabricator container) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(ContainerFabricator container) {
        List<Slot> slots = Lists.newArrayList();

        for(int i = 0; i < 9; i++){
            slots.add(container.getSlot(i));
        }

        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(ContainerFabricator container){
        List<Slot> slots = Lists.newArrayList();

        for(int i = container.getPlayerInventoryStart(); i < container.inventorySlots.size(); i++){
            slots.add(container.getSlot(i));
        }

        return slots    ;
    }

}
