/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.gui

import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation

class ResourceAction(loc: ResourceLocation) {
  def bind() {
    mc.renderEngine.bindTexture(loc)
  }

  def mc = Minecraft.getMinecraft
}