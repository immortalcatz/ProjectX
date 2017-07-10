package keri.projectx.machine

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

@Mod(modid = "projectxymech", name = "Project XY - Mech", modLanguage = "scala", dependencies = "required-after:projectxy")
object ProjectXYMech {
  MechBlocks.init()
  val MOD_ID = "projectxymech"

  @EventHandler def init(event: FMLInitializationEvent) {
    ProjectXYMechProxy.init(event)
  }

  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    ProjectXYMechProxy.preInit(event)
  }

  @EventHandler def postInit(event: FMLPostInitializationEvent) {
    ProjectXYMechProxy.postInit(event)
  }
}
