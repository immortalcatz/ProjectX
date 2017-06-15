package keri.projectx.util;

import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.lib.util.VectorUtils;

public class ModelUtils {

    public static void rotate(CCModel model, double angle, Vector3 axis, Vector3 origin){
        model.apply(new Rotation(angle * MathHelper.torad, axis).at(origin));
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
