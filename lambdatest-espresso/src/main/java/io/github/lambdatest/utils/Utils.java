package io.github.lambdatest.utils;

import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.util.Base64;
import androidx.test.runner.screenshot.Screenshot;
import io.github.lambdatest.client.HttpClient;
import java.util.Map;


public class Utils {
    private final HttpClient httpClient;

    public Utils() {
        this.httpClient = new HttpClient();
    }

    public String screenshot(Map <String, String> screenshotDetails) {

        Bitmap bitmap = Screenshot.capture().getBitmap();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        String content = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        screenshotDetails.put("screenshot", content);
        return httpClient.postScreenshot(screenshotDetails);
    }

    public String realDeviceScreenshot(Map <String, Object> realDeviceScreenshotDetails) {
        return httpClient.postRealDeviceScreenshot(realDeviceScreenshotDetails);
    }
}