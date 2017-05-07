package keri.projectx.client.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.util.ResourceAction;
import keri.projectx.util.ModPrefs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTruncatedIcosahedron {

    private Vector3[] verts = new Vector3[60];
    private int listIndex;

    public RenderTruncatedIcosahedron(){
        generate();
    }

    public void render(double size, int colourPent, int colourHex, EnumHedrontexture type) {
        render(size, new ColourRGBA(colourPent), new ColourRGBA(colourHex), type);
    }

    public void render(double size, Colour colourPent, Colour colourHex, EnumHedrontexture type) {
        GL11.glDepthMask(false);
        GL11.glDisable(2896);
        GL11.glDisable(16384);
        GL11.glDisable(16385);
        type.getTexture().bind(true);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glScaled(size * 0.1D, size * 0.1D, size * 0.1D);
        colourPent.glColour();
        GL11.glCallList(listIndex);
        colourHex.glColour();
        GL11.glCallList(listIndex + 1);
        GL11.glScaled(1.0D / (size * 0.1D), 1.0D / (size * 0.1D), 1.0D / (size * 0.1D));
        GL11.glEnable(2896);
        GL11.glEnable(16384);
        GL11.glEnable(16385);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void generate() {
        int[] s = {1, -1};

        for (int i = 0; i < 4; i++) {
            verts[i] = new Vector3(0.0D, s[(i / 2)], s[(i % 2)] * 3 * 1.618034D);
        }

        for (int i = 0; i < 8; i++) {
            verts[(i + 4)] = new Vector3(s[(i / 4)] * 2, s[(i / 2 % 2)] * 4.2360679999999995D, s[(i % 2)] * MathHelper.phi);
            verts[(i + 12)] = new Vector3(s[(i / 4)], s[(i / 2 % 2)] * 3.6180339999999998D, s[(i % 2)] * 2 * MathHelper.phi);
        }

        for (int i = 0; i < 20; i++) {
            verts[(i + 20)] = new Vector3(verts[i].y, verts[i].z, verts[i].x);
            verts[(i + 40)] = new Vector3(verts[i].z, verts[i].x, verts[i].y);
        }

        listIndex = GL11.glGenLists(2);
        GL11.glNewList(listIndex, 4864);
        GL11.glBegin(4);

        for (int rot = 0; rot < 3; rot++) {
            for (int i = 0; i < 4; i++) {
                pentagon(rot, i);
            }
        }

        GL11.glEnd();
        GL11.glEndList();
        GL11.glNewList(listIndex + 1, 4864);
        GL11.glBegin(4);

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

        center.multiply(1.0D / verts.length);

        Vector3 prev = verts[0];
        int start = reverse ? verts.length : 1;
        int end = reverse ? -1 : verts.length + 1;
        int step = reverse ? -1 : 1;

        for (int i = start; i != end; i += step) {
            GL11.glTexCoord2d(0.5D, 0.5D);
            GL11.glVertex3d(center.x, center.y, center.z);
            GL11.glTexCoord2d(0.0D, 0.0D);
            GL11.glVertex3d(prev.x, prev.y, prev.z);
            GL11.glTexCoord2d(1.0D, 0.0D);
            GL11.glVertex3d(verts[(i % verts.length)].x, verts[(i % verts.length)].y, verts[(i % verts.length)].z);
            prev = verts[(i % verts.length)];
        }
    }

    private void pentagon(int rot, int i) {
        Vector3[] pentagon = new Vector3[5];
        pentagon[0] = verts[(rot * 20 + i)];
        pentagon[1] = verts[((rot * 20 + 2 * i + 44) % 60)];
        pentagon[2] = verts[((rot * 20 + i + 12) % 60)];
        pentagon[3] = verts[((rot * 20 + i + 16) % 60)];
        pentagon[4] = verts[((rot * 20 + 2 * i + 45) % 60)];
        renderShape(pentagon, (i != 0) && (i != 3));
    }

    private void hexagon1(int rot, int i) {
        Vector3[] hexagon = new Vector3[6];
        hexagon[0] = verts[(rot * 20 + i / 2)];
        hexagon[1] = verts[((rot * 20 + i + 44) % 60)];
        hexagon[2] = verts[((rot * 20 + i + 52) % 60)];
        hexagon[3] = verts[((rot * 20 + i + 56) % 60)];
        hexagon[4] = verts[((rot * 20 + i + 48) % 60)];
        hexagon[5] = verts[(rot * 20 + i / 2 + 2)];
        renderShape(hexagon, (i == 0) || (i == 3));
    }

    private void hexagon2(int i) {
        Vector3[] hexagon = new Vector3[6];
        hexagon[0] = verts[(4 + i)];
        hexagon[1] = verts[(12 + i)];
        hexagon[2] = verts[(44 + i / 4 + i % 4 * 2)];
        hexagon[3] = verts[(52 + i / 4 + i % 4 * 2)];
        hexagon[4] = verts[(24 + i / 2 + i % 2 * 4)];
        hexagon[5] = verts[(32 + i / 2 + i % 2 * 4)];
        renderShape(hexagon, (i % 3 != 0) && (i != 5));
    }

    public enum EnumHedrontexture {
        SPACE(new ResourceAction(ModPrefs.MODID.toLowerCase(), "textures/models/icosa_space.png")),
        FILL(new ResourceAction(ModPrefs.MODID.toLowerCase(), "textures/models/icosa_fill.png"));

        private ResourceAction texture;

        EnumHedrontexture(ResourceAction texture){
            this.texture = texture;
        }

        public ResourceAction getTexture(){
            return texture;
        }
    }

}
