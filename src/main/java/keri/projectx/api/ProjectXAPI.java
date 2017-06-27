/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api;

import keri.projectx.util.ModPrefs;
import net.minecraftforge.fml.common.Loader;

public class ProjectXAPI {

    public static boolean isModLoaded(){
        return Loader.isModLoaded(ModPrefs.MODID);
    }

}
