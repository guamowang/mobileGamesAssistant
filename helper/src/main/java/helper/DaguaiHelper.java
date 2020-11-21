package helper;

import entity.RgbInfo;
import utils.HelperUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 我的斧头会变长辅助
 */
public class DaguaiHelper {
    private static final String IMAGE_NAME = "daguai.png";
    private static final RgbInfo HERO_LEVEL_UP = new RgbInfo(205, 151, 58);
    private static final RgbInfo HERO_LEVEL_UP2 = new RgbInfo(197, 150, 58);
    private static final RgbInfo GREEN = new RgbInfo(26, 90, 74);
    private static final RgbInfo XIANGZI_LINGQU = new RgbInfo(181, 57, 41);

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        new Thread( new Runnable() {
            public void run(){
                while (true) {
                    HelperUtils.executeTouch(1000, 700);
                }
            }
        }).start();


        while (true) {
            // 执行ADB命令，获取截图，上传到电脑并打开
            HelperUtils.screenshotAndUpload(IMAGE_NAME);
            // 读取截图文件
            BufferedImage bufferedImage = ImageIO.read(HelperUtils.getScreenshotFile(IMAGE_NAME));

            HelperUtils.executeTouch(1000, 1435, bufferedImage, HERO_LEVEL_UP, 20);
            HelperUtils.executeTouch(1000, 1435, bufferedImage, HERO_LEVEL_UP2, 20);

            if (HelperUtils.executeTouch(850, 1650, bufferedImage, HERO_LEVEL_UP, 5)
                || HelperUtils.executeTouch(850, 1650, bufferedImage, HERO_LEVEL_UP2, 5)) {
                HelperUtils.executeTouch(100, 2200, 3);
            }

            if (HelperUtils.executeTouch(500, 1400, bufferedImage, GREEN, 1)) {
                HelperUtils.executeTouch(100, 2200, 3);
            }

            HelperUtils.executeTouch(300, 1300, bufferedImage, XIANGZI_LINGQU, 5);
            Thread.sleep(100);
        }
    }
}