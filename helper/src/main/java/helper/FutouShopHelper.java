package helper;

import entity.RgbInfo;
import utils.HelperUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 我的斧头会变长辅助
 */
public class FutouShopHelper {
    private static final String IMAGE_NAME = "futouShop.png";
    private static final RgbInfo FETCH_TOUCH_POINT = new RgbInfo(255, 147, 4);
    private static final RgbInfo FETCH_TOUCH_POINT2 = new RgbInfo(245, 141, 4);
    private static final RgbInfo THANK_LETTER_CONFIRM_POINT = new RgbInfo(23, 150, 26);

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        int executeCount = 0;
        while (true) {
            executeCount++;
            System.out.println("当前第" + executeCount + "次执行!");

            // 执行ADB命令，获取截图，上传到电脑并打开
            HelperUtils.screenshotAndUpload(IMAGE_NAME);
            File currentImage = HelperUtils.getScreenshotFile(IMAGE_NAME);

            // 读取截图文件
            BufferedImage bufferedImage = ImageIO.read(currentImage);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            // 点击广告
            if ((HelperUtils.pointMatchColor(bufferedImage, 800, 940, FETCH_TOUCH_POINT)
                    || HelperUtils.pointMatchColor(bufferedImage, 800, 940, FETCH_TOUCH_POINT2))
                    && HelperUtils.isWhitePoint(bufferedImage, 716, 854)) {
                HelperUtils.executeTouch(800, 940);
            }

            // 右上角有白色X，则点击
            if (HelperUtils.isWhitePoint(bufferedImage, 965, 182)
                    && HelperUtils.isWhitePoint(bufferedImage, 951, 168)
                    && HelperUtils.isWhitePoint(bufferedImage, 980, 168)
                    && HelperUtils.isWhitePoint(bufferedImage, 951, 197)
                    && HelperUtils.isWhitePoint(bufferedImage, 980, 197)
                    && !HelperUtils.isWhitePoint(bufferedImage, 951, 182)
                    && !HelperUtils.isWhitePoint(bufferedImage, 980, 182)
                    && !HelperUtils.isWhitePoint(bufferedImage, 965, 168)
                    && !HelperUtils.isWhitePoint(bufferedImage, 965, 197)) {
                HelperUtils.executeTouch(965, 182);
            }

            // 点击感谢信确认
            if (HelperUtils.pointMatchColor(bufferedImage, 500, 1900, THANK_LETTER_CONFIRM_POINT)) {
                HelperUtils.executeTouch(500, 1900);
            }

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

}