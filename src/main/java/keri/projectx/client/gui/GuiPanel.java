package keri.projectx.client.gui;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.lib.math.Point2i;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPanel {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ModPrefs.MODID, "textures/gui/misc.png");
    public static final int ALIGNMENT_LEFT = 0;
    public static final int ALIGNMENT_RIGHT = 1;
    public static final int ALIGNMENT_TOP = 2;
    public static final int ALIGNMENT_BOTTOM = 3;
    public static final int ALIGNMENT_NONE = 4;

    public static void drawBackground(GuiScreen gui, Point2i pos, Point2i size, int alignment){
        drawBackground(gui, pos, size, alignment, new ColourRGBA(255, 255, 255, 255));
    }

    public static void drawBackground(GuiScreen gui, Point2i pos, Point2i size, int alignment, Colour color){
        GlStateManager.pushMatrix();
        Point2i posCornerTL = new Point2i(pos.getX(), pos.getY());
        Point2i minUVCornerTL = new Point2i(0, 0);
        Point2i maxUVCornerTL = new Point2i(4, 4);
        Point2i posCornerBL = new Point2i(pos.getX(), pos.getY() + size.getY());
        Point2i minUVCornerBL = new Point2i(0, 20);
        Point2i maxUVCornerBL = new Point2i(4, 24);
        Point2i posCornerTR = new Point2i(pos.getX() + size.getX(), pos.getY());
        Point2i minUVCornerTR = new Point2i(20, 0);
        Point2i maxUVCornerTR = new Point2i(24, 4);
        Point2i posCornerBR = new Point2i(pos.getX() + size.getX(), pos.getY() + size.getY());
        Point2i minUVCornerBR = new Point2i(20, 20);
        Point2i maxUVCornerBR = new Point2i(24, 24);

        switch(alignment){
            case ALIGNMENT_LEFT:
                draw(gui, posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                draw(gui, posCornerBL, minUVCornerBL, maxUVCornerBL, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    draw(gui, posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    draw(gui, posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    draw(gui, posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_RIGHT:
                draw(gui, posCornerTR, minUVCornerTR, maxUVCornerTR, color);
                draw(gui, posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    draw(gui, posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    draw(gui, posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    draw(gui, posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_TOP:
                draw(gui, posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                draw(gui, posCornerTR, minUVCornerTR, maxUVCornerTR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    draw(gui, posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    draw(gui, posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    draw(gui, posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_BOTTOM:
                draw(gui, posCornerBL, minUVCornerBL, maxUVCornerBL, color);
                draw(gui, posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    draw(gui, posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    draw(gui, posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    draw(gui, posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_NONE:
                draw(gui, posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                draw(gui, posCornerTR, minUVCornerTR, maxUVCornerTR, color);
                draw(gui, posCornerBL, minUVCornerBL, maxUVCornerBL, color);
                draw(gui, posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    draw(gui, posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    draw(gui, posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    draw(gui, posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    draw(gui, posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
        }

        GlStateManager.popMatrix();
    }

    private static void draw(GuiScreen gui, Point2i pos, Point2i minUV, Point2i maxUV, Colour color){
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        color.glColour();
        gui.drawTexturedModalRect(pos.getX(), pos.getY(), minUV.getX(), minUV.getY(), maxUV.getX(), maxUV.getY());
    }

}
