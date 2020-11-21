package helper;

import entity.RgbInfo;
import utils.HelperUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static utils.HelperUtils.DEBUG_INFO_SWITCH_ON;

/**
 * 抽卡人生辅助器
 */
public class ChoukaHelper {
    private static final String IMAGE_NAME = "chouka.png";
    private static final RgbInfo LIGHT_YELLO_POINT = new RgbInfo(217, 199, 86);
    private static final RgbInfo YELLO_POINT = new RgbInfo(250, 225, 87);

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        int executeCount = 0;
        int advertisementCloseCount = 0;
        while (true) {
            if (DEBUG_INFO_SWITCH_ON) {
                System.out.println("当前第" + ++executeCount + "次执行!");
            }

            // 执行ADB命令，获取截图，上传到电脑并打开
            HelperUtils.screenshotAndUpload(IMAGE_NAME);
            File currentImage = HelperUtils.getScreenshotFile(IMAGE_NAME);
            HelperUtils.recordPictureHistory(executeCount, currentImage);

            // 读取截图文件
            BufferedImage bufferedImage = ImageIO.read(currentImage);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (DEBUG_INFO_SWITCH_ON) {
                System.out.println("宽度：" + width + "，高度：" + height);
            }

            // 点击免费的命运之沙
            if (HelperUtils.pointMatchColor(bufferedImage, 500, 1900, LIGHT_YELLO_POINT)) {
                HelperUtils.executeTouch(500, 1900);
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
                System.out.println("生命之沙第" + ++advertisementCloseCount + "次获取!");
                HelperUtils.executeTouch(965, 182);
                TimeUnit.MILLISECONDS.sleep(1000);
            }

            // 点击确认获取生命之沙
            if (HelperUtils.pointMatchColor(bufferedImage, 500, 1400, YELLO_POINT)) {
                HelperUtils.executeTouch(500, 1400);
            }

            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }

    /**
     * 执行按压
     *
     * @param distance
     * @author LeeHo
     * @update 2017年12月31日 下午12:23:19
     */
    private static void doTouch(double distance) {
        System.out.println("distance: " + distance);
        int pressTime = (int) Math.floor((distance - 250) * 1000 / 285);
        System.out.println("pressTime: " + pressTime);
        //执行按压操作
        int touchX = HelperUtils.getRandomTouchX(300);
        int touchY = HelperUtils.getRandomTouchY(1860);
        String command = String.format("adb shell input swipe %s %s %s %s %s", touchX, touchY, touchX, touchY,
                pressTime);
        System.out.println(command);
        HelperUtils.executeCommand(command);
    }
}
