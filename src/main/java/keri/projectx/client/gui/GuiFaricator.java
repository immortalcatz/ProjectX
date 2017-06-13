package keri.projectx.client.gui;

import codechicken.lib.texture.TextureUtils;
import keri.projectx.ProjectX;
import keri.projectx.container.ContainerFabricator;
import keri.projectx.tile.TileEntityFabricator;
import keri.projectx.util.EnumXycroniumColor;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFaricator extends GuiContainer {

    private final ResourceLocation texture = new ResourceLocation(ModPrefs.MODID, "textures/gui/fabricator.png");
    private InventoryPlayer inventoryPlayer;
    private TileEntityFabricator tile;
    private ContainerFabricator container;

    public GuiFaricator(InventoryPlayer inventoryPlayer, TileEntityFabricator tile) {
        super(new ContainerFabricator(inventoryPlayer, tile));
        this.inventoryPlayer = inventoryPlayer;
        this.tile = tile;
        this.container = (ContainerFabricator)this.inventorySlots;
        super.xSize = 192;
        super.ySize = 166;
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
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 92, 0xFF101010);
        this.fontRendererObj.drawString("Fabricator", 8, this.ySize - 162, 0xFF101010);
    }

}
