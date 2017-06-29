/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client;

import keri.projectx.init.ProjectXContent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ProjectXTab extends CreativeTabs {

    public static final ProjectXTab PROJECTX = new ProjectXTab();

    public ProjectXTab() {
        super("projectx.name");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ProjectXContent.POWER_CORE, 1, 0);
    }

}
