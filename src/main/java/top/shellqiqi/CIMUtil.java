package top.shellqiqi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

class CIMUtil {

    static void writeCIM(final String filePath, final BufferedImage bufferedImage) throws Exception {
        File file = new File(filePath);
        DataOutputStream out = new DataOutputStream(new DeflaterOutputStream(new FileOutputStream(file)));

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int type = BIM2CIMType(bufferedImage.getType());
        if (type == -1) throw new Exception("Only supported RGBA8888");

        out.writeInt(width);
        out.writeInt(height);
        out.writeInt(type);

        Color color = new Color();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Color.argb8888ToColor(color, bufferedImage.getRGB(x, y));
                out.writeInt(Color.rgba8888(color));
            }
        }

        out.close();
    }

    static BufferedImage readCIM(final String filePath) throws Exception {
        File file = new File(filePath);
        DataInputStream in = new DataInputStream(new InflaterInputStream(new BufferedInputStream(new FileInputStream(file))));

        int width = in.readInt();
        int height = in.readInt();
        int type = CIM2BIMType(in.readInt());
        if (type == -1) throw new Exception("Only supported RGBA8888");

        BufferedImage bufferedImage = new BufferedImage(width, height, type);

        Color color = new Color();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                color.set(in.readInt());
                bufferedImage.setRGB(x, y, Color.argb8888(color));
            }
        }

        in.close();
        return bufferedImage;
    }

    private static int CIM2BIMType(int cimType) {
        Pixmap.Format format = Pixmap.Format.fromGdx2DPixmapFormat(cimType);
        if (format == Pixmap.Format.RGBA8888)
            return BufferedImage.TYPE_4BYTE_ABGR;
        else
            return -1;
    }

    private static int BIM2CIMType(int bimType) {
        if (bimType == BufferedImage.TYPE_4BYTE_ABGR)
            return Pixmap.Format.toGdx2DPixmapFormat(Pixmap.Format.RGBA8888);
        else
            return -1;
    }
}
