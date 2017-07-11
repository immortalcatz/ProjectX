package keri.projectx.machine

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}

@Mod(modid = "projectxmech", name = "ProjectX - Mech", modLanguage = "scala", dependencies = "required-after:projectx")
object ProjectXYMech {
  val MOD_ID = "projectxmech"

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
