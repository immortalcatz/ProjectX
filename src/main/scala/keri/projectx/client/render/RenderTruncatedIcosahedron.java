/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.vec.Vector3;
import keri.projectx.util.ModPrefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTruncatedIcosahedron {

    private static Vector3[] VERTS = new Vector3[60];
    private static int LIST_INDEX;
    private static final ThreadLocal<RenderTruncatedIcosahedron> INSTANCES = ThreadLocal.withInitial(RenderTruncatedIcosahedron::new);

    private RenderTruncatedIcosahedron(){
        this.generate();
    }

    public static RenderTruncatedIcosahedron getInstance() {
        return INSTANCES.get();
    }

    public void render(double size, int colourPent, int colourHex, EnumHedrontexture type) {
        this.render(size, new ColourRGBA(colourPent), new ColourRGBA(colourHex), type);
    }

    public void render(double size, Colour colourPent, Colour colourHex, EnumHedrontexture type) {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_LIGHT0);
        GL11.glDisable(GL11.GL_LIGHT1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(type.getTexture());
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glScaled(size * 0.1D, size * 0.1D, size * 0.1D);
        colourPent.glColour();
        GL11.glCallList(LIST_INDEX);
        colourHex.glColour();
        GL11.glCallList(LIST_INDEX + 1);
        GL11.glScaled(1D / (size * 0.1D), 1D / (size * 0.1D), 1D / (size * 0.1D));
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT1);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    private void generate() {
        int[] s = {1, -1};

        for (int i = 0; i < 4; i++) {
            VERTS[i] = new Vector3(0D, s[(i / 2)], s[(i % 2)] * 3 * MathHelper.phi);
        }

        for (int i = 0; i < 8; i++) {
            VERTS[(i + 4)] = new Vector3(s[(i / 4)] * 2, s[(i / 2 % 2)] * 4.2360679999999995D, s[(i % 2)] * MathHelper.phi);
            VERTS[(i + 12)] = new Vector3(s[(i / 4)], s[(i / 2 % 2)] * 3.6180339999999998D, s[(i % 2)] * 2 * MathHelper.phi);
        }

        for (int i = 0; i < 20; i++) {
            VERTS[(i + 20)] = new Vector3(VERTS[i].y, VERTS[i].z, VERTS[i].x);
            VERTS[(i + 40)] = new Vector3(VERTS[i].z, VERTS[i].x, VERTS[i].y);
        }

        LIST_INDEX = GL11.glGenLists(2);
        GL11.glNewList(LIST_INDEX, GL11.GL_COMPILE);
        GL11.glBegin(GL11.GL_LINE_BIT);

        for (int rot = 0; rot < 3; rot++) {
            for (int i = 0; i < 4; i++) {
                pentagon(rot, i);
            }
        }

        GL11.glEnd();
        GL11.glEndList();
        GL11.glNewList(LIST_INDEX + 1, GL11.GL_COMPILE);
        GL11.glBegin(GL11.GL_LINE_BIT);

        for (int rot = 0; rot < 3; rot++) {
            for (int i = 0; i < 4; i++) {
                hexagon1(rot, i);
            }
        }

        for (int i = 0; i < 8; i++) {
            hexagon2(i);
        }

        GL11.glEnd();
        GL11.glEndList();
    }

    private void renderShape(Vector3[] verts, boolean reverse) {
        Vector3 center = new Vector3();
        for (int i = 0; i < verts.length; i++) {
            center.add(verts[i]);
        }

        center.multiply(1D / verts.length);

        Vector3 prev = verts[0];
        int start = reverse ? verts.length : 1;
        int end = reverse ? -1 : verts.length + 1;
        int step = reverse ? -1 : 1;

        for (int i = start; i != end; i += step) {
            GL11.glTexCoord2d(0.5D, 0.5D);
            GL11.glVertex3d(center.x, center.y, center.z);
            GL11.glTexCoord2d(0D, 0D);
            GL11.glVertex3d(prev.x, prev.y, prev.z);
            GL11.glTexCoord2d(1D, 0D);
            GL11.glVertex3d(verts[(i % verts.length)].x, verts[(i % verts.length)].y, verts[(i % verts.length)].z);
            prev = verts[(i % verts.length)];
        }
    }

    private void pentagon(int rot, int i) {
        Vector3[] pentagon = new Vector3[5];
        pentagon[0] = VERTS[(rot * 20 + i)];
        pentagon[1] = VERTS[((rot * 20 + 2 * i + 44) % 60)];
        pentagon[2] = VERTS[((rot * 20 + i + 12) % 60)];
        pentagon[3] = VERTS[((rot * 20 + i + 16) % 60)];
        pentagon[4] = VERTS[((rot * 20 + 2 * i + 45) % 60)];
        renderShape(pentagon, (i != 0) && (i != 3));
    }

    private void hexagon1(int rot, int i) {
        Vector3[] hexagon = new Vector3[6];
        hexagon[0] = VERTS[(rot * 20 + i / 2)];
        hexagon[1] = VERTS[((rot * 20 + i + 44) % 60)];
        hexagon[2] = VERTS[((rot * 20 + i + 52) % 60)];
        hexagon[3] = VERTS[((rot * 20 + i + 56) % 60)];
        hexagon[4] = VERTS[((rot * 20 + i + 48) % 60)];
        hexagon[5] = VERTS[(rot * 20 + i / 2 + 2)];
        renderShape(hexagon, (i == 0) || (i == 3));
    }

    private void hexagon2(int i) {
        Vector3[] hexagon = new Vector3[6];
        hexagon[0] = VERTS[(4 + i)];
        hexagon[1] = VERTS[(12 + i)];
        hexagon[2] = VERTS[(44 + i / 4 + i % 4 * 2)];
        hexagon[3] = VERTS[(52 + i / 4 + i % 4 * 2)];
        hexagon[4] = VERTS[(24 + i / 2 + i % 2 * 4)];
        hexagon[5] = VERTS[(32 + i / 2 + i % 2 * 4)];
        renderShape(hexagon, (i % 3 != 0) && (i != 5));
    }

    public enum EnumHedrontexture {
        SPACE(new ResourceLocation(ModPrefs.MODID, "textures/models/icosa_space.png")),
        FILL(new ResourceLocation(ModPrefs.MODID, "textures/models/icosa_fill.png"));

        private ResourceLocation texture;

        EnumHedrontexture(ResourceLocation texture){
            this.texture = texture;
        }

        public ResourceLocation getTexture(){
            return texture;
        }
    }

}
