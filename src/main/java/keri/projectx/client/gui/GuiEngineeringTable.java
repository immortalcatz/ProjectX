package keri.projectx.client.gui;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.texture.TextureUtils;
import keri.ninetaillib.util.ResourceAction;
import keri.projectx.ProjectX;
import keri.projectx.container.ContainerEngineeringTable;
import keri.projectx.property.EnumXycroniumColor;
import keri.projectx.tile.TileEntityEngineeringTable;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEngineeringTable extends GuiContainer {

    private final ResourceAction texture = new ResourceAction(ModPrefs.MODID, "textures/gui/engineering_table.png");
    private InventoryPlayer inventoryPlayer;
    private TileEntityEngineeringTable tile;
    private ContainerEngineeringTable container;

    public GuiEngineeringTable(InventoryPlayer inventoryPlayer, TileEntityEngineeringTable tile) {
        super(new ContainerEngineeringTable(inventoryPlayer, tile));
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
        this.container = (ContainerEngineeringTable)this.inventorySlots;
        this.xSize = 176;
        this.ySize = 185;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        TextureUtils.bindBlockTexture();
        TextureAtlasSprite animation = ProjectX.PROXY.getAnimationIcon();
        EnumXycroniumColor.BLUE.getColor().glColour();
        this.drawTexturedModalRect(this.guiLeft + 3, this.guiTop + 3, animation, this.xSize - 4, this.ySize - 4);
        new ColourRGBA(255, 255, 255, 255).glColour();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.texture.bind(true);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = "Engineering Table";
        int posX = (this.xSize / 2) - (this.fontRendererObj.getStringWidth(name) / 2);
        int posY = 6;
        Colour textColor = new ColourRGBA(40, 40, 40, 255);
        this.fontRendererObj.drawString(name, posX, posY, textColor.argb());
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, textColor.argb());
    }

}
