package top.shellqiqi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

class PNGUtil {

    static void writePNG(final String filePath, final BufferedImage image) throws Exception {
        ImageIO.write(image, "png", new File(filePath));
    }

    static BufferedImage readPNG(final String filePath) throws Exception {
        return ImageIO.read(new File(filePath));
    }
}
