package no.vejmon.dommern.config;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class BarcodeGenTest {

    @Test
    void testQrCodeBean() {
        BarcodeGen barcodeGen = new BarcodeGen();
        String testData = "test-data";
        BufferedImage qrCodeImage = barcodeGen.qrCode(testData, "Test Caption");
        Font font = new Font("SansSerif", Font.BOLD, 20);
        int textHeight = (int) (font.getLineMetrics("Test Caption",
                new FontRenderContext(null, true, false))
                .getHeight() * 1.3);

        assertNotNull(qrCodeImage);
        assertEquals(BufferedImage.TYPE_BYTE_GRAY, qrCodeImage.getType());

        assertEquals(qrCodeImage.getWidth() + textHeight, qrCodeImage.getHeight());
        assertTrue(qrCodeImage.getWidth() > 10);
    }
}