/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.item;

import keri.ninetaillib.lib.item.ItemBase;
import keri.projectx.client.ProjectXTab;
import net.minecraft.creativetab.CreativeTabs;

public class ItemProjectX extends ItemBase {

    public ItemProjectX(String itemName) {
        super(itemName);
    }

    public ItemProjectX(String itemName, String... subNames) {
        super(itemName, subNames);
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return ProjectXTab.PROJECTX;
    }

}
