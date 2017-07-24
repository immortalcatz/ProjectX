package keri.projectx.integration.jei;

import keri.projectx.init.ProjectXContent;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;

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

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }

}
