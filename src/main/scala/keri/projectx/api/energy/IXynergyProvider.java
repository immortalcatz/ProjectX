/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.api.energy;

public interface IXynergyProvider {

    void extractEnergy(int amount);

    int getEnergyStored();

    int getMaxEnergyStored();

}
