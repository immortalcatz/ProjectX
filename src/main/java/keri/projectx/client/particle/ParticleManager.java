package keri.projectx.client.particle;

import com.google.common.collect.Maps;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class ParticleManager {

    public static final ParticleManager INSTANCE = new ParticleManager();
    private Map<String, ResourceLocation> textureLocations = Maps.newHashMap();
    private Map<String, TextureAtlasSprite> textures = Maps.newHashMap();

    public void preInit(){
        this.registerParticleTexture("sparkle", new ResourceLocation(ModPrefs.MODID, "particle/sparkle"));
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event){
        textureLocations.forEach((k, v) -> textures.put(k, event.getMap().registerSprite(v)));
    }

    public void registerParticleTexture(String name, ResourceLocation location){
        this.textureLocations.put(name, location);
    }

    public TextureAtlasSprite getParticleTexture(String name){
        return this.textures.get(name);
    }

}
