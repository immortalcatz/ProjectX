package keri.projectx.client.gui;

import codechicken.lib.colour.Colour;
import codechicken.lib.texture.TextureUtils;
import keri.ninetaillib.lib.math.Point2i;
import keri.projectx.util.GuiUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.List;

public class GuiTab {

    private Point2i position;
    private Point2i size;
    private Point2i sizeExpanded;
    private TextureAtlasSprite icon;
    private List<String> text;
    private Colour colorUnselected;
    private Colour colorSelected;
    private boolean isExpanded = false;
    private int hoverTicks = 0;
    private int expandTicksX = 0;
    private int expandTicksY = 0;

    public void renderBackground(GuiScreen gui, int mouseX, int mouseY){
        if(!this.isExpanded){
            if(this.expandTicksX > 0){
                if(this.expandTicksX < 14){
                    this.expandTicksX--;
                }
                else{
                    this.expandTicksX -= 14;
                }
            }
            else{
                if(this.expandTicksY > 0){
                    if(this.expandTicksY < 16){
                        this.expandTicksY--;
                    }
                    else{
                        this.expandTicksY -= 16;
                    }
                }
                else{
                    if(this.isInBounds(mouseX, mouseY)){
                        if(this.hoverTicks < 20){
                            this.hoverTicks += 4;
                        }
                    }
                    else{
                        if(this.hoverTicks > 0){
                            if(this.hoverTicks < 6){
                                this.hoverTicks--;
                            }
                            else{
                                this.hoverTicks -= 6;
                            }
                        }
                    }
                }
            }
        }
        else{
            if(this.hoverTicks > 0){
                if(this.hoverTicks < 6){
                    this.hoverTicks--;
                }
                else{
                    this.hoverTicks -= 6;
                }
            }
            else{
                if(this.expandTicksX < (this.sizeExpanded.getX() * 4)){
                    this.expandTicksX += 16;
                }
                else{
                    if(this.expandTicksY < (this.sizeExpanded.getY() * 4)){
                        this.expandTicksY += 16;
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

        if(this.icon != null){
            TextureUtils.bindBlockTexture();
            gui.drawTexturedModalRect(positionX + 4, positionY + 4, this.icon, 16, 16);
        }
    }

    public void renderForeground(GuiScreen gui, int mouseX, int mouseY){

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

    public void setPosition(Point2i position){
        this.position = position;
    }

    public void setSize(Point2i size){
        this.size = size;
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

    private boolean isInBounds(int mouseX, int mouseY){
        if(mouseX >= this.position.getX() && mouseX <= (this.position.getX() + size.getX() - 1)) {
            if (mouseY >= this.position.getY() && mouseY <= (this.position.getY() + size.getY() + 2)) {
                return true;
            }
        }

        return false;
    }

}
