package helper;

import entity.RgbInfo;
import utils.HelperUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 我的斧头会变长辅助
 * adb shell screencap -p /sdcard/test.png && adb pull /sdcard/test.png d:/work/helper_screencapture
 */
public class DaguaiMaxAndEquipmentHelper2 {
    private static final String IMAGE_NAME = "daguai.png";
    private static volatile boolean stopClick = false;
    private static final RgbInfo RED_EYE = new RgbInfo(255, 0, 0);
    private static final RgbInfo CAN_REBORN = new RgbInfo(123, 89, 33);
    private static final RgbInfo CAN_REBORN2 = new RgbInfo(115, 89, 33);
    private static final RgbInfo BLACK = new RgbInfo(0, 0, 0);
    private static final RgbInfo BAG_FULL = new RgbInfo(181, 57, 41);
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
                stopClick = true;
                Thread.sleep(1000);
                HelperUtils.executeTouchWithSleep(300, 1300); // 确认现金领取
                if (!HelperUtils.pointMatchColor(bufferedImage, 68, 368, BLACK)) {
                    HelperUtils.executeTouchWithSleep(60, 370); // 停止点击
                    HelperUtils.executeTouchWithSleep(300, 1300); // 确认现金领取
                }

                if (HelperUtils.pointMatchColor(bufferedImage, 900, 1750, CAN_REBORN)
                        || HelperUtils.pointMatchColor(bufferedImage, 900, 1750, CAN_REBORN2)) {
                    HelperUtils.executeTouchWithSleep(900, 1750); // 点击转生
                    Thread.sleep(1000);
                    HelperUtils.executeTouchWithSleep(700, 1700); // 决定了
                    Thread.sleep(3000);
                    cleanEquipment();
                }

                HelperUtils.executeTouchWithSleep(560, 465); // 挑战
                stopClick = false;
                HelperUtils.executeTouchWithSleep(60, 370); // 开始点击
            }

            // 看广告
            if (WITH_ADVER && HelperUtils.isWhitePoint(bufferedImage, 240, 395) && !HelperUtils.isWhitePoint(bufferedImage, 975, 520)) {
                stopClick = true;
                HelperUtils.executeTouchWithSleep(60, 370); // 停止点击
                HelperUtils.executeTouchWithSleep(300, 1300); // 确认现金领取
                HelperUtils.executeTouchWithSleep(977, 450); // GET
                Thread.sleep(1000);
                HelperUtils.executeTouchWithSleep(500, 1400); // 点击广告领取

                int count = 0;
                while (!HelperUtils.touchCloseAdvertisementCross(bufferedImage)) {
                    Thread.sleep(3000);
                    bufferedImage = HelperUtils.screenshotAndUpload(IMAGE_NAME);
                    count++;
                    if (count > 30) {
                        break;
                    }
                }
                if (count > 30) {
                    continue;
                }

                HelperUtils.executeTouchWithSleep(550, 750); // 当广告没看完中途退出时，关闭广告
                HelperUtils.executeTouchWithSleep(560, 465); // 挑战
                stopClick = false;
                HelperUtils.executeTouchWithSleep(60, 370); // 开始点击
            }


            if (FETCH_EQUIPMENT && !bagFull && HelperUtils.pointMatchColor(bufferedImage, 530, 1330, BAG_FULL)) {
                stopClick = true;
                Thread.sleep(1000);
                HelperUtils.executeTouchWithSleep(300, 1300); // 确认现金领取
                HelperUtils.executeTouchWithSleep(60, 370); // 停止点击
                HelperUtils.executeTouchWithSleep(300, 1300); // 确认现金领取
                HelperUtils.executeTouchWithSleep(530, 1330); // 知道了

                cleanEquipment();

                HelperUtils.executeTouchWithSleep(560, 465); // 挑战
                stopClick = false;
                HelperUtils.executeTouchWithSleep(60, 370); // 开始点击
            }
            Thread.sleep(2000);
        }
    }

    private static void cleanEquipment() throws InterruptedException {
        HelperUtils.executeTouchWithSleep(600, 2200); // 装备
        HelperUtils.executeTouchWithSleep(1000, 1200); // 打开一键分解
        HelperUtils.executeTouchWithSleep(550, 900); // 分解所有普通装备
        HelperUtils.executeTouchWithSleep(550, 1000); // 分解所有优秀装备
        HelperUtils.executeTouchWithSleep(550, 1050); // 分解所有精良装备
        HelperUtils.executeTouchWithSleep(550, 1350); // 分解所有稀有装备
        HelperUtils.executeTouchWithSleep(550, 1500); // 分解所有卓越装备
        HelperUtils.executeTouchWithSleep(550, 650); // 关闭一键分解
        HelperUtils.executeTouchWithSleep(100, 2200); // 我
    }

    /**
     * 不能秒掉敌人的时候转生
     * @param bufferedImage
     * @return
     */
    private static boolean needReborn(BufferedImage bufferedImage) {
        if ((HelperUtils.pointMatchColor(bufferedImage, 400, 280, BLOOD_HAS1) || HelperUtils.pointMatchColor(bufferedImage, 400, 280, BLOOD_HAS2))
                && (HelperUtils.pointMatchColor(bufferedImage, 700, 280, BLOOD_NOT_HAS1) || HelperUtils.pointMatchColor(bufferedImage, 700, 280, BLOOD_NOT_HAS2))) {
            return true;
        }
        return HelperUtils.pointMatchColor(bufferedImage, 560, 465, RED_EYE);
    }

    /**
     * 超过100关重生
     * @param bufferedImage
     * @return
     */
    private static boolean needReborn1(BufferedImage bufferedImage) {
        if (HelperUtils.isWhitePoint(bufferedImage, 420, 375) ||
                HelperUtils.isWhitePoint(bufferedImage, 420, 390) ||
                HelperUtils.isWhitePoint(bufferedImage, 420, 407)) {
            return true;
        }
        return HelperUtils.pointMatchColor(bufferedImage, 560, 465, RED_EYE);
    }







}