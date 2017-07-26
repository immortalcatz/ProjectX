/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration.jei;

import keri.projectx.init.ProjectXContent;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class IntegrationJEI implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(Item.getItemFromBlock(ProjectXContent.XYCRONIUM_LIGHT), stack -> {
            return Integer.toString(stack.getTagCompound().getInteger("color"));
        });
        subtypeRegistry.registerSubtypeInterpreter(Item.getItemFromBlock(ProjectXContent.XYCRONIUM_LIGHT_INVERTED), stack -> {
            return Integer.toString(stack.getTagCompound().getInteger("color"));
        });
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

    }

    @Override
    public void register(IModRegistry registry) {
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(new FabricatorRecipeTransferInfo());
        registry.addRecipeCategoryCraftingItem(new ItemStack(ProjectXContent.FABRICATOR, 1, 0), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {

    }

}
