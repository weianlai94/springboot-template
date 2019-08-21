package com.example.demo.common.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QrCodeUtil {
    public static void main(String[] args) {
        String url = "http://baidu.com";
        createQrCode(url);
    }

    public static String createQrCode(String url) {
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, "1");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
            return writeToFile(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static String writeToFile(BitMatrix matrix) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        InputStream is = null;
        image.flush();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut = null;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(image, "png", imOut);
            is = new ByteArrayInputStream(bs.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imOut.close();
        String FILE_PATH = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/SSS/").format(new Date());
        String FILE_NAME = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        return AbstractOssClientUtil.putObject(is, FILE_PATH, FILE_NAME, (long) bs.size());
    }

    static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}