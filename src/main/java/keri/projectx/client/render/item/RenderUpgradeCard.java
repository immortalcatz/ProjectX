package keri.projectx.client.render.item;

import codechicken.lib.colour.Colour;
import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.render.IItemRenderingHandler;
import keri.ninetaillib.lib.render.RenderingRegistry;
import keri.ninetaillib.lib.texture.IIconItem;
import keri.projectx.util.EnumUpgradeType;
import keri.projectx.util.ModelUtils;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderUpgradeCard implements IItemRenderingHandler {

    public static EnumItemRenderType RENDER_TYPE;
    private static CCModel[] ITEM_MODEL;

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(new RenderUpgradeCard());
        Cuboid6[] bounds = new Cuboid6[]{
                new Cuboid6(3D, 2D, 7D, 13D, 12D, 8D),
                new Cuboid6(3D, 4D, 8D, 13D, 12D, 9D),
                new Cuboid6(3D, 2D, 8D, 3.5D, 4D, 9D),
                new Cuboid6(12.5D, 2D, 8D, 13D, 4D, 9D),
                new Cuboid6(4D, 2D, 8D, 5D, 4D, 8.5D),
                new Cuboid6(6D, 2D, 8D, 7D, 4D, 8.5D),
                new Cuboid6(8D, 2D, 8D, 9D, 4D, 8.5D),
                new Cuboid6(10D, 2D, 8D, 12D, 4D, 8.5D),
                new Cuboid6(3D, 12D, 7D, 11D, 14D, 9D),
                new Cuboid6(9.5D, 11.4D, 7D, 12.5D, 13.4D, 8.75D)
        };
        ITEM_MODEL = ModelUtils.getNormalized(bounds);
        ModelUtils.rotate(ITEM_MODEL[9], -45D, new Vector3(0D, 0D, 1D), new Vector3(11D, 12D, 8D));
    }

    @Override
    public void renderItem(ItemStack stack, VertexBuffer buffer) {
        IIconItem iconProvider = (IIconItem)stack.getItem();
        Colour color = EnumUpgradeType.VALUES[stack.getMetadata()].getColor();
    }

    @Override
    public EnumItemRenderType getRenderType() {
        return RENDER_TYPE;
    }

}
