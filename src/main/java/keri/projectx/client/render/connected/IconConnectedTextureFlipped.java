/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.connected;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IconConnectedTextureFlipped extends IconConnectedTexture {

    public IconConnectedTextureFlipped(String textureName) {
        super(textureName);
    }

    @Override
    public float getMinV() {
        return super.getMaxV();
    }

    @Override
    public float getMaxV() {
        return super.getMinV();
    }

}
