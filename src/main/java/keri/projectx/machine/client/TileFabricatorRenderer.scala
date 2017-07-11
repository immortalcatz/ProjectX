//package keri.projectx.machine.client
//
//import keri.projectx.machine.tile.TileFabricator
//import net.minecraft.client.Minecraft
//import net.minecraft.client.renderer.OpenGlHelper
//import net.minecraft.client.renderer.entity.Render
//import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
//import net.minecraft.entity.item.EntityItem
//import org.lwjgl.opengl.GL11
//
//class TileFabricatorRenderer extends TileEntitySpecialRenderer[TileFabricator] {
//  override def render(tile: TileFabricator, x: Double, y: Double, z: Double, frameTime: Float, destroyStage: Int, alpha: Float): Unit = {
//    if (tile.crafting_render_time > 0) {
//      val renderer = Minecraft.getMinecraft.getRenderManager.getEntityClassRenderObject(classOf[EntityItem]).asInstanceOf[Render[EntityItem]]
//      val entityItem = new EntityItem(tile.getWorld)
//      //Hover should be set to 0 so it doesn't spin or start at a random angle
//      entityItem.hoverStart = 0.0f
//
//
//      GL11.glPushMatrix()
//      //Shift to top middle and up 1
//      GL11.glTranslated(x + .5, y + 1, z + .5)
//      for (i <- 0 until 9) {
//        val stack = tile.getCrafted().getStackInSlot(i)
//        if (stack != null) {
//          //Row should be divisable by 3
//          val row = i / 3
//          //Column is index divided by three remainder
//          val col = i % 3
//
//          entityItem.setItem(stack)
//          val spacing = .1875D
//
//          val translateX = -spacing + col * spacing
//          val translateZ = -spacing + row * spacing
//          GL11.glTranslated(translateX, 0, translateZ)
//          GL11.glScaled(.50, .5, .5)
//          val var5 = 0xF000F0
//          val var6 = var5 % 65536
//          val var7 = var5 / 65536
//          OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F)
//          renderer.doRender(entityItem, 0, 0, 0, 0, 0)
//          GL11.glScaled(1 / .5, 1 / .5, 1 / .5)
//          GL11.glTranslated(-translateX, 0, -translateZ)
//        }
//      }
//      GL11.glPopMatrix()
//    }
//  }
//}
