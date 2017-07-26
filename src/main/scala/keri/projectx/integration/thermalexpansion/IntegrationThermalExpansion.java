/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration.thermalexpansion;

import cofh.api.util.ThermalExpansionHelper;
import cofh.thermalfoundation.init.TFItems;
import keri.projectx.ProjectX;
import keri.projectx.init.ProjectXContent;
import keri.projectx.integration.IIntegrationModule;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class IntegrationThermalExpansion implements IIntegrationModule {

    @Override
    public String getName() {
        return "Thermal Expansion";
    }

    @Override
    public boolean isEnabled() {
        return (Boolean)ProjectX.CONFIG.getProperty("integrationThermalExpansion").getValue();
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
        this.addRecipes();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void preInitClient(FMLPreInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initClient(FMLInitializationEvent event) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void postInitClient(FMLPostInitializationEvent event) {

    }

    private void removeRecipes(){
        for(int meta = 0; meta < 5; meta++){
            ThermalExpansionHelper.removePulverizerRecipe(new ItemStack(ProjectXContent.XYCRONIUM_ORE, 1, meta));
            ThermalExpansionHelper.removePulverizerRecipe(new ItemStack(ProjectXContent.XYCRONIUM_NETHER_ORE, 1, meta));
            ThermalExpansionHelper.removeSmelterRecipe(
                    new ItemStack(ProjectXContent.XYCRONIUM_ORE, 1, meta),
                    new ItemStack(TFItems.itemMaterial, 1, 865)
            );
            ThermalExpansionHelper.removeSmelterRecipe(
                    new ItemStack(ProjectXContent.XYCRONIUM_NETHER_ORE, 1, meta),
                    new ItemStack(TFItems.itemMaterial, 1, 865)
            );
            ThermalExpansionHelper.removeSmelterRecipe(
                    new ItemStack(ProjectXContent.XYCRONIUM_ORE, 1, meta),
                    new ItemStack(Blocks.SAND, 1, OreDictionary.WILDCARD_VALUE)
            );
            ThermalExpansionHelper.removeSmelterRecipe(
                    new ItemStack(ProjectXContent.XYCRONIUM_NETHER_ORE, 1, meta),
                    new ItemStack(Blocks.SAND, 1, OreDictionary.WILDCARD_VALUE)
            );
            ThermalExpansionHelper.removeSmelterRecipe(
                    new ItemStack(ProjectXContent.XYCRONIUM_ORE, 1, meta),
                    new ItemStack(TFItems.itemMaterial, 1, 866)
            );
            ThermalExpansionHelper.removeSmelterRecipe(
                    new ItemStack(ProjectXContent.XYCRONIUM_NETHER_ORE, 1, meta),
                    new ItemStack(TFItems.itemMaterial, 1, 866)
            );
        }
    }

    private void addRecipes(){
        for(int meta = 0; meta < 5; meta++){
            ThermalExpansionHelper.addSmelterRecipe(800,
                    new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 2, meta),
                    new ItemStack(Blocks.SAND, 1, OreDictionary.WILDCARD_VALUE),
                    new ItemStack(ProjectXContent.XYCRONIUM_INGOT, 2, meta),
                    new ItemStack(TFItems.itemMaterial, 1, 864), 25
            );
            ThermalExpansionHelper.addPulverizerRecipe(2400,
                    new ItemStack(ProjectXContent.XYCRONIUM_CRYSTAL, 1, meta),
                    new ItemStack(ProjectXContent.XYCRONIUM_DUST, 2, meta)
            );
        }
    }

}
