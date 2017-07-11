/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.integration.chisel;

import keri.projectx.ProjectX;
import keri.projectx.api.color.EnumXycroniumColor;
import keri.projectx.init.ProjectXContent;
import keri.projectx.integration.IIntegrationModule;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class IntegrationChisel implements IIntegrationModule {

    @Override
    public String getName() {
        return "Chisel";
    }

    @Override
    public boolean isEnabled() {
        return (Boolean)ProjectX.CONFIG.getProperty("integrationChisel").getValue();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        this.addVariations();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

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

    @SuppressWarnings("deprecation")
    private void addVariations(){
        for(int meta = 0; meta < 5; meta++){
            String colorName = EnumXycroniumColor.VALUES[meta].getName();
            this.addChiselVariation(ProjectXContent.XYCRONIUM_BRICKS.getStateFromMeta(meta), "projectx_xycronium_bricks_" + colorName);
            this.addChiselVariation(ProjectXContent.XYCRONIUM_BRICKS_SMALL.getStateFromMeta(meta), "projectx_xycronium_bricks_" + colorName);
            this.addChiselVariation(ProjectXContent.XYCRONIUM_BRICKS_CHISELED.getStateFromMeta(meta), "projectx_xycronium_bricks_" + colorName);
        }

        for(int meta = 0; meta < 16; meta++){
            this.addChiselVariation(ProjectXContent.XYCRONIUM_STRUCTURE.getStateFromMeta(meta), "projectx_xycronium_structure");
            this.addChiselVariation(ProjectXContent.XYCRONIUM_PLATE.getStateFromMeta(meta), "projectx_xycronium_plate");
            this.addChiselVariation(ProjectXContent.XYCRONIUM_PLATFORM.getStateFromMeta(meta), "projectx_xycronium_platform");
            this.addChiselVariation(ProjectXContent.XYCRONIUM_SHIELD.getStateFromMeta(meta), "projectx_xycronium_shield");
        }
    }

    private void addChiselVariation(IBlockState state, String groupName){
        if(state != null){
            Block block = state.getBlock();
            int meta = block.getMetaFromState(state);
            FMLInterModComms.sendMessage("chisel", "variation:add", groupName + "|" + block.getRegistryName().toString() + "|" + meta);
        }
    }

}
