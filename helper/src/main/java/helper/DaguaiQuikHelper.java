package helper;

import entity.RgbInfo;
import utils.HelperUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 我的斧头会变长辅助
 * adb shell screencap -p /sdcard/test.png && adb pull /sdcard/test.png d:/work/helper_screencapture
 */
public class DaguaiQuikHelper {
    private static final String IMAGE_NAME = "daguai.png";
    private static volatile boolean stopClick = false;
    private static final RgbInfo RED_EYE = new RgbInfo(255, 0, 0);
    private static final RgbInfo CAN_REBORN = new RgbInfo(123, 89, 33);
    private static final RgbInfo CAN_REBORN2 = new RgbInfo(115, 89, 33);
    private static final RgbInfo BLACK = new RgbInfo(0, 0, 0);
    private static final RgbInfo BAG_FULL = new RgbInfo(181, 57, 41);
    private static final RgbInfo BAG_FULL2 = new RgbInfo(189, 60, 41);
    private static final RgbInfo BLOOD_HAS1 = new RgbInfo(255, 93, 0);
    private static final RgbInfo BLOOD_HAS2 = new RgbInfo(255, 89, 0);
    private static final RgbInfo BLOOD_NOT_HAS1 = new RgbInfo(16, 57, 82);
    private static final RgbInfo BLOOD_NOT_HAS2 = new RgbInfo(8, 57, 74);
    private static final RgbInfo ORANGE_EQUIPMENT = new RgbInfo(255, 134, 0);
    private static final RgbInfo HAS_EQUIPMENT = new RgbInfo(58, 61, 58);
    private static final RgbInfo BLACK_BACKGROUND = new RgbInfo(20, 20, 20);
    private static boolean bagFull = false;
    private static final boolean FETCH_EQUIPMENT = true;
    private static final boolean WITH_ADVER = false;

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        while (true) {
            // 执行ADB命令，获取截图，上传到电脑并打开
            BufferedImage bufferedImage = HelperUtils.screenshotAndUpload(IMAGE_NAME);
            if (needReborn(bufferedImage)) {
                if (!HelperUtils.pointMatchColor(bufferedImage, 68, 368, BLACK)) {
                    HelperUtils.executeTouchWithSleep(60, 370); // 停止点击
                }

                HelperUtils.executeTouchWithSleep(900, 1750); // 点击转生
                Thread.sleep(1000);
                HelperUtils.executeTouchWithSleep(700, 1500); // 决定了
                Thread.sleep(3000);

                HelperUtils.executeTouchWithSleep(560, 465); // 挑战
                HelperUtils.executeTouchWithSleep(100, 2200); // 挑战
                HelperUtils.executeTouchWithSleep(60, 370); // 开始点击
            }

            Thread.sleep(2000);
        }
    }

    /**
     * 不能秒掉敌人的时候转生
     * @param bufferedImage
     * @return
     */
    private static boolean needReborn(BufferedImage bufferedImage) {
        return HelperUtils.pointMatchColor(bufferedImage, 900, 1750, CAN_REBORN)
                || HelperUtils.pointMatchColor(bufferedImage, 900, 1750, CAN_REBORN2);
    }





}