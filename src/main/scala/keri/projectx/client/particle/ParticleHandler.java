/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.client.particle;

import codechicken.lib.colour.Colour;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleHandler {

    public static void handleQuartzCrystal(World world, BlockPos pos, Colour color){
        int puffs = 2;
        int crystalWidth = 3;
        double crystalBorder = 0.4D;
        int meta = BlockAccessUtils.getBlockMetadata(world, pos);

        if(Minecraft.getMinecraft().gameSettings.particleSetting < 2){
            for(int i = 0; i < puffs; i++){
                ParticleSparkle effect = null;
                double width = crystalBorder + (double)world.rand.nextInt(crystalWidth) / 10D;
                double length = crystalBorder + (double)world.rand.nextInt(crystalWidth) / 10D;
                double height = (double)world.rand.nextInt(7) / 10D + 0.2D;
                double x = pos.getX();
                double y = pos.getY();
                double z = pos.getZ();
                float r = color.r / 255F;
                float g = color.g / 255F;
                float b = color.b / 255F;

                switch(meta){
                    case 0:
                        effect = new ParticleSparkle(world, x + length, y + height, z + width, 0.3F, r, g, b, 3 + world.rand.nextInt(1));
                        break;
                    case 1:
                        effect = new ParticleSparkle(world, x + length, y + height, z + width, 0.3F, r, g, b, 3 + world.rand.nextInt(1));
                        break;
                    case 2:
                        effect = new ParticleSparkle(world, x + length, y + width, z + height, 0.3F, r, g, b, 3 + world.rand.nextInt(1));
                        break;
                    case 3:
                        effect = new ParticleSparkle(world, x + length, y + width, z + height, 0.3F, r, g, b, 3 + world.rand.nextInt(1));
                        break;
                    case 4:
                        effect = new ParticleSparkle(world, x + height, y + length, z + width, 0.3F, r, g, b, 3 + world.rand.nextInt(1));
                        break;
                    case 5:
                        effect = new ParticleSparkle(world, x + height, y + length, z + width, 0.3F, r, g, b, 3 + world.rand.nextInt(1));
                        break;
                }

                if(effect != null){
                    effect.setGravity(0.02F);
                    Minecraft.getMinecraft().effectRenderer.addEffect(effect);
                }
            }
        }
    }

    public static void handleQuartzCrystalHit(World world, BlockPos pos){
        int puffs = 10;
        int flameWidth = 2;
        double flameBorder = 0.4D;
        int meta = BlockAccessUtils.getBlockMetadata(world, pos);

        if(Minecraft.getMinecraft().gameSettings.particleSetting < 2){
            double x1 = pos.getX() + 0.5D;
            double y1 = pos.getY() + 0.7D;
            double z1 = pos.getZ() + 0.5D;
            double modH1 = 0.2199999988079071D;
            double modH2 = 0.27000001072883606D;

            for(int i = 0; i < puffs; ++i){
                ParticleSparkle effect = null;
                double width = flameBorder + (double)world.rand.nextInt(flameWidth) / 10D;
                double height = (double)world.rand.nextInt(7) / 10D + 0.2D;

                switch(meta){
                    case 1:
                        effect = new ParticleSparkle(world, x1 - modH2 + width, y1 + height, z1 + width, 0.3F, 0.2F, 1.0F, 1.0F, 3 + world.rand.nextInt(1));
                        break;
                    default:
                        effect = new ParticleSparkle(world, x1 + width, y1, z1, 0.3F, 0.2F, 1.0F, 1.0F, 3 + world.rand.nextInt(1));
                }

                if(effect != null){
                    effect.setGravity(-0.3F);
                    Minecraft.getMinecraft().effectRenderer.addEffect(effect);
                }
            }
        }
    }

}
