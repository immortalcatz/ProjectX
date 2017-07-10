/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.event;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import keri.projectx.api.energy.IXynergyConnector;
import keri.projectx.init.ProjectXContent;
import keri.projectx.item.ItemXynergyTool;
import keri.projectx.util.ModPrefs;
import keri.projectx.util.Translations;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Point2i;
import javax.vecmath.Vector4d;
import java.util.Collection;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    public static final ClientEventHandler INSTANCE = new ClientEventHandler();
    private int[] ticks = new int[]{0, 0};
    private int[] tooltipTicks = new int[]{0, 0};
    private Object[][] tooltipData = new Object[2][];
    private boolean shouldRenderBoxes = false;
    private int boxTicks = 0;
    private int lastHotbarSlot = 0;

    @SubscribeEvent
    public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event){
        RayTraceResult rtr = Minecraft.getMinecraft().objectMouseOver;
        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = Minecraft.getMinecraft().world;
        int scaledWidth = event.getResolution().getScaledWidth();
        int scaledHeight = event.getResolution().getScaledHeight();
        int resolutionMin = Math.min(scaledWidth, scaledHeight);
        int offsetX = (scaledWidth - resolutionMin) / 2;
        int offsetY = (scaledHeight - resolutionMin) / 2;

        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            this.renderModeTooltip(world, player, new Vector4d(scaledWidth, scaledHeight, offsetX, offsetY));
            this.renderDiagnosticTooltip(world, player, new Vector4d(scaledWidth, scaledHeight, offsetX, offsetY));
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        EntityPlayer player = Minecraft.getMinecraft().player;

        if(player != null){
            InventoryPlayer inventoryPlayer = player.inventory;

            if(this.lastHotbarSlot != inventoryPlayer.currentItem){
                this.resetTooltip(ModPrefs.TOOLTIP_XYNERGY_TOOL);
            }

            this.lastHotbarSlot = inventoryPlayer.currentItem;
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event){
        RayTraceResult rtr = Minecraft.getMinecraft().objectMouseOver;
        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = Minecraft.getMinecraft().world;
        RenderXynergyNet.INSTANCE.handleRender(world, player, event.getPartialTicks());

        if(rtr != null && rtr.typeOfHit == RayTraceResult.Type.BLOCK){
            BlockPos pos = rtr.getBlockPos();
            this.renderDiagnosticBoxes(world, pos, player, event.getPartialTicks());
        }
    }

    private void renderDiagnosticBoxes(World world, BlockPos pos, EntityPlayer player, float partialTicks){
        ItemStack heldItemMainhand = player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack heldItemOffhand = player.getHeldItem(EnumHand.OFF_HAND);
        IBlockState state = world.getBlockState(pos);
        TileEntity tile = (TileEntity)world.getTileEntity(pos);

        if(heldItemMainhand.getItem() == ProjectXContent.XYNERGY_TOOL){
            if(heldItemMainhand.getTagCompound() != null && heldItemMainhand.getTagCompound().hasKey("mode")){
                int currentMode = heldItemMainhand.getTagCompound().getInteger("mode");

                if(currentMode == ItemXynergyTool.MODE_DIAGNOSTIC){
                    this.shouldRenderBoxes = true;
                }
                else{
                    this.shouldRenderBoxes = false;
                }
            }
            else{
                this.shouldRenderBoxes = false;
            }
        }
        else if(heldItemOffhand.getItem() == ProjectXContent.XYNERGY_TOOL){
            if(heldItemOffhand.getTagCompound() != null && heldItemOffhand.getTagCompound().hasKey("mode")){
                int currentMode = heldItemOffhand.getTagCompound().getInteger("mode");

                if(currentMode == ItemXynergyTool.MODE_DIAGNOSTIC){
                    this.shouldRenderBoxes = true;
                }
                else{
                    this.shouldRenderBoxes = false;
                }
            }
            else{
                this.shouldRenderBoxes = false;
            }
        }
        else{
            this.shouldRenderBoxes = false;
        }

        if(this.shouldRenderBoxes){
            if(this.boxTicks > 0){
                this.boxTicks -= 5;
            }
            else{
                this.boxTicks = 255;
            }

            if(state != null){
                AxisAlignedBB aabb = state.getBoundingBox(world, pos);

                if(tile != null && tile instanceof IXynergyConnector){
                    IXynergyConnector connector = (IXynergyConnector)tile;
                    Collection<BlockPos> connections = connector.getConnectedDevices();
                    int alpha = (int)MathHelper.sin((double)net.minecraft.util.math.MathHelper.clamp(this.boxTicks, 1, 40));
                    Colour colorMaster = new ColourRGBA(255, 40, 255, this.boxTicks);
                    Colour colorSlave = new ColourRGBA(40, 255, 255, this.boxTicks);
                    this.renderBox(pos, player, partialTicks, new Cuboid6(aabb), colorMaster);
                    connections.forEach(connection -> {
                        IBlockState deviceState = world.getBlockState(connection);

                        if(deviceState != null){
                            AxisAlignedBB deviceAabb = deviceState.getBoundingBox(world, pos);
                            this.renderBox(pos, player, partialTicks, new Cuboid6(deviceAabb), colorSlave);
                        }
                    });
                }
            }
        }
    }

    private void renderBox(BlockPos pos, EntityPlayer player, float partialTicks, Cuboid6 bounds, Colour color){
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        CCModel model = CCModel.quadModel(24).generateBlock(0, bounds).computeNormals();
        model.setColour(color.rgba());
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        double interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        double posX = pos.getX() - interpPosX;
        double posY = pos.getY() - interpPosY;
        double posZ = pos.getZ() - interpPosZ;
        model.apply(new Translation(new Vector3(posX, posY, posZ)));
        model.render(renderState);
        Tessellator.getInstance().draw();
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private void renderDiagnosticTooltip(World world, EntityPlayer player, Vector4d screenPos){
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        ItemStack heldItemMainhand = player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack heldItemOffhand = player.getHeldItem(EnumHand.OFF_HAND);

        if(this.tooltipData[ModPrefs.TOOLTIP_DIAGNOSTIC] != null && this.tooltipData[ModPrefs.TOOLTIP_DIAGNOSTIC][0] != null){
            List<String> tooltip = (List<String>)this.tooltipData[ModPrefs.TOOLTIP_DIAGNOSTIC][0];

            if(!tooltip.isEmpty()){
                if(!heldItemMainhand.isEmpty() && heldItemOffhand.isEmpty()){
                    this.renderDiagnosticTooltip(heldItemMainhand, new Point2i((int)screenPos.x, (int)screenPos.y), fontRenderer, tooltip);
                }
                else if(heldItemMainhand.isEmpty() && !heldItemOffhand.isEmpty()){
                    this.renderDiagnosticTooltip(heldItemOffhand, new Point2i((int)screenPos.x, (int)screenPos.y), fontRenderer, tooltip);
                }
                else{
                    this.renderDiagnosticTooltip(heldItemMainhand, new Point2i((int)screenPos.x, (int)screenPos.y), fontRenderer, tooltip);
                }
            }
        }
    }

    private void renderDiagnosticTooltip(ItemStack stack, Point2i pos, FontRenderer fontRenderer, List<String> tooltip){
        if(stack != null && !stack.isEmpty()){
            if(stack.getItem() == ProjectXContent.XYNERGY_TOOL){
                if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("mode")){
                    this.updateTooltip(ModPrefs.TOOLTIP_DIAGNOSTIC);

                    if(this.tooltipTicks[ModPrefs.TOOLTIP_DIAGNOSTIC] > 0){
                        for(int i = 0; i < tooltip.size(); i++){
                            String s = tooltip.get(i);
                            int posX = pos.getX() / 2 - fontRenderer.getStringWidth(s) / 2;
                            int posY = pos.getY() / 2 - 10 * tooltip.size() - 10 * i;
                            GlStateManager.pushMatrix();
                            GlStateManager.enableBlend();
                            Colour textColor = new ColourRGBA(255, 255, 255, this.tooltipTicks[ModPrefs.TOOLTIP_DIAGNOSTIC]);
                            fontRenderer.drawStringWithShadow(s, posX, posY, textColor.argb());
                            GlStateManager.disableBlend();
                            GlStateManager.popMatrix();
                        }
                    }
                }
            }
        }
    }

    private void renderModeTooltip(World world, EntityPlayer player, Vector4d screenPos){
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        ItemStack heldItemMainhand = player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack heldItemOffhand = player.getHeldItem(EnumHand.OFF_HAND);

        if(!heldItemMainhand.isEmpty() && heldItemOffhand.isEmpty()){
            this.renderModeTooltip(heldItemMainhand, new Point2i((int)screenPos.x, (int)screenPos.y), fontRenderer);
        }
        else if(heldItemMainhand.isEmpty() && !heldItemOffhand.isEmpty()){
            this.renderModeTooltip(heldItemOffhand, new Point2i((int)screenPos.x, (int)screenPos.y), fontRenderer);
        }
        else{
            this.renderModeTooltip(heldItemMainhand, new Point2i((int)screenPos.x, (int)screenPos.y), fontRenderer);
        }
    }

    private void renderModeTooltip(ItemStack stack, Point2i pos, FontRenderer fontRenderer){
        if(stack != null && !stack.isEmpty()){
            if(stack.getItem() == ProjectXContent.XYNERGY_TOOL){
                if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("mode")){
                    this.updateTooltip(ModPrefs.TOOLTIP_XYNERGY_TOOL);
                    int currentMode = stack.getTagCompound().getInteger("mode");
                    String mode = "";

                    if(currentMode == ItemXynergyTool.MODE_WRENCH){
                        mode = Translations.TOOLTIP_MODE_WRENCH;
                    }
                    else if(currentMode == ItemXynergyTool.MODE_LINK){
                        mode = Translations.TOOLTIP_MODE_LINK;
                    }
                    else if(currentMode == ItemXynergyTool.MODE_DIAGNOSTIC){
                        mode = Translations.TOOLTIP_MODE_DIAGNOSTIC;
                    }
                    else if(currentMode == ItemXynergyTool.MODE_SWAP){
                        mode = Translations.TOOLTIP_MODE_SWAP;
                    }

                    String s = Translations.TOOLTIP_TOOL_MODE + ": " + mode;
                    int posX = (int)pos.getX() / 2 - fontRenderer.getStringWidth(s) / 2;
                    int posY = (int)pos.getY() / 2 + 60;
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    Colour textColor = new ColourRGBA(10, 255, 100, this.tooltipTicks[0]);

                    if(this.tooltipTicks[ModPrefs.TOOLTIP_XYNERGY_TOOL] > 0){
                        fontRenderer.drawStringWithShadow(s, posX, posY, textColor.argb());
                    }

                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    public void resetTooltip(int index, Object... params){
        this.ticks[index] = 50;
        this.tooltipTicks[index] = 255;
        this.tooltipData[index] = params;
    }

    private void updateTooltip(int index){
        if(this.ticks[index] > 0){
            this.ticks[index]--;
        }
        else{
            if(this.tooltipTicks[index] > 0){
                this.tooltipTicks[index] -= 5;
            }
        }
    }

}
