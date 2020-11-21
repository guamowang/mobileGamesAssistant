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
public class FashiHelper {
    private static final String IMAGE_NAME = "fashi.png";
    private static final RgbInfo START_TOUCH_POINT = new RgbInfo(255, 165, 21);
    private static final RgbInfo RIGHT_TOP_GOLD_COIN = new RgbInfo(255, 221, 130);
    private static final RgbInfo REVIVE_BUTTON = new RgbInfo(255, 65, 18);
    private static final RgbInfo DOUBLE_FETCH = new RgbInfo(187, 24, 251);
    private static final RgbInfo DOUBLE_FETCH2 = new RgbInfo(176, 23, 253);
    private static final RgbInfo FAIL_CONFIRM_BUTTON = new RgbInfo(255, 237, 188);
    private static final RgbInfo SHENQI_JIESUO_CONFIRM_BUTTON = new RgbInfo(255, 198, 10);
    private static final RgbInfo BLACK = new RgbInfo(42, 45, 51);
    private static final RgbInfo WHITE = new RgbInfo(255, 255, 255);

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        int executeCount = 0;
        int step = 1;
        while (true) {
            executeCount++;
            System.out.println("当前第" + executeCount + "次执行!");

            // 执行ADB命令，获取截图，上传到电脑并打开
            HelperUtils.screenshotAndUpload(IMAGE_NAME);

            // 读取截图文件
            BufferedImage bufferedImage = ImageIO.read(HelperUtils.getScreenshotFile(IMAGE_NAME));

            // 开始挑战
            if (step == 1) {
                if (HelperUtils.pointMatchColor(bufferedImage, 400, 1900, START_TOUCH_POINT)) {
                    // 进入战场
                    HelperUtils.executeTouch(400, 1900);
                    TimeUnit.MILLISECONDS.sleep(2000);
                    // 往前走进入第一关
                    goAhead(5000);
                    step = 2;
                    continue;
                }
            }

            // 判断打完第一关，右上角出现黄色金币图案
            if (step == 2) {
                if (HelperUtils.pointMatchColor(bufferedImage, 1000, 130, RIGHT_TOP_GOLD_COIN)) {
                    goAhead(5000);
                    // 点击放弃（+9金币）按钮
                    HelperUtils.executeTouch(400, 2100);
                    TimeUnit.MILLISECONDS.sleep(50);
                    goAhead(3000);
                    step = 3;
                    continue;
                }

            }

            // 判断打完第二关，右上角出现黄色金币图案
            if (step == 3) {
                if (HelperUtils.pointMatchColor(bufferedImage, 1000, 130, RIGHT_TOP_GOLD_COIN)) {
                    // 走向第三关
                    goAhead(3500);
                    // 在第三关自杀
                    goAhead(15000);
                    step = 4;
                    continue;
                }
            }

            // 判断在第三关是否死了
            if (step == 4) {
                if (HelperUtils.pointMatchColor(bufferedImage, 300, 1300, REVIVE_BUTTON)) {
                    HelperUtils.executeTouch(300, 1300);
                    step = 5;
                    continue;
                }
            }

            // 点击双倍领取的广告
            if (step == 5) {
                if (HelperUtils.pointMatchColor(bufferedImage, 900, 1500, DOUBLE_FETCH)) {
                    HelperUtils.executeTouch(200, 1500);
                    step = 7;
                    continue;
                }
                if (HelperUtils.pointMatchColor(bufferedImage, 725, 1600, DOUBLE_FETCH2)) {
                    HelperUtils.executeTouch(200, 1600);
                    step = 7;
                    continue;
                }
            }

            // 点击双倍领取的广告
            if (step == 7) {
                if (HelperUtils.pointMatchColor(bufferedImage, 400, 1900, FAIL_CONFIRM_BUTTON)) {
                    HelperUtils.executeTouch(400, 1900);
                    step = 1;
                    continue;
                }
            }

            // 碰到解锁神器按钮就点了吧
            if (HelperUtils.pointMatchColor(bufferedImage, 500, 1600, SHENQI_JIESUO_CONFIRM_BUTTON)) {
                HelperUtils.executeTouch(500, 1600);
            }

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    /**
     * 往前走time微秒
     */
    private static void goAhead(int time) {
        String command = String.format("adb shell input swipe %s %s %s %s %s", 500, 2000, 500, 1500,
                time);
        if (HelperUtils.DEBUG_INFO_SWITCH_ON) {
            System.out.println(command);
        }
        HelperUtils.executeCommand(command);
    }

    private static void advertisement() throws InterruptedException, IOException {
        int executeCount = 0;
        int step = 1;
        while (true) {
            executeCount++;
            System.out.println("当前第" + executeCount + "次执行!");

            // 执行ADB命令，获取截图，上传到电脑并打开
            HelperUtils.screenshotAndUpload(IMAGE_NAME);

            // 读取截图文件
            BufferedImage bufferedImage = ImageIO.read(HelperUtils.getScreenshotFile(IMAGE_NAME));

            // 开始挑战
            if (step == 1) {
                if (HelperUtils.pointMatchColor(bufferedImage, 400, 1900, START_TOUCH_POINT)) {
                    // 进入战场
                    HelperUtils.executeTouch(400, 1900);
                    TimeUnit.MILLISECONDS.sleep(2000);
                    // 往前走进入第一关
                    goAhead(5000);
                    step = 2;
                    continue;
                }
            }

            // 判断打完第一关，右上角出现黄色金币图案
            if (step == 2) {
                if (HelperUtils.pointMatchColor(bufferedImage, 1000, 130, RIGHT_TOP_GOLD_COIN)) {
                    goAhead(5000);
                    // 点击放弃（+9金币）按钮
                    HelperUtils.executeTouch(400, 2100);
                    TimeUnit.MILLISECONDS.sleep(50);
                    goAhead(3000);
                    step = 3;
                    continue;
                }

            }

            // 判断打完第二关，右上角出现黄色金币图案
            if (step == 3) {
                if (HelperUtils.pointMatchColor(bufferedImage, 1000, 130, RIGHT_TOP_GOLD_COIN)) {
                    // 走向第三关
                    goAhead(3500);
                    // 在第三关自杀
                    goAhead(15000);
                    step = 4;
                    continue;
                }
            }

            // 判断在第三关是否死了
            if (step == 4) {
                if (HelperUtils.pointMatchColor(bufferedImage, 300, 1300, REVIVE_BUTTON)) {
                    HelperUtils.executeTouch(300, 1300);
                    step = 5;
                    continue;
                }
            }

            // 点击双倍领取的广告
            if (step == 5) {
                if (HelperUtils.pointMatchColor(bufferedImage, 900, 1500, DOUBLE_FETCH)) {
                    HelperUtils.executeTouch(900, 1500);
                    step = 6;
                    continue;
                }
                if (HelperUtils.pointMatchColor(bufferedImage, 725, 1600, DOUBLE_FETCH2)) {
                    HelperUtils.executeTouch(725, 1600);
                    step = 6;
                    continue;
                }
            }

            // 点击双倍领取的广告
            if (step == 6) {
                if (HelperUtils.touchCloseAdvertisementCross(bufferedImage)) {
                    step = 7;
                    continue;
                }
                if (HelperUtils.pointMatchColor(bufferedImage, 56, 148, BLACK)
                        && HelperUtils.pointMatchColor(bufferedImage, 1000, 148, WHITE)) {
                    HelperUtils.executeTouch(56, 148);
                    step = 7;
                    continue;
                }
            }

            // 点击双倍领取的广告
            if (step == 7) {
                if (HelperUtils.pointMatchColor(bufferedImage, 400, 1900, FAIL_CONFIRM_BUTTON)) {
                    HelperUtils.executeTouch(400, 1900);
                    step = 1;
                    continue;
                }
            }

            // 碰到解锁神器按钮就点了吧
            if (HelperUtils.pointMatchColor(bufferedImage, 500, 1600, SHENQI_JIESUO_CONFIRM_BUTTON)) {
                HelperUtils.executeTouch(500, 1600);
            }

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}