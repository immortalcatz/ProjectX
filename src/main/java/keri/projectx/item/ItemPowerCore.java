/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.projectx.api.energy.EnumCoreType;
import keri.projectx.client.render.item.RenderPowerCore;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPowerCore extends ItemProjectX {

    public ItemPowerCore() {
        super("power_core", EnumCoreType.toStringArray());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumItemRenderType getRenderType() {
        return RenderPowerCore.RENDER_TYPE;
    }

}
