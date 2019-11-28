package top.shellqiqi;

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
        if (type == -1) throw new Exception("Unsupported image type");

        out.writeInt(width);
        out.writeInt(height);
        out.writeInt(type);
        for (int y = 0; y < height; ++y)
            for (int x = 0; x < width; ++x)
                out.writeInt(bufferedImage.getRGB(x, y));

        out.close();
    }

    static BufferedImage readCIM(final String filePath) throws Exception {
        File file = new File(filePath);
        DataInputStream in = new DataInputStream(new InflaterInputStream(new BufferedInputStream(new FileInputStream(file))));

        int width = in.readInt();
        int height = in.readInt();
        int type = CIM2BIMType(in.readInt());
        if (type == -1) throw new Exception("Unsupported image type");

        BufferedImage bufferedImage = new BufferedImage(width, height, type);
        for (int y = 0; y < height; ++y)
            for (int x = 0; x < width; ++x)
                bufferedImage.setRGB(x, y, in.readInt());

        in.close();
        return bufferedImage;
    }

    private static int CIM2BIMType(int cimType) {
        Pixmap.Format format = Pixmap.Format.fromGdx2DPixmapFormat(cimType);
        if (format == Pixmap.Format.RGB888) {
            return BufferedImage.TYPE_3BYTE_BGR;
        } else if (format == Pixmap.Format.RGBA8888) {
            return BufferedImage.TYPE_4BYTE_ABGR;
        } else {
            return -1;
        }
    }

    private static int BIM2CIMType(int bimType) {
        switch (bimType) {
            case BufferedImage.TYPE_3BYTE_BGR:
                return Pixmap.Format.toGdx2DPixmapFormat(Pixmap.Format.RGB888);
            case BufferedImage.TYPE_4BYTE_ABGR:
                return Pixmap.Format.toGdx2DPixmapFormat(Pixmap.Format.RGBA8888);
            default:
                return -1;
        }
    }
}
