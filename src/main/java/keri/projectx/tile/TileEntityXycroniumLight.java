package keri.projectx.tile;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.lib.tile.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityXycroniumLight extends TileEntityBase {

    private Colour color = new ColourRGBA(255, 255, 255, 255);

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.color = new ColourRGBA(tag.getInteger("color"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("color", this.color.rgba());
        return tag;
    }

    public void setColor(Colour color){
        this.color = color;
    }

    public Colour getColor(){
        return this.color;
    }

}
