package entity;

import java.awt.image.BufferedImage;

import static utils.HelperUtils.DEBUG_INFO_SWITCH_ON;

/**
 * 一个像素的r、g、b数据
 */
public class RgbInfo {
    private int rValue;

    private int gValue;

    private int bValue;

    public RgbInfo() {

    }

    public RgbInfo(int r, int g, int b) {
        this.rValue = r;
        this.gValue = g;
        this.bValue = b;
    }

    public int getRValue() {
        return rValue;
    }

    public void setRValue(int rValue) {
        this.rValue = rValue;
    }

    public int getGValue() {
        return gValue;
    }

    public void setGValue(int gValue) {
        this.gValue = gValue;
    }

    public int getBValue() {
        return bValue;
    }

    public void setBValue(int bValue) {
        this.bValue = bValue;
    }

    public void reset() {
        this.rValue = 0;
        this.gValue = 0;
        this.bValue = 0;
    }

    @Override
    public boolean equals(Object obj) {
        RgbInfo target = (RgbInfo) obj;
        return Math.abs(this.getRValue() - target.getRValue()) <= 5
                && Math.abs(this.getGValue() - target.getGValue()) <= 5
                && Math.abs(this.getBValue() - target.getBValue()) <= 5;
    }

    public void loadRGB(BufferedImage bufferedImage, int x, int y) {
        this.reset();
        int pixel = bufferedImage.getRGB(x, y);
        //转换为RGB数字
        this.setRValue((pixel & 0xff0000) >> 16);
        this.setGValue((pixel & 0xff00) >> 8);
        this.setBValue((pixel & 0xff));

        if (DEBUG_INFO_SWITCH_ON) {
            System.out.println("R:" + getRValue());
            System.out.println("G:" + getGValue());
            System.out.println("B:" + getBValue());
        }
    }
}
