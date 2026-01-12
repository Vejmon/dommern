package no.vejmon.dommern.config;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import uk.org.okapibarcode.backend.HumanReadableLocation;
import uk.org.okapibarcode.backend.QrCode;
import uk.org.okapibarcode.graphics.Color;
import uk.org.okapibarcode.output.Java2DRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class BarcodeGen {

    public BufferedImage qrCode(String data) {
        QrCode qrCode = new QrCode();
        qrCode.setContent(data);
        qrCode.setFontName("Monospaced");
        qrCode.setFontSize(16);
        qrCode.setModuleWidth(2);
        qrCode.setBarHeight(50);
        qrCode.setHumanReadableLocation(HumanReadableLocation.BOTTOM);

        int width = qrCode.getWidth();
        int height = qrCode.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = image.createGraphics();
        Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
        renderer.render(qrCode);
        return image;
    }

    public static byte[] toPngBytes(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            boolean wrote = ImageIO.write(image, "png", os);
            if (!wrote) {
                throw new IOException("No ImageIO writer for PNG");
            }
            return os.toByteArray();
        }
    }

    public static InputStreamSource toByteArrayResource(BufferedImage image) throws IOException {
        byte[] bytes = toPngBytes(image);
        return new ByteArrayResource(bytes);
    }


}
