/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client;

import com.google.common.collect.Maps;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class IconHandler {

    public static final IconHandler INSTANCE = new IconHandler();
    private Map<String, ResourceLocation> textureLocations = Maps.newHashMap();
    private Map<String, TextureAtlasSprite> textures = Maps.newHashMap();

    public void preInit(){
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        this.registerIcon("particle_sparkle", new ResourceLocation(ModPrefs.MODID, "particle/sparkle"));
        this.registerIcon("xynergy_beam", new ResourceLocation(ModPrefs.MODID, "particle/beam"));
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event){
        textureLocations.forEach((k, v) -> textures.put(k, event.getMap().registerSprite(v)));
    }

    public void registerIcon(String name, ResourceLocation location){
        this.textureLocations.put(name, location);
    }

    public TextureAtlasSprite getIcon(String name){
        return this.textures.get(name);
    }

}
