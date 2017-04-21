package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.lib.vec.uv.UVTransformation;
import keri.ninetaillib.util.CommonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ModelRenderer {

    private Map<String, List<ModelPart>> modelParts;

    //TODO right fucking here!

    @SideOnly(Side.CLIENT)
    private class ModelPart {

        private CCRenderState renderState;
        private CCModel part;
        private RenderAttributes[] renderAttributes;

        public ModelPart(CCRenderState renderState, Cuboid6 bounds){
            this.renderState = renderState;
            this.part = CCModel.quadModel(24).generateBlock(0, CommonUtils.devide(bounds, 16D)).computeNormals();
            Arrays.fill(this.renderAttributes, new RenderAttributes());
        }

        public ModelPart setTexture(TextureAtlasSprite texture){
            for(int i = 0; i < 6; i++){
                this.renderAttributes[i].uvOverride = true;
                this.renderAttributes[i].uv = new IconTransformation(texture);
            }

            return this;
        }

        public ModelPart setTexture(TextureAtlasSprite... textures){
            if(textures != null){
                if(textures.length != 6){
                    String message = String.format("Cannot set texture with array size %d!", textures.length);
                    throw new IllegalArgumentException(message);
                }
            }

            for(int i = 0; i < 6; i++){
                this.renderAttributes[i].uvOverride = true;
                this.renderAttributes[i].uv = new IconTransformation(textures[i]);
            }

            return this;
        }

        public ModelPart setBrightness(int brightness){
            for(int i = 0; i < 6; i++){
                this.renderAttributes[i].brightnessOverride = true;
                this.renderAttributes[i].brightness = brightness;
            }

            return this;
        }

        public ModelPart setBrightness(int... brightness){
            if(brightness != null){
                if(brightness.length != 6){
                    String message = String.format("Cannot set brightness with array size %d!", brightness.length);
                    throw new IllegalArgumentException(message);
                }
            }

            for(int i = 0; i < 6; i++){
                this.renderAttributes[i].brightnessOverride = true;
                this.renderAttributes[i].brightness = brightness[i];
            }

            return this;
        }

        public ModelPart setColor(Colour color){
            for(int i = 0; i < 6; i++){
                this.renderAttributes[i].colorOverride = true;
                this.renderAttributes[i].color = color.rgba();
            }

            return this;
        }

        public ModelPart setColor(Colour... colors){
            if(colors != null){
                if(colors.length != 6){
                    String message = String.format("Cannot set color with array size %d!", colors.length);
                    throw new IllegalArgumentException(message);
                }
            }

            for(int i = 0; i < 6; i++){
                this.renderAttributes[i].colorOverride = true;
                this.renderAttributes[i].color = colors[i].rgba();
            }

            return this;
        }

        public void render(){
            for(int side = 0; side < 6; side++){
                int renderFrom = side * 4;
                int renderTo = 4 + (side * 4);
                UVTransformation uv;
                int color;

                if(this.renderAttributes[side].uvOverride){
                    uv = this.renderAttributes[side].uv;
                }
                else{
                    uv = new IconTransformation(Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite());
                }

                if(this.renderAttributes[side].colorOverride){
                    color = this.renderAttributes[side].color;
                }
                else{
                    color = 0xFFFFFFFF;
                }

                if(this.renderAttributes[side].brightnessOverride){
                    this.renderState.brightness = this.renderAttributes[side].brightness;
                }

                this.part.setColour(color);
                this.part.render(this.renderState, renderFrom, renderTo, uv);
            }
        }

    }

    @SideOnly(Side.CLIENT)
    private class RenderAttributes {

        public boolean uvOverride;
        public UVTransformation uv;
        public boolean brightnessOverride;
        public int brightness;
        public boolean colorOverride;
        public int color;

        public RenderAttributes(){
            this.uvOverride = false;
            this.brightnessOverride = false;
            this.colorOverride = false;
        }

    }

}
