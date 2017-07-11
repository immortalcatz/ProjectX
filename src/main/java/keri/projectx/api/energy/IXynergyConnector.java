/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api.energy;

import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public interface IXynergyConnector {

    boolean canConnectEnergy();

    Collection<BlockPos> getConnectedDevices();

}
