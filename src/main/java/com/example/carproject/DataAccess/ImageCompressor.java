package com.example.carproject.DataAccess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
public class ImageCompressor {

    public static byte[] compressImage(File imageFile, float quality) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        javax.imageio.stream.ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        writer.setOutput(ios);

        javax.imageio.ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(javax.imageio.ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
        writer.dispose();
        ios.close();

        return baos.toByteArray();
    }

    public static Image getImageFromBytes(byte[] imageData) {
        return new Image(new ByteArrayInputStream(imageData));
    }


}
