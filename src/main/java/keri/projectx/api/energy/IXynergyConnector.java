/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api.energy;

import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public interface IXynergyConnector {

    boolean canConnectEnergy();

    void addConnection(BlockPos pos);

    void removeConnection(BlockPos pos);

    Collection<BlockPos> getConnectedDevices();

}
