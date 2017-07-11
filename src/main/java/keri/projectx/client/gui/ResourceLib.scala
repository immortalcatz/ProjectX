package keri.projectx.client.gui

import keri.projectx.util.ModPrefs
import net.minecraft.util.ResourceLocation

/**
  * Created by Adam on 7/10/2017.
  */
object ResourceLib {
  val baseGui = register("textures/gui/generic_processor.png")

  def register(path: String) = new ResourceAction(new ResourceLocation(ModPrefs.MODID, path))
}
