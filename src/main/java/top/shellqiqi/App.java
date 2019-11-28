package top.shellqiqi;

import java.util.logging.Logger;

public class App {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("top.shellqiqi.cimtool");
        try {
            for (String imgPath : args) {
                if (imgPath.endsWith(".cim")) {
                    PNGUtil.writePNG(
                            imgPath.replaceAll("cim$", "png"),
                            CIMUtil.readCIM(imgPath)
                    );
                } else if (imgPath.endsWith(".png")) {
                    CIMUtil.writeCIM(
                            imgPath.replaceAll("png$", "cim"),
                            PNGUtil.readPNG(imgPath)
                    );
                } else {
                    logger.warning("Unknown suffix");
                    continue;
                }
                logger.info("Convert Done");
            }
        } catch (Throwable e) {
            logger.warning(e.getMessage());
        }
    }
}
