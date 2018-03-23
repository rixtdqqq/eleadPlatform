package com.xys.libzxing.zxing.encoding;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.left;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class ColorQRCodeWriter {
    private static final int QUIET_ZONE_SIZE = 4;

    public Map<String, Object> encode(String contents, BarcodeFormat format, int width, int height)
            throws WriterException {
        return encode(contents, format, width, height, null);
    }

    public static Map<String, Object> encode(String contents,
                                      BarcodeFormat format,
                                      int width,
                                      int height,
                                      Map<EncodeHintType, ?> hints) throws WriterException {

        if (contents.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }

        if (format != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
        }

        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' +
                    height);
        }

        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.H;
        int quietZone = QUIET_ZONE_SIZE;
        if (hints != null) {
            ErrorCorrectionLevel requestedECLevel = (ErrorCorrectionLevel) hints.get(EncodeHintType.ERROR_CORRECTION);
            if (requestedECLevel != null) {
                errorCorrectionLevel = requestedECLevel;
            }
            Integer quietZoneInt = (Integer) hints.get(EncodeHintType.MARGIN);
            if (quietZoneInt != null) {
                quietZone = quietZoneInt;
            }
        }

        QRCode code = Encoder.encode(contents, errorCorrectionLevel, hints);
        return renderResult(code, width, height, quietZone);
    }

    // Note that the input matrix uses 0 == white, 1 == black, while the output matrix uses
    // 0 == black, 255 == white (i.e. an 8 bit greyscale bitmap).
    private static Map<String, Object> renderResult(QRCode code, int width, int height, int quietZone) {
        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (quietZone * 2);
        int qrHeight = inputHeight + (quietZone * 2);
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        // Padding includes both the quiet zone and the extra white pixels to accommodate the requested
        // dimensions. For example, if input is 25x25 the QR will be 33x33 including the quiet zone.
        // If the requested size is 200x160, the multiple will be 4, for a QR of 132x132. These will
        // handle all the padding from 100x100 (the actual QR) up to 200x160.
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;

        BitMatrix output = new BitMatrix(outputWidth, outputHeight);

        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            // Write the contents of this row of the barcode
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if (input.get(inputX, inputY) == 1) {
                    output.setRegion(outputX, outputY, multiple, multiple);
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("BitMatrix", output);
        map.put("Version", code.getVersion());
        return map;
    }

    public Bitmap encodeBitmap(String content, int width, int height, boolean all) {
        return encodeBitmap(content, width, height, null, null, null, all);
    }

    /**
     * 生成基点颜色不同的图片
     *
     * @param content         需要生成的二维码的内容
     * @param width           二维码宽
     * @param height          二维码高
     * @param topLeftColor    左基点颜色
     * @param topRightColor   右顶基点颜色
     * @param bottomLeftColor 左底基点颜色
     * @return
     */
    public static Bitmap encodeBitmap(String content, int width, int height,
                               Integer topLeftColor, Integer topRightColor, Integer bottomLeftColor, boolean all) {
        try {
            int startModel = 2;
            int endModel = 5;
            if (all) {
                startModel = 0;
                endModel = 7;
            }
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);
            Map<String, Object> map =encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BitMatrix matrix = (BitMatrix) map.get("BitMatrix");
            Version version = (Version) map.get("Version");
            int[] tl = matrix.getTopLeftOnBit();
            int totalModelNum = (version.getVersionNumber() - 1) * 4 + 5 + 16;    //获取单边模块数
            int resultWidth = width - 2 * (tl[0]);
            int modelWidth = resultWidth / totalModelNum;   //得到每个模块长度
            //得到三个基准点的起始与终点
            int topEndX = tl[0] + modelWidth * endModel;
            int topStartX = tl[0] + modelWidth * startModel;
            int topStartY = tl[0] + modelWidth * startModel;
            int topEndY = tl[0] + modelWidth * endModel;
            int rightStartX = (totalModelNum - endModel) * modelWidth + tl[0];
            int rightEndX = width - modelWidth * startModel - tl[0];
            int leftStartY = height - modelWidth * endModel - tl[1];
            int leftEndY = height - modelWidth * startModel - tl[1];
            int[] pixels = new int[width * height];
            for (int y = 0; y < matrix.getHeight(); y++) {
                for (int x = 0; x < matrix.getWidth(); x++) {
                    if (x >= topStartX && x < topEndX && y >= topStartY && y < topEndY) {
                        //左上角颜色
                        if (topLeftColor == null) {
                            topLeftColor = Color.GRAY;
                        }
                        pixels[y * width + x] = matrix.get(x, y) ? topLeftColor : Color.WHITE;
                    } else if (x < rightEndX && x >= rightStartX && y >= topStartY && y < topEndY) {
                        //右上角颜色
                        if (topRightColor == null) {
                            topRightColor = Color.GRAY;
                        }
                        pixels[y * width + x] = matrix.get(x, y) ? topRightColor : Color.WHITE;
                    } else if (x >= topStartX && x < topEndX && y >= leftStartY && y < leftEndY) {
                        //右下角颜色
                        if (bottomLeftColor == null) {
                            bottomLeftColor = Color.GRAY;
                        }
                        pixels[y * width + x] = matrix.get(x, y) ? bottomLeftColor : Color.WHITE;
                    } else {
                        pixels[y * width + x] = matrix.get(x, y) ? Color.BLACK : Color.WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 在二维码中间添加Logo图案
     */
    public Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    /**
     * 给二维码图片加背景
     */
    public static Bitmap addBackground(Bitmap foreground, Bitmap background, ImageView iv) {
        Rect rect=new Rect();
        Paint paint=new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        int leftL=iv.getLeft();
        int right=iv.getRight()+fgWidth+left;
        int top=iv.getTop();
        int bottom=iv.getTop()+fgHeight;

        rect.left=leftL;
        rect.right=right;
        rect.top=top;
        rect.bottom=bottom;

        Bitmap newmap = Bitmap
                .createBitmap(fgWidth,fgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
       // canvas.drawBitmap(background, 0, 0, null);
        canvas.drawRect(rect,paint);
        canvas.drawBitmap(foreground, fgWidth,
                fgHeight, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }


}
