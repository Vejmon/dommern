package no.vejmon.dommern.config;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class BarcodeGenTest {

    @Test
    void testQrCodeBean() {
        BarcodeGen barcodeGen = new BarcodeGen();
        String testData = "test-data";
        BufferedImage qrCodeImage = barcodeGen.qrCode(testData);

        assertNotNull(qrCodeImage);
        assertEquals(BufferedImage.TYPE_BYTE_GRAY, qrCodeImage.getType());
        // image should be square
        assertEquals(qrCodeImage.getHeight(), qrCodeImage.getWidth());
        assertTrue(qrCodeImage.getHeight() > 10);
    }
}