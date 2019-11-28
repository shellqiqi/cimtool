package top.shellqiqi;

import java.io.File;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class App {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("top.shellqiqi.logger");
        for (String imgPath : args) {
            File file = new File(imgPath);
            if (!file.isFile()) {
                logger.warning("Not a file, skip...");
                continue;
            }
            try {
                String newPath;
                if (imgPath.endsWith(".cim")) {
                    newPath = imgPath.replaceAll("cim$", "png");
                    Pixmap tex = PixmapIO.readCIM(Gdx.files.internal(imgPath));
                    PixmapIO.writePNG(Gdx.files.local(newPath), tex);
                } else if (imgPath.endsWith(".png")) {
                    newPath = imgPath.replaceAll("png$", "cim");
                    Pixmap tex = new Pixmap(Gdx.files.internal(imgPath));
                    PixmapIO.writeCIM(Gdx.files.local(newPath), tex);
                } else {
                    logger.warning("Not PNG nor CIM picture file.");
                    continue;
                }
                logger.info("Convert " + imgPath + " to " + newPath);
            } catch (Throwable e) {
                logger.warning("Failed loading " + imgPath + e.getMessage());
            }
        }
    }
}
