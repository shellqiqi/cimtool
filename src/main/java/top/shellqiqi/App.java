package top.shellqiqi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final Logger logger = LogManager.getLogger("CIMTool");

    private static Runnable convert(final String imgPath) {
        return new Runnable() {
            public void run() {
                try {
                    if (imgPath.endsWith(".cim")) {
                        PNGUtil.writePNG(
                                imgPath.replaceAll("cim$", "png"),
                                CIMUtil.readCIM(imgPath)
                        );
                        logger.info(imgPath + " -> PNG");
                    } else if (imgPath.endsWith(".png")) {
                        CIMUtil.writeCIM(
                                imgPath.replaceAll("png$", "cim"),
                                PNGUtil.readPNG(imgPath)
                        );
                        logger.info(imgPath + " -> CIM");
                    } else {
                        logger.warn("Unknown suffix");
                    }
                } catch (Throwable e) {
                    logger.error(e.getMessage());
                }
            }
        };
    }

    public static void main(String[] args) {
        final int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);
        for (String imgPath : args)
            pool.execute(convert(imgPath));
        pool.shutdown();
    }
}
