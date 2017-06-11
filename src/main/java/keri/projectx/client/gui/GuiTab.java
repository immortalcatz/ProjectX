package keri.projectx.client.gui;

import codechicken.lib.colour.Colour;
import codechicken.lib.texture.TextureUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.math.Point2i;
import keri.projectx.util.GuiUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.List;

public class GuiTab {

    private Point2i position;
    private Point2i size = new Point2i(22, 20);
    private Point2i sizeExpanded;
    private TextureAtlasSprite icon;
    private String tooltip;
    private List<String> text;
    private Colour colorUnselected;
    private Colour colorSelected;
    private boolean isExpanded = false;
    private boolean isFullyExpanded = false;
    private int hoverTicks = 0;
    private int expandTicksX = 0;
    private int expandTicksY = 0;

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
                    if(this.isInBounds(mouseX, mouseY)){
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
                if(this.expandTicksX < (this.sizeExpanded.getX() * 4)){
                    this.expandTicksX += 16;
                }
                else{
                    if(this.expandTicksY < (this.sizeExpanded.getY() * 4)){
                        this.expandTicksY += 16;
                    }
                    else{
                        this.isFullyExpanded = true;
                    }
                }
            }
        }

        int hoverOffset = this.hoverTicks / 4;
        int expandOffsetX = this.expandTicksX / 4;
        int expandOffsetY = this.expandTicksY / 4;
        int positionX = (this.position.getX() - hoverOffset) - expandOffsetX;
        int positionY = this.position.getY();
        int sizeX = (this.size.getX() + hoverOffset) + expandOffsetX;
        int sizeY = this.size.getY() + expandOffsetY;
        Colour color = this.isExpanded || this.isInBounds(mouseX, mouseY) ? this.colorSelected : this.colorUnselected;
        GuiUtils.drawBackground(gui, new Point2i(positionX, positionY), new Point2i(sizeX, sizeY), GuiUtils.ALIGNMENT_LEFT, color);
        GlStateManager.color(1F, 1F, 1F, 1F);

        if(this.icon != null){
            TextureUtils.bindBlockTexture();
            gui.drawTexturedModalRect(positionX + 4, positionY + 4, this.icon, 16, 16);
        }
    }

    public void renderForeground(GuiScreen gui, int mouseX, int mouseY){
        FontRenderer fontRenderer = gui.mc.fontRendererObj;

        if(!this.isExpanded){
            if(this.isInBounds(mouseX, mouseY)){
                int positionX = mouseX - (this.position.getX() - this.size.getX());
                int positionY = mouseY - (this.position.getY() - this.size.getY());

                if(this.tooltip != null){
                    List<String> text = Lists.newArrayList(this.tooltip);
                    net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(text, positionX - 48, positionY + 8, gui.width, gui.height, 200, fontRenderer);
                }
            }
        }
    }

    public boolean onMouseClicked(int mouseX, int mouseY){
        if(this.isInBounds(mouseX, mouseY)){
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

    private boolean isInBounds(int mouseX, int mouseY){
        if(mouseX >= this.position.getX() && mouseX <= (this.position.getX() + size.getX() - 1)) {
            if (mouseY >= this.position.getY() && mouseY <= (this.position.getY() + size.getY() + 2)) {
                return true;
            }
        }

        return false;
    }

    public void setPosition(Point2i position){
        this.position = position;
    }

    public void setSizeExpanded(Point2i sizeExpanded){
        this.sizeExpanded = sizeExpanded;
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

    public void setTooltip(String tooltip){
        this.tooltip = tooltip;
    }

    public boolean getIsFullyExpanded(){
        return this.isFullyExpanded;
    }

}
