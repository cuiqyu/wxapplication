package com.thinkgem.jeesite.modules.wx.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;

/**
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，
 * 可以将该类直接拷贝到源码中使用，当然你也可以自己写个生产条形码的基类
 */
public class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;//用于设置图案的颜色
    private static final int WHITE = 0xFFFFFFFF; //用于背景色

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? BLACK : WHITE));
                //image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file, String logUri) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        image = logoConfig.setMatrixLogo(image, logUri);

        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream, String logUri)
        throws IOException {

        BufferedImage image = toBufferedImage(matrix);

        //设置logo图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        image = logoConfig.setMatrixLogo(image, logUri);

        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
}