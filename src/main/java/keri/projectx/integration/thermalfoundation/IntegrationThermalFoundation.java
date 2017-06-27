/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.integration.thermalfoundation;

import codechicken.lib.util.ItemUtils;
import keri.projectx.ProjectX;
import keri.projectx.init.ProjectXContent;
import keri.projectx.integration.IIntegrationModule;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Iterator;

public class IntegrationThermalFoundation implements IIntegrationModule {

    @Override
    public String getName() {
        return "Thermal Foundation";
    }

    @Override
    public boolean isEnabled(){
        return (Boolean)ProjectX.CONFIG.getProperty("integrationThermalFoundation").getValue();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        this.removeRecipes();
    }

    @Override
    public void preInitClient(FMLPreInitializationEvent event) {

    }

    @Override
    public void initClient(FMLInitializationEvent event) {

    }

    @Override
    public void postInitClient(FMLPostInitializationEvent event) {

    }

    private void removeRecipes(){
        Iterator<IRecipe> recipeIterator = CraftingManager.getInstance().getRecipeList().iterator();

        while(recipeIterator.hasNext()){
            ItemStack output = recipeIterator.next().getRecipeOutput();

            if(ItemUtils.areStacksSameTypeCrafting(output, new ItemStack(ProjectXContent.XYCRONIUM_DUST, 1, OreDictionary.WILDCARD_VALUE))){
                recipeIterator.remove();
            }
        }
    }

}
