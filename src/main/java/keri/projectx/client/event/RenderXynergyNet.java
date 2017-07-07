/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.event;

import com.google.common.collect.Lists;
import keri.projectx.api.energy.IXynergyConnector;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderXynergyNet {

    public static final RenderXynergyNet INSTANCE = new RenderXynergyNet();
    private static List<BlockPos> DEVICES = Lists.newArrayList();

    public void handleRender(World world, EntityPlayer player, float partialTicks){
        for(BlockPos devicePos : DEVICES){
            TileEntity tile = world.getTileEntity(devicePos);

            if(tile != null && tile instanceof IXynergyConnector){
                IXynergyConnector connector = (IXynergyConnector)tile;

                for(BlockPos connectedDevicePos : connector.getConnectedDevices()){
                    this.handleRender(world, player, devicePos, connectedDevicePos, partialTicks);
                }
            }
        }
    }

    private void handleRender(World world, EntityPlayer player, BlockPos startPos, BlockPos targetPos, float partialTicks){
        GlStateManager.pushMatrix();
        GlStateManager.glTexParameterf(3553, 10242, 10497F);
        GlStateManager.glTexParameterf(3553, 10243, 10497F);
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

        //render shit here! :D

        Tessellator.getInstance().draw();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

}
