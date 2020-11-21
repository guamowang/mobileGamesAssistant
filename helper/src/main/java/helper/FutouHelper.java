package helper;

import entity.RgbInfo;
import utils.HelperUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 我的斧头会变长辅助
 */
public class FutouHelper {
    private static final String IMAGE_NAME = "futou.png";
    private static final RgbInfo RGBINFO = new RgbInfo();
    private static final RgbInfo START_POINT = new RgbInfo(89, 185, 60);
    private static final RgbInfo END_POINT1 = new RgbInfo(109, 44, 19);
    private static final RgbInfo END_POINT2 = new RgbInfo(181, 73, 32);
    private static final RgbInfo END_POINT_LEFT_LINE_LOSE = new RgbInfo(224, 108, 54);
    private static final RgbInfo END_POINT_LEFT_LINE_LOSE2 = new RgbInfo(221, 92, 40);
    private static final RgbInfo START_EMPTY_BLOOD_POINT = new RgbInfo(38, 38, 28);
    private static final RgbInfo START_EMPTY_BLOOD_POINT2 = new RgbInfo(38, 32, 21);
    private static final RgbInfo END_EMPTY_BLOOD_POINT = new RgbInfo(182, 73, 32);
    private static final RgbInfo END_EMPTY_BLOOD_POINT2 = new RgbInfo(44, 36, 23);
    private static final RgbInfo END_EMPTY_BLOOD_POINT3 = new RgbInfo(53, 53, 41);
    private static final RgbInfo ADVERTISEMENT_TOUCH_POINT = new RgbInfo(255, 154, 8);
    private static final RgbInfo ADVERTISEMENT_TOUCH_POINT2 = new RgbInfo(245, 148, 8);
    private static final RgbInfo CONFIRM_TOUCH_POINT = new RgbInfo(30, 157, 26);
    private static final RgbInfo START_TOUCH_POINT = new RgbInfo(197, 82, 1);
    private static final RgbInfo THANK_LETTER_CONFIRM_POINT = new RgbInfo(23, 150, 26);
    private static final int BLOOD_ITEM_HEIGHT = 1585;

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
            BufferedImage bufferedImage = HelperUtils.screenshotAndUpload(IMAGE_NAME);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (HelperUtils.DEBUG_INFO_SWITCH_ON) {
                System.out.println("宽度：" + width + "，高度：" + height);
            }

            // 开始挑战
            if (HelperUtils.pointMatchColor(bufferedImage, 500, 1700, START_TOUCH_POINT)) {
                HelperUtils.executeTouch(500, 1700);
            }

            // 获取与敌人的距离
            int startPointX = 0;
            int endPointX = 0;
            for (int x = 10; x < width; x++) {
                RGBINFO.loadRGB(bufferedImage, x, BLOOD_ITEM_HEIGHT);
                if (startPointX == 0
                        && (RGBINFO.equals(START_POINT)
                        || RGBINFO.equals(START_EMPTY_BLOOD_POINT)
                        || RGBINFO.equals(START_EMPTY_BLOOD_POINT2))) {
                    startPointX = x;
                    x += 150;
                    continue;
                }
                if (RGBINFO.equals(END_POINT1) || RGBINFO.equals(END_POINT2)
                        || RGBINFO.equals(END_EMPTY_BLOOD_POINT)
                        || RGBINFO.equals(END_EMPTY_BLOOD_POINT2)
                        || RGBINFO.equals(END_EMPTY_BLOOD_POINT3)
                        || RGBINFO.equals(END_POINT_LEFT_LINE_LOSE)
                        || RGBINFO.equals(END_POINT_LEFT_LINE_LOSE2)) {
                    endPointX = x;
                    break;
                }
            }

            // 执行攻击
            if (Math.abs(startPointX - 45) <= 1 && endPointX != 0) {
                doTouch(endPointX - startPointX);
                TimeUnit.MILLISECONDS.sleep(900 + (int) Math.floor(Math.random() * 300));
            }

            // 点击广告
            if (HelperUtils.pointMatchColor(bufferedImage, 800, 1500, ADVERTISEMENT_TOUCH_POINT)
                    || HelperUtils.pointMatchColor(bufferedImage, 800, 1500, ADVERTISEMENT_TOUCH_POINT2)) {
                HelperUtils.executeTouch(800, 1500);
            }

            // 关闭广告
            HelperUtils.touchCloseAdvertisementCross(bufferedImage);

            // 点击感谢信确认
            if (HelperUtils.pointMatchColor(bufferedImage, 500, 1900, THANK_LETTER_CONFIRM_POINT)) {
                HelperUtils.executeTouch(500, 1900);
            }

            // 点击确认获取通关奖励
            if (HelperUtils.pointMatchColor(bufferedImage, 500, 1500, CONFIRM_TOUCH_POINT)) {
                HelperUtils.executeTouch(500, 1500);
            }


            TimeUnit.MILLISECONDS.sleep(100);
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
        if (HelperUtils.DEBUG_INFO_SWITCH_ON) {
            System.out.println("distance: " + distance);
        }
        int pressTime = (int) Math.floor((distance - 250) * 1000 / 290);
        if (HelperUtils.DEBUG_INFO_SWITCH_ON) {
            System.out.println("pressTime: " + pressTime);
        }
        //执行按压操作
        int touchX = HelperUtils.getRandomTouchX(300);
        int touchY = HelperUtils.getRandomTouchY(1860);
        String command = String.format("adb shell input swipe %s %s %s %s %s", touchX, touchY, touchX, touchY,
                pressTime);
        if (HelperUtils.DEBUG_INFO_SWITCH_ON) {
            System.out.println(command);
        }
        HelperUtils.executeCommand(command);
    }

}