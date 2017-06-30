/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.connected;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface ICTMBlock {

    public TextureHandlerCTM getTextureHandler(IBlockAccess world, BlockPos pos);

}
