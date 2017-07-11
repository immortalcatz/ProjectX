/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api;

import net.minecraftforge.fml.common.Loader;

public class ProjectXAPI {

    public static final String MODID = "projectx";

    public static boolean isModLoaded(){
        return Loader.isModLoaded(MODID);
    }

}
