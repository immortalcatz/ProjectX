package keri.projectx.client.gui;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.lib.math.Point2i;
import keri.projectx.util.GuiUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class GuiTab {

    private Point2i position;
    private Point2i size;
    private Point2i sizeExpanded;
    private ResourceLocation icon;
    private List<String> text;
    private Colour colorUnselected;
    private Colour colorSelected;
    private Colour color;
    private boolean isExpanded = false;
    private int hoverTicks = 0;
    private int expandTicksX = 0;
    private int expandTicksY = 0;

    public GuiTab(ResourceLocation icon, List<String> text){
        this.icon = icon;
        this.text = text;
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
        this.color = color;
    }

    public void setColorSelected(Colour color){
        this.colorSelected = color;
    }

    public void renderBackground(GuiScreen gui, int mouseX, int mouseY){
        if(!this.isExpanded){
            if(this.expandTicksX > 0){
                this.expandTicksX -= 14;
            }
            else{
                if(this.expandTicksY > 0){
                    this.expandTicksY -= 16;
                }
            }

            if(this.isInBounds(mouseX, mouseY)){
                if(this.hoverTicks < 20){
                    this.hoverTicks += 2;
                }
            }
            else{
                if(this.hoverTicks > 0){
                    this.hoverTicks -= 4;
                }
            }
        }
        else{
            if(this.hoverTicks > 0){
                this.hoverTicks -= 2;
            }

            if(this.expandTicksX < (this.sizeExpanded.getX() * 4)){
                this.expandTicksX += 12;
            }
            else{
                if(this.expandTicksY < (this.sizeExpanded.getY() * 4)){
                    this.expandTicksY += 14;
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
        GuiUtils.drawBackground(gui, new Point2i(positionX, positionY), new Point2i(sizeX, sizeY), GuiUtils.ALIGNMENT_LEFT, this.color);
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

    private boolean isInBounds(int mouseX, int mouseY){
        if(mouseX >= this.position.getX() && mouseX <= (this.position.getX() + size.getX() - 1)) {
            if (mouseY >= this.position.getY() && mouseY <= (this.position.getY() + size.getY() + 2)) {
                return true;
            }
        }

        return false;
    }

    private Colour blendColors(Colour normal, Colour target, int factor){
        int normalR = normal.r & 0xFF;
        int normalG = normal.g & 0xFF;
        int normalB = normal.b & 0xFF;
        int normalA = normal.a & 0xFF;
        int targetR = target.r & 0xFF;
        int targetG = target.g & 0xFF;
        int targetB = target.b & 0xFF;
        int targetA = target.a & 0xFF;
        boolean[] matrix = new boolean[4];
        Arrays.fill(matrix, false);

        if(normalR < targetR){
            matrix[0] = true;
            normalR += factor;
        }
        else if(normalR > targetR && !matrix[0]){
            normalR -= factor;
        }

        if(normalG < targetG){
            matrix[1] = true;
            normalG += factor;
        }
        else if(normalG > targetG && !matrix[1]){
            normalG -= factor;
        }

        if(normalB < targetB){
            matrix[2] = true;
            normalB += factor;
        }
        else if(normalB > targetB && !matrix[2]){
            normalB -= factor;
        }

        return new ColourRGBA(normalR, normalG, normalB, normalA);
    }

}
