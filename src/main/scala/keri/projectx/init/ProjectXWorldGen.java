/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.init;

import keri.projectx.ProjectX;
import keri.projectx.world.WorldGenOres;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProjectXWorldGen {

    public static void init(){
        int oreGenerationWeight = (Integer)ProjectX.CONFIG.getProperty("oreGenerationWeight").getValue();
        GameRegistry.registerWorldGenerator(new WorldGenOres(), oreGenerationWeight);
    }

}
