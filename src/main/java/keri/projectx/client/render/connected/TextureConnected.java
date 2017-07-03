/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.render.connected;

import keri.ninetaillib.lib.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureConnected extends TextureAtlasSprite {

    //TODO: get rid of this class so we can use normal TAS's for the textures
    //TODO: move anything ctm related to NineTailLib once cleaned up

    private String textureName;
    public final TextureAtlasSprite[] icons = new TextureAtlasSprite[5];
    private int type;

    public TextureConnected(String textureName) {
        super(textureName);
        this.textureName = textureName;
    }

    public TextureConnected register(IIconRegister register){
        this.icons[0] = register.registerIcon(this.textureName + "_c");
        this.icons[1] = register.registerIcon(this.textureName + "_v");
        this.icons[2] = register.registerIcon(this.textureName + "_h");
        this.icons[3] = register.registerIcon(this.textureName);
        this.icons[4] = register.registerIcon(this.textureName + "_a");
        return this;
    }

    public void setType(int type){
        this.type = type;
    }

    public void resetType(){
        this.type = 0;
    }

    @Override
    public float getMinU() {
        return this.icons[this.type].getMinU();
    }

    @Override
    public float getMaxU() {
        return this.icons[this.type].getMaxU();
    }

    @Override
    public float getInterpolatedU(double u) {
        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * ((float)u / 16F);
    }

    @Override
    public float getMinV() {
        return this.icons[this.type].getMinV();
    }

    @Override
    public float getMaxV() {
        return this.icons[this.type].getMaxV();
    }

    @Override
    public float getInterpolatedV(double v) {
        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * ((float)v / 16F);
    }

    @Override
    public String getIconName() {
        return this.icons[this.type].getIconName();
    }

    @Override
    public int getIconWidth() {
        return this.icons[this.type].getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return this.icons[this.type].getIconHeight();
    }

}
