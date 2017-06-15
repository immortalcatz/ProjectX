package keri.projectx.util;

import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.lib.util.VectorUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelUtils {

    //TODO: move this whole class to NTL once it's done

    public static void rotate(CCModel model, double angle, Vector3 axis, Vector3 origin){
        model.apply(new Rotation(angle * MathHelper.torad, axis).at(origin.divide(16D)));
    }

    public static CCModel[] getNormalized(Cuboid6[] bounds){
        CCModel[] parts = new CCModel[bounds.length];

        for(int i = 0; i < parts.length; i++){
            parts[i] = getNormalized(bounds[i]);
        }

        return parts;
    }

    public static CCModel getNormalized(Cuboid6 bounds){
        return CCModel.quadModel(24).generateBlock(0, VectorUtils.divide(bounds, 16D)).computeNormals();
    }

}
