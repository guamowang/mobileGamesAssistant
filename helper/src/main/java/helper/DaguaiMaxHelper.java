package helper;

import entity.RgbInfo;
import utils.HelperUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 我的斧头会变长辅助
 */
public class DaguaiMaxHelper {
    private static final String IMAGE_NAME = "daguai.png";
    private static final RgbInfo RED_EYE = new RgbInfo(255, 0, 0);

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        while (true) {
            // 执行ADB命令，获取截图，上传到电脑并打开
            HelperUtils.screenshotAndUpload(IMAGE_NAME);
            // 读取截图文件
            BufferedImage bufferedImage = ImageIO.read(HelperUtils.getScreenshotFile(IMAGE_NAME));

            if (HelperUtils.pointMatchColor(bufferedImage, 560, 465, RED_EYE)) {
                HelperUtils.executeTouch(60, 370); // 停止点击
                Thread.sleep(100);
                HelperUtils.executeTouch(900, 1700); // 点击转生
                Thread.sleep(110);
                HelperUtils.executeTouch(700, 1700); // 决定了
                Thread.sleep(2000);
                HelperUtils.executeTouch(100, 2200); // 我
                Thread.sleep(120);
                HelperUtils.executeTouch(60, 370); // 开始点击
            }

            Thread.sleep(2000);
        }
    }
}