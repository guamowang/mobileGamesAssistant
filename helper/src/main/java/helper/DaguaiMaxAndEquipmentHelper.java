package helper;

import entity.RgbInfo;
import utils.HelperUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 我的斧头会变长辅助
 */
public class DaguaiMaxAndEquipmentHelper {
    private static final String IMAGE_NAME = "daguai.png";
    private static volatile boolean stopClick = false;
    private static final RgbInfo RED_EYE = new RgbInfo(255, 0, 0);
    private static final RgbInfo CAN_REBORN = new RgbInfo(197, 150, 58);
    private static final RgbInfo BLACK = new RgbInfo(0, 0, 0);
    private static final RgbInfo BAG_FULL = new RgbInfo(181, 57, 41);
    private static final RgbInfo ORANGE_EQUIPMENT = new RgbInfo(255, 134, 0);
    private static final RgbInfo HAS_EQUIPMENT = new RgbInfo(58, 61, 58);
    private static final RgbInfo BLACK_BACKGROUND = new RgbInfo(20, 20, 20);
    private static boolean bagFull = false;
    private static final boolean FETCH_EQUIPMENT = false;

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        if (FETCH_EQUIPMENT) {
            new Thread( new Runnable() {
                public void run(){
                    while (true) {
                        if (!stopClick) {
                            HelperUtils.executeTouch(520, 860);
                        }
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        while (true) {
            // 执行ADB命令，获取截图，上传到电脑并打开
            BufferedImage bufferedImage = HelperUtils.screenshotAndUpload(IMAGE_NAME);
            if (HelperUtils.pointMatchColor(bufferedImage, 560, 465, RED_EYE)) {
                stopClick = true;
                Thread.sleep(1000);
                HelperUtils.executeTouchWithSleep(300, 1300); // 确认现金领取
                if (!HelperUtils.pointMatchColor(bufferedImage, 68, 368, BLACK)) {
                    HelperUtils.executeTouchWithSleep(60, 370); // 停止点击
                    HelperUtils.executeTouchWithSleep(300, 1300); // 确认现金领取
                }

                if (HelperUtils.pointMatchColor(bufferedImage, 900, 1750, CAN_REBORN)) {
                    HelperUtils.executeTouchWithSleep(900, 1750); // 点击转生
                    Thread.sleep(1000);
                    HelperUtils.executeTouchWithSleep(700, 1700); // 决定了
                    Thread.sleep(3000);
                    HelperUtils.executeTouchWithSleep(300, 2200); // 伙伴
                    HelperUtils.executeTouchWithSleep(100, 2200); // 我
                }

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
                HelperUtils.executeTouchWithSleep(700, 2250); // 切换装备页签

                bagFull = true;
                cleanEquipment(100); // 切换装备-武器页签
                cleanEquipment(300); // 切换装备-衣服页签
                cleanEquipment(500); // 切换装备-帽子页签
                cleanEquipment(700); // 切换装备-首饰页签

                HelperUtils.executeTouchWithSleep(800, 1000); // 关闭“已装备”弹出框
                HelperUtils.executeTouchWithSleep(100, 2200); // 我
                HelperUtils.executeTouchWithSleep(560, 465); // 挑战
                stopClick = false;
                HelperUtils.executeTouchWithSleep(60, 370); // 开始点击
            }
            Thread.sleep(2000);
        }
    }

    private static void cleanEquipment(int x) throws InterruptedException, IOException {
        BufferedImage bufferedImage;
        HelperUtils.executeTouchWithSleep(x, 1400); // 切换装备-XX页签
        bufferedImage = HelperUtils.screenshotAndUpload(IMAGE_NAME);
        if (equipmentCanRecovery(bufferedImage)) {
            bagFull = false;
            do {
                if (fourEquipmentCanRecovery(bufferedImage)){
                    fourEquipmentRecovery();
                    HelperUtils.executeTouchWithSleep(x, 1400); // 切换装备-XX页签
                }
                if (thirdEquipmentCanRecovery(bufferedImage)){
                    thirdEquipmentRecovery();
                    HelperUtils.executeTouchWithSleep(x, 1400); // 切换装备-XX页签
                }
                if (secondEquipmentCanRecovery(bufferedImage)){
                    secondEquipmentRecovery();
                    HelperUtils.executeTouchWithSleep(x, 1400); // 切换装备-XX页签
                }
                if (firstEquipmentCanRecovery(bufferedImage)){
                    firstEquipmentRecovery();
                    HelperUtils.executeTouchWithSleep(x, 1400); // 切换装备-XX页签
                }
                bufferedImage = HelperUtils.screenshotAndUpload(IMAGE_NAME);
            } while (equipmentCanRecovery(bufferedImage));
        }
    }

    private static boolean equipmentCanRecovery(BufferedImage bufferedImage) {
        return firstEquipmentCanRecovery(bufferedImage)
            || secondEquipmentCanRecovery(bufferedImage)
            || thirdEquipmentCanRecovery(bufferedImage)
            || fourEquipmentCanRecovery(bufferedImage);
    }

    private static boolean firstEquipmentCanRecovery(BufferedImage bufferedImage) {
        return !HelperUtils.pointMatchColor(bufferedImage, 100, 1585, ORANGE_EQUIPMENT)
                && !HelperUtils.pointMatchColor(bufferedImage, 1000, 1500, BLACK_BACKGROUND)
                && !HelperUtils.pointMatchColor(bufferedImage, 1000, 1500, HAS_EQUIPMENT);
    }
    private static void firstEquipmentRecovery() throws InterruptedException {
        HelperUtils.executeTouchWithSleep(100, 1585); // 点开装备
        HelperUtils.executeTouchWithSleep(700, 1250); // 分解
    }

    private static boolean secondEquipmentCanRecovery(BufferedImage bufferedImage) {
        return !HelperUtils.pointMatchColor(bufferedImage, 100, 1765, ORANGE_EQUIPMENT)
                && !HelperUtils.pointMatchColor(bufferedImage, 1000, 1700, BLACK_BACKGROUND)
                && !HelperUtils.pointMatchColor(bufferedImage, 1000, 1700, HAS_EQUIPMENT);
    }
    private static void secondEquipmentRecovery() throws InterruptedException {
        HelperUtils.executeTouchWithSleep(100, 1765); // 点开装备
        HelperUtils.executeTouchWithSleep(700, 1250); // 分解
    }

    private static boolean thirdEquipmentCanRecovery(BufferedImage bufferedImage) {
        return !HelperUtils.pointMatchColor(bufferedImage, 100, 1945, ORANGE_EQUIPMENT)
                && !HelperUtils.pointMatchColor(bufferedImage, 1000, 1900, BLACK_BACKGROUND)
                && !HelperUtils.pointMatchColor(bufferedImage, 1000, 1900, HAS_EQUIPMENT);
    }
    private static void thirdEquipmentRecovery() throws InterruptedException {
        HelperUtils.executeTouchWithSleep(100, 1945); // 点开装备
        HelperUtils.executeTouchWithSleep(700, 1250); // 分解
    }

    private static boolean fourEquipmentCanRecovery(BufferedImage bufferedImage) {
        return !HelperUtils.pointMatchColor(bufferedImage, 100, 2125, ORANGE_EQUIPMENT)
                && !HelperUtils.pointMatchColor(bufferedImage, 1000, 2050, BLACK_BACKGROUND)
                && !HelperUtils.pointMatchColor(bufferedImage, 1000, 2050, HAS_EQUIPMENT);
    }
    private static void fourEquipmentRecovery() throws InterruptedException {
        HelperUtils.executeTouchWithSleep(100, 2125); // 点开装备
        HelperUtils.executeTouchWithSleep(700, 1250); // 分解
    }

}