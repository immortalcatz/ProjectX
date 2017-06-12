package keri.projectx.client.gui;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.texture.TextureUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.math.Point2i;
import keri.projectx.ProjectX;
import keri.projectx.client.IconHandler;
import keri.projectx.container.ContainerFabricator;
import keri.projectx.tile.TileEntityFabricator;
import keri.projectx.util.EnumXycroniumColor;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiFaricator extends GuiContainer {

    private final ResourceLocation texture = new ResourceLocation(ModPrefs.MODID, "textures/gui/fabricator.png");
    private InventoryPlayer inventoryPlayer;
    private TileEntityFabricator tile;
    private ContainerFabricator container;
    private GuiTab tabInfo = new GuiTab(new Point2i(192, 166));

    public GuiFaricator(InventoryPlayer inventoryPlayer, TileEntityFabricator tile) {
        super(new ContainerFabricator(inventoryPlayer, tile));
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
        this.container = (ContainerFabricator)this.inventorySlots;
        super.xSize = 192;
        super.ySize = 166;
        this.initTabs();
    }

    private void initTabs(){
        this.tabInfo.setPosition(new Point2i(-22, 10));
        this.tabInfo.setSize(new Point2i(86, 76));
        this.tabInfo.setColorUnselected(new ColourRGBA(80, 80, 80, 255));
        this.tabInfo.setColorSelected(new ColourRGBA(220, 220, 80, 255));
        this.tabInfo.setIcon(IconHandler.INSTANCE.getIcon("gui_icon_info"));
        this.tabInfo.setTooltip("Information");
        this.tabInfo.setText(Lists.newArrayList(
                TextFormatting.BLUE + "The recipe to craft",
                TextFormatting.DARK_GREEN + "The crafting result",
                TextFormatting.DARK_RED + "The internal inventory",
                TextFormatting.BLACK + "If the Fabricator has",
                TextFormatting.BLACK + "all the required ingre-",
                TextFormatting.BLACK + "dients it needs, it will",
                TextFormatting.BLACK + "start crafting."
        ));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        TextureUtils.bindBlockTexture();
        TextureAtlasSprite animationTexture = ProjectX.PROXY.getAnimatedTexture();
        EnumXycroniumColor.BLUE.getColor().glColour();
        this.drawTexturedModalRect(this.guiLeft + 13, this.guiTop + 14, animationTexture, 58, 58);
        EnumXycroniumColor.GREEN.getColor().glColour();
        this.drawTexturedModalRect(this.guiLeft + 81, this.guiTop + 28, animationTexture, 30, 30);
        EnumXycroniumColor.RED.getColor().glColour();
        this.drawTexturedModalRect(this.guiLeft + 121, this.guiTop + 14, animationTexture, 58, 58);
        GlStateManager.color(1F, 1F, 1F, 1F);
        this.mc.getTextureManager().bindTexture(this.texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.tabInfo.renderBackground(this, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.tabInfo.renderForeground(this, mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.tabInfo.onMouseClicked(this, mouseX, mouseY);
    }

}
