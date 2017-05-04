package keri.projectx.client.render;

import keri.ninetaillib.texture.IIconBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderData {

    private IBlockState state;
    private ItemStack stack;
    private IIconBlock iconProvider;
    private IAnimationSideHandler animationHandler;
    private TextureAtlasSprite particleTexture;
    private int renderPass;
    private int modelPart;
    private int lastBrightness;
    private boolean isInitialized;

    public RenderData(IBlockState state, ItemStack stack, IIconBlock iconProvider, IAnimationSideHandler animationHandler, int renderPass, int modelPart, int lastBrightness, boolean isInitialized){
        this.state = state;
        this.stack = stack;
        this.iconProvider = iconProvider;
        this.animationHandler = animationHandler;
        this.particleTexture = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
        this.renderPass = renderPass;
        this.modelPart = modelPart;
        this.lastBrightness = lastBrightness;
        this.isInitialized = isInitialized;
    }

    public void setParticleTexture(TextureAtlasSprite texture){
        this.particleTexture = particleTexture;
    }

    public IBlockState getState(){
        return this.state;
    }

    public ItemStack getStack(){
        return this.stack;
    }

    public IIconBlock getIconProvider(){
        return this.iconProvider;
    }

    public IAnimationSideHandler getAnimationHandler(){
        return this.animationHandler;
    }

    public int getRenderPass(){
        return this.renderPass;
    }

    public int getModelPart(){
        return this.modelPart;
    }

    public int getLastBrightness(){
        return this.lastBrightness;
    }

    public boolean getIsInitialized(){
        return this.isInitialized;
    }

}
