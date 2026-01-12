package no.vejmon.dommern.config;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import uk.org.okapibarcode.backend.QrCode;
import uk.org.okapibarcode.graphics.Color;
import uk.org.okapibarcode.output.Java2DRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class BarcodeGen {

    public BufferedImage qrCode(String data, String caption) {
        QrCode qrCode = new QrCode();
        qrCode.setContent(data);
        Font font = new Font("SansSerif", Font.BOLD, 20);
        int textHeight = (int) (font.getLineMetrics(caption,
                new FontRenderContext(null, true, false))
                .getHeight() * 1.3);

        int magnification = 4;
        int width = qrCode.getWidth() * magnification;
        int height = (qrCode.getHeight() * magnification) + textHeight;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = image.createGraphics();
        Java2DRenderer renderer = new Java2DRenderer(g2d, magnification, Color.WHITE, Color.BLACK);
        renderer.render(qrCode);

        g2d.setColor(java.awt.Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(caption);

        int x = (width - textWidth) / 2;
        int y = width + fm.getAscent();

        g2d.drawString(caption, x, y);
        g2d.dispose();

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
