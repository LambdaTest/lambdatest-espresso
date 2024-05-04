package io.github.lambdatest.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gson.Gson;
import io.github.lambdatest.constants.Constants;
import io.github.lambdatest.LTApp;

public class HttpClient {

    public String postScreenshot(Map < String, String > screenshotDetails) {

        try {

            URL url = new URL(Constants.ApiConstants.UPLOAD_API);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream output = con.getOutputStream()) {
                Map < String, String > formFields = new LinkedHashMap < > ();
                formFields.put("screenshot", screenshotDetails.get("screenshot"));
                formFields.put(Constants.KeyConstants.screenshotName, screenshotDetails.get(Constants.KeyConstants.screenshotName));
                formFields.put(Constants.KeyConstants.projectToken, screenshotDetails.get(Constants.KeyConstants.projectToken));
                formFields.put(Constants.KeyConstants.buildId, screenshotDetails.get(Constants.KeyConstants.buildId));
                formFields.put(Constants.KeyConstants.browser, screenshotDetails.get(Constants.KeyConstants.browser));
                formFields.put(Constants.KeyConstants.resolution, screenshotDetails.get(Constants.KeyConstants.resolution));
                formFields.put(Constants.KeyConstants.deviceName, screenshotDetails.get(Constants.KeyConstants.deviceName));
                formFields.put(Constants.KeyConstants.screenshotType, screenshotDetails.get(Constants.KeyConstants.screenshotType));
                formFields.put(Constants.KeyConstants.os, screenshotDetails.get(Constants.KeyConstants.os));
                formFields.put(Constants.KeyConstants.baseline, screenshotDetails.get(Constants.KeyConstants.baseline));
                formFields.put(Constants.KeyConstants.buildName, screenshotDetails.get(Constants.KeyConstants.buildName));
                formFields.put(Constants.KeyConstants.customCropStatusBar, screenshotDetails.get(Constants.KeyConstants.customCropStatusBar));
                formFields.put(Constants.KeyConstants.customCropNavigationBar, screenshotDetails.get(Constants.KeyConstants.customCropNavigationBar));
                formFields.put(Constants.KeyConstants.cropStatusBar, screenshotDetails.get(Constants.KeyConstants.cropStatusBar));
                formFields.put(Constants.KeyConstants.cropNavigationBar, screenshotDetails.get(Constants.KeyConstants.cropNavigationBar));

                for (Map.Entry < String, String > field: formFields.entrySet()) {
                    output.write(("--" + boundary + "\r\n").getBytes());
                    output.write(("Content-Disposition: form-data; name=\"" + field.getKey() + "\"\r\n\r\n").getBytes());
                    output.write((field.getValue() + "\r\n").getBytes());
                }
                output.write(("--" + boundary + "--\r\n").getBytes());
            }

            int responseCode = con.getResponseCode();
            if (responseCode < HttpURLConnection.HTTP_OK || responseCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
                throw new IOException("Unexpected response code for post Screenshot: " + responseCode);
            }



            LTApp.log("Screenshot posted successfully: " + responseCode, "info");

            return String.valueOf(responseCode);

        } catch (IOException e) {
            LTApp.log("Network error while posting screenshot: " + e.getMessage(), "error");
        } catch (Exception e) {
            LTApp.log("Failed to post screenshot: " + e.toString(), "error");
        }

        return null;
    }

    public String postRealDeviceScreenshot(Map < String, Object > realDeviceScreenshotDetails) {
        try {
            URL url = new URL(Constants.ApiConstants.REAL_UPLOAD_DEVICE_API);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");



            Gson gson = new Gson();
            String jsonInputString = gson.toJson(realDeviceScreenshotDetails);

            try (OutputStream output = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                output.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode < HttpURLConnection.HTTP_OK || responseCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
                throw new IOException("Unexpected response code for posting real device screenshot: " + responseCode);
            }

            LTApp.log("Real device screenshot posted successfully: " + responseCode, "info");

            return String.valueOf(responseCode);

        } catch (IOException e) {
            LTApp.log("Network error while posting real device screenshot: " + e.getMessage(), "error");
        } catch (Exception e) {
            LTApp.log("Failed to post real device screenshot: " + e.toString(), "error");
        }

        return null;
    }


}