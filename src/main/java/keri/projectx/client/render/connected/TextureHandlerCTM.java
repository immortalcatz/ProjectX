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
public class TextureHandlerCTM {

    private String texturePath;
    private TextureAtlasSprite[] textures;

    public TextureHandlerCTM(String texturePath){
        this.texturePath = texturePath;
        this.textures = new TextureAtlasSprite[4];
    }

    public void registerIcons(IIconRegister register){
        this.textures[0] = register.registerIcon(this.texturePath);
        this.textures[1] = register.registerIcon(this.texturePath + "_corners");
        this.textures[2] = register.registerIcon(this.texturePath + "_vertical");
        this.textures[3] = register.registerIcon(this.texturePath + "_horizontal");
    }

    public TextureAtlasSprite[] getIcons(){
        return this.textures;
    }

    public TextureAtlasSprite getIconNormal(){
        return this.textures[0];
    }

    public TextureAtlasSprite getIconCorners(){
        return this.textures[1];
    }

    public TextureAtlasSprite getIconVertical(){
        return this.textures[2];
    }

    public TextureAtlasSprite getIconHorizontal(){
        return this.textures[3];
    }

    public String getTexturePath(){
        return this.texturePath;
    }

}
