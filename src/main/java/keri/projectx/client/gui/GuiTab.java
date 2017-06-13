package keri.projectx.client.gui;

import codechicken.lib.colour.Colour;
import codechicken.lib.texture.TextureUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.math.Point2i;
import keri.projectx.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.List;

public class GuiTab {

    //TODO Implement proper GuiTabList behaviour you twat!!!

    private Point2i guiSize;
    private Point2i position;
    private Point2i size;
    private TextureAtlasSprite icon;
    private String tooltip;
    private List<String> text;
    private Colour colorUnselected;
    private Colour colorSelected;
    private boolean isExpanded = false;
    private boolean isFullyExpanded = false;
    private boolean isVisible = true;
    private int hoverTicks = 0;
    private int expandTicksX = 0;
    private int expandTicksY = 0;

    public GuiTab(Point2i guiSize){
        this.guiSize = guiSize;
    }

    public void renderBackground(GuiScreen gui, int mouseX, int mouseY){
        if(!this.isExpanded){
            this.isFullyExpanded = false;

            if(this.expandTicksX > 0){
                this.expandTicksX -= 16;
            }
            else{
                if(this.expandTicksY > 0){
                    this.expandTicksY -= 16;
                }
                else{
                    if(this.isInBounds(gui, mouseX, mouseY)){
                        if(this.hoverTicks < 24){
                            this.hoverTicks += 4;
                        }
                    }
                    else{
                        if(this.hoverTicks > 0){
                            this.hoverTicks -= 6;
                        }
                    }
                }
            }
        }
        else{
            if(this.hoverTicks > 0){
                this.hoverTicks -= 6;
            }
            else{
                if(this.expandTicksX < (this.size.getX() * 4)){
                    this.expandTicksX += 16;
                }
                else{
                    if(this.expandTicksY < (this.size.getY() * 4)){
                        this.expandTicksY += 16;
                    }
                    else{
                        this.isFullyExpanded = true;
                    }
                }
            }
        }

        int basePosX = (gui.width - this.guiSize.getX()) / 2;
        int basePosY = (gui.height - this.guiSize.getY()) / 2;
        int hoverOffset = this.hoverTicks / 4;
        int expandOffsetX = this.expandTicksX / 4;
        int expandOffsetY = this.expandTicksY / 4;
        int positionX = (basePosX + (this.position.getX() - hoverOffset)) - expandOffsetX;
        int positionY = basePosY + this.position.getY();
        int sizeX = (22 + hoverOffset) + expandOffsetX;
        int sizeY = 20 + expandOffsetY;
        Colour color = this.isExpanded || this.isInBounds(gui, mouseX, mouseY) ? this.colorSelected : this.colorUnselected;

        if(this.isVisible){
            GlStateManager.color(1F, 1F, 1F, 1F);
            GuiUtils.drawBackground(gui, new Point2i(positionX, positionY), new Point2i(sizeX, sizeY), GuiUtils.ALIGNMENT_LEFT, color);
            GlStateManager.color(1F, 1F, 1F, 1F);

            if(this.icon != null){
                TextureUtils.bindBlockTexture();
                gui.drawTexturedModalRect(positionX + 4, positionY + 4, this.icon, 16, 16);
            }
        }
    }

    public void renderForeground(GuiScreen gui, int mouseX, int mouseY){
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        if(this.isFullyExpanded){
            GlStateManager.pushMatrix();

            if(this.text != null){
                int iconOffset = this.icon != null ? 24 : 5;
                int positionX = this.position.getX() - this.size.getX() + 6;
                int positionY = this.position.getY() + iconOffset;

                for(int i = 0; i < this.text.size(); i++){
                    String line = this.text.get(i);
                    int lineOffset = 8 * i;
                    GlStateManager.translate(positionX, positionY + lineOffset, 0D);
                    GlStateManager.scale(0.75D, 0.75D, 0.75D);
                    fontRenderer.drawString(line, 0, 0, 0xFFFFFFFF, false);
                    GlStateManager.scale(1D / 0.75D, 1D / 0.75D, 1D / 0.75D);
                    GlStateManager.translate(-positionX, -(positionY + lineOffset), 0D);
                }
            }

            GlStateManager.popMatrix();
        }
    }

    public boolean onMouseClicked(GuiScreen gui, int mouseX, int mouseY){
        if(this.isInBounds(gui, mouseX, mouseY) && this.isVisible){
            if(!this.isExpanded){
                this.isExpanded = true;
            }
            else{
                this.isExpanded = false;
            }

            return true;
        }

        return false;
    }

    private boolean isInBounds(GuiScreen gui, int mouseX, int mouseY){
        int positionX = (gui.width - this.guiSize.getX()) / 2 + this.position.getX();
        int positionY = (gui.height - this.guiSize.getY()) / 2 + this.position.getY();

        if(mouseX >= positionX && mouseX <= (positionX + 22)) {
            if (mouseY >= positionY && mouseY <= (positionY + 22)) {
                return true;
            }
        }

        return false;
    }

    public void setPosition(Point2i position){
        this.position = position;
    }

    public void setSize(Point2i size){
        this.size = size;
    }

    public void setColorUnselected(Colour color){
        this.colorUnselected = color;
    }

    public void setColorSelected(Colour color){
        this.colorSelected = color;
    }

    public boolean isExpanded(){
        return this.isExpanded;
    }

    public void setText(List<String> text){
        this.text = text;
    }

    public void setIcon(TextureAtlasSprite icon){
        this.icon = icon;
    }

    public void setIsVisible(boolean isVisible){
        this.isVisible = isVisible;
    }

    public void setTooltip(String tooltip){
        this.tooltip = tooltip;
    }

    public boolean getIsExpanded(){
        return this.isExpanded;
    }

    public boolean getIsFullyExpanded(){
        return this.isFullyExpanded;
    }

    public static class GuiTabList {

        private List<GuiTab> tabs = Lists.newArrayList();
        private int activeTab = -1;

        public void addTab(GuiTab tab){
            this.tabs.add(tab);
        }

        public void renderBackground(GuiScreen gui, int mouseX, int mouseY){
            for(int i = 0; i < this.tabs.size(); i++){
                GuiTab tab = this.tabs.get(i);

                if(tab.getIsExpanded()){
                    this.activeTab = i;
                    break;
                }
                else{
                    this.activeTab = -1;
                    continue;
                }
            }

            for(int i = 0; i < this.tabs.size(); i++){
                GuiTab tab = this.tabs.get(i);
                tab.renderBackground(gui, mouseX, mouseY);
            }
        }

        public void renderForeground(GuiScreen gui, int mouseX, int mouseY){
            for(int i = 0; i < this.tabs.size(); i++){
                this.tabs.get(i).renderForeground(gui, mouseX, mouseY);
            }
        }

        public void onMouseClicked(GuiScreen gui, int mouseX, int mouseY){
            for(int i = 0; i < this.tabs.size(); i++){
                this.tabs.get(i).onMouseClicked(gui, mouseX, mouseY);
            }
        }

    }

}
