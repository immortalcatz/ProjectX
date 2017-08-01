/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.render;

import keri.projectx.util.ModPrefs;
import net.minecraft.util.ResourceLocation;

public enum EnumHedronTexture {

    SPACE(new ResourceLocation(ModPrefs.MODID, "textures/models/icosa_space.png")),
    FILL(new ResourceLocation(ModPrefs.MODID, "textures/models/icosa_fill.png"));

    private ResourceLocation texture;

    EnumHedronTexture(ResourceLocation texture){
        this.texture = texture;
    }

    public ResourceLocation getTexture(){
        return texture;
    }

}
