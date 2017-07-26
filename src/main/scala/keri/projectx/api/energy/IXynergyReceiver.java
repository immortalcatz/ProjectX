/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.api.energy;

public interface IXynergyReceiver {

    void receiveEnergy(int amount);

    int getEnergyStored();

    int getMaxEnergyStored();

}
