/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.multiblock;

import keri.projectx.init.ProjectXContent;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum MultiShadowTypes {
    ROCK(ProjectXContent.SHADOW_ROCK),
    WOOD(ProjectXContent.SHADOW_WOOD),
    GLASS(ProjectXContent.SHADOW_GLASS),
    GRASS(ProjectXContent.SHADOW_GRASS),
    AIR(ProjectXContent.SHADOW_AIR),
    NONE(ProjectXContent.SHADOW_ROCK);

    private Block block;

    MultiShadowTypes(Block block) {
        this.block = block;
    }

    public void convertToShadowBlock(World world, BlockPos pos, int meta) {
        world.setBlockState(pos, block.getStateFromMeta(meta), 3);
    }
}
