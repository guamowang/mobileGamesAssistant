package utils;

import entity.RgbInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelperUtils {
    public static final boolean DEBUG_INFO_SWITCH_ON = false;
    private static final String STORE_DIR = "d:/work/helper_screencapture";
    private static final int IMAGE_HISTORY_LENGTH = 5;
    private static final long[] IMAGE_HISTORY = new long[IMAGE_HISTORY_LENGTH];



    /**
     * 执行命令
     *
     * @param command
     * @author LeeHo
     * @update 2017年12月31日 下午12:13:39
     */
    public static void executeCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            if (DEBUG_INFO_SWITCH_ON) {
                System.out.println("exec command start: " + command);
            }
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = bufferedReader.readLine();
            if (DEBUG_INFO_SWITCH_ON) {
                if (line != null) {
                    System.out.println(line);
                }
                System.out.println("exec command end: " + command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * 手机屏幕截图并上传文件
     * @param pictureName
     */
    public static BufferedImage screenshotAndUpload(String pictureName) throws IOException {
        createScreenshotStoreCatalog();
        executeCommand("adb shell screencap -p /sdcard/" + pictureName);
        executeCommand("adb pull /sdcard/" + pictureName + " " + STORE_DIR);
        return ImageIO.read(HelperUtils.getScreenshotFile(pictureName));
    }

    /**
     * 创建存放截图的目录
     * @return
     */
    public static boolean createScreenshotStoreCatalog() {
        File storeDir = new File(STORE_DIR);
        if (!storeDir.exists()) {
            boolean flag = storeDir.mkdir();
            if (!flag) {
                System.err.println("创建图片存储目录失败");
                return true;
            }
        }
        return false;
    }

    /**
     * 打开截图文件
     * @param imageName
     * @return
     */
    public static File getScreenshotFile(String imageName) {
        File currentImage = new File(STORE_DIR, imageName);
        if (!currentImage.exists()) {
            System.out.println("图片不存在");
            return null;
        }
        return currentImage;
    }

    /**
     * 记录历史N次截图大小
     * @param executeCount
     * @param currentImage
     */
    public static void recordPictureHistory(int executeCount, File currentImage) {
        long length = currentImage.length();
        IMAGE_HISTORY[executeCount % IMAGE_HISTORY_LENGTH] = length;
    }

    /**
     * 获取指定位置随机往右偏移的位置x坐标
     * @param i
     * @return
     */
    public static int getRandomTouchX(int i) {
        return i + (int)Math.floor(Math.random() * 60);
    }

    /**
     * 获取指定位置随机往上偏移的位置y坐标
     * @param i
     * @return
     */
    public static int getRandomTouchY(int i) {
        return i + (int)Math.floor(Math.random() * 60);
    }

    /**
     * 颜色相等则执行点击
     * @param x
     * @param y
     * @param bufferedImage
     * @param rgbInfo
     */
    public static boolean executeTouch(int x, int y, BufferedImage bufferedImage, RgbInfo rgbInfo, int times) {
        if (HelperUtils.pointMatchColor(bufferedImage, x, y, rgbInfo)) {
            String command = String.format("adb shell input tap %s %s", x, y);
            for (int i = 0; i < times; i++) {
                executeCommand(command);
            }
            return true;
        }
        return false;
    }

    /**
     * 执行点击
     * @param x
     * @param y
     */
    public static void executeTouch(int x, int y) {
        String command = String.format("adb shell input tap %s %s", x, y);
        executeCommand(command);
    }
    /**
     * 执行点击
     * @param x
     * @param y
     */
    public static void executeTouchWithSleep(int x, int y) throws InterruptedException {
        String command = String.format("adb shell input tap %s %s", x, y);
        executeCommand(command);
        Thread.sleep(100);
    }

    /**
     * 执行点击
     * @param x
     * @param y
     */
    public static void executeTouch(int x, int y, int times) {
        String command = String.format("adb shell input tap %s %s", x, y);
        for (int i = 0; i < times; i++) {
            executeCommand(command);
        }
    }

    /**
     * 判断一个点是否是白色
     *
     * @param bufferedImage
     * @param x
     * @param y
     * @return
     */
    public static boolean isWhitePoint(BufferedImage bufferedImage, int x, int y) {
        int pixel = bufferedImage.getRGB(x, y);
        //转换为RGB数字
        int rValue = ((pixel & 0xff0000) >> 16);
        int gValue = ((pixel & 0xff00) >> 8);
        int bValue = ((pixel & 0xff));

        if (rValue > 200 && gValue > 200 && bValue > 200) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个点是否是白色
     *
     * @param bufferedImage
     * @param x
     * @param y
     * @return
     */
    public static boolean isLightWhitePoint(BufferedImage bufferedImage, int x, int y) {
        int pixel = bufferedImage.getRGB(x, y);
        //转换为RGB数字
        int rValue = ((pixel & 0xff0000) >> 16);
        int gValue = ((pixel & 0xff00) >> 8);
        int bValue = ((pixel & 0xff));

        if (rValue > 80 && gValue > 80 && bValue > 80
                && rValue < 110 && gValue < 110 && bValue < 110) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个点或者周围的点是否是白色
     *
     * @param bufferedImage
     * @param x
     * @param y
     * @return
     */
    public static boolean isWhiteAroundPoint(BufferedImage bufferedImage, int x, int y) {
        return isWhitePoint(bufferedImage, x, y)
                || isWhitePoint(bufferedImage, x + 1, y)
                || isWhitePoint(bufferedImage, x - 1, y)
                || isWhitePoint(bufferedImage, x + 1, y + 1)
                || isWhitePoint(bufferedImage, x + 1, y - 1)
                || isWhitePoint(bufferedImage, x, y + 1)
                || isWhitePoint(bufferedImage, x, y - 1)
                || isWhitePoint(bufferedImage, x - 1, y + 1)
                || isWhitePoint(bufferedImage, x + 1, y + 1);
    }

    /**
     * 判断一个点或者周围的点是否是浅白色
     *
     * @param bufferedImage
     * @param x
     * @param y
     * @return
     */
    public static boolean isLightWhiteAroundPoint(BufferedImage bufferedImage, int x, int y) {
        return isLightWhitePoint(bufferedImage, x, y)
                || isLightWhitePoint(bufferedImage, x + 1, y)
                || isLightWhitePoint(bufferedImage, x - 1, y)
                || isLightWhitePoint(bufferedImage, x + 1, y + 1)
                || isLightWhitePoint(bufferedImage, x + 1, y - 1)
                || isLightWhitePoint(bufferedImage, x, y + 1)
                || isLightWhitePoint(bufferedImage, x, y - 1)
                || isLightWhitePoint(bufferedImage, x - 1, y + 1)
                || isLightWhitePoint(bufferedImage, x + 1, y + 1);
    }

    /**
     * 比较从图片文件中获取的点是否跟指定点颜色相同
     * @param bufferedImage
     * @param x
     * @param y
     * @param rgbInfo
     * @return
     */
    public static boolean pointMatchColor(BufferedImage bufferedImage, int x, int y, RgbInfo rgbInfo) {
        RgbInfo rgbInfo1 = new RgbInfo();
        rgbInfo1.loadRGB(bufferedImage, x, y);
        return rgbInfo1.equals(rgbInfo);
    }

    /**
     * 关闭广告
     * @param bufferedImage
     */
    public static boolean touchCloseAdvertisementCross(BufferedImage bufferedImage) {
        // 右上角有白色X，则点击
        if (touchCloseAdvertisementCrossCore(bufferedImage, 965, 182, 951, 980, 168, 197)) {
            executeTouch(965, 182);
            return true;
        }

        // 右上角有浅白色X，则点击中间关闭按钮
        if (touchCloseLightAdvertisementCrossCore(bufferedImage, 965, 182, 951, 980, 168, 197)) {
            executeTouch(100, 1300);
            return true;
        }

        // 左上角有白色X，则点击
        if (touchCloseAdvertisementCrossCore(bufferedImage, 104, 170, 93, 116, 159, 182)) {
            executeTouch(104, 170);
            return true;
        }
        return false;
    }

    private static boolean touchCloseAdvertisementCrossCore(BufferedImage bufferedImage, int middleX, int middleY, int leftX, int rightX, int topY, int bottomY) {
        if (isWhiteAroundPoint(bufferedImage, middleX, middleY)
                && isWhiteAroundPoint(bufferedImage, leftX, topY)
                && isWhiteAroundPoint(bufferedImage, rightX, topY)
                && isWhiteAroundPoint(bufferedImage, leftX, bottomY)
                && isWhiteAroundPoint(bufferedImage, rightX, bottomY)
                && !isWhiteAroundPoint(bufferedImage, leftX, middleY)
                && !isWhiteAroundPoint(bufferedImage, rightX, middleY)
                && !isWhiteAroundPoint(bufferedImage, middleX, topY)
                && !isWhiteAroundPoint(bufferedImage, middleX, bottomY)) {
            return true;
        }
        return false;
    }

    private static boolean touchCloseLightAdvertisementCrossCore(BufferedImage bufferedImage, int middleX, int middleY, int leftX, int rightX, int topY, int bottomY) {
        if (isLightWhiteAroundPoint(bufferedImage, middleX, middleY)
                && isLightWhiteAroundPoint(bufferedImage, leftX, topY)
                && isLightWhiteAroundPoint(bufferedImage, rightX, topY)
                && isLightWhiteAroundPoint(bufferedImage, leftX, bottomY)
                && isLightWhiteAroundPoint(bufferedImage, rightX, bottomY)
                && !isLightWhiteAroundPoint(bufferedImage, leftX, middleY)
                && !isLightWhiteAroundPoint(bufferedImage, rightX, middleY)
                && !isLightWhiteAroundPoint(bufferedImage, middleX, topY)
                && !isLightWhiteAroundPoint(bufferedImage, middleX, bottomY)) {
            return true;
        }
        return false;
    }
}
