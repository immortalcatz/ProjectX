package keri.projectx.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderData {

    private IBlockState state;
    private ItemStack stack;
    private int renderPass;
    private int modelPart;
    private int lastBrightness;
    private boolean isInitialized;

    public RenderData(IBlockState state, ItemStack stack, int renderPass, int modelPart, int lastBrightness, boolean isInitialized){
        this.state = state;
        this.stack = stack;
        this.renderPass = renderPass;
        this.modelPart = modelPart;
        this.lastBrightness = lastBrightness;
        this.isInitialized = isInitialized;
    }

    public IBlockState getState(){
        return this.state;
    }

    public ItemStack getStack(){
        return this.stack;
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
