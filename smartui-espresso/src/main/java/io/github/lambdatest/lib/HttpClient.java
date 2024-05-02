package io.github.lambdatest.lib;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import io.github.lambdatest.SmartUIApp;

public class HttpClient {
    private static final String UPLOAD_API = "https://stage-api.lambdatestinternal.com/visualui/1.0/screenshot/save";

    public void postScreenshot(Map<String, String> screenshotDetails) {

        try {


            URL url = new URL(UPLOAD_API);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream output = con.getOutputStream()) {
                Map<String, String> formFields = new LinkedHashMap<>();
                formFields.put("screenshot", screenshotDetails.get("screenshot"));
                formFields.put("screenshotName", screenshotDetails.get("screenshotName"));
                formFields.put("projectToken", screenshotDetails.get("projectToken"));
                formFields.put("buildId", screenshotDetails.get("buildId"));
                formFields.put("browser", screenshotDetails.get("browser"));
                formFields.put("resolution", screenshotDetails.get("resolution"));
                formFields.put("deviceName", screenshotDetails.get("deviceName"));
                formFields.put("screenshotType",  screenshotDetails.get("screenshotType"));
                formFields.put("os", screenshotDetails.get("os"));
                formFields.put("baseline",screenshotDetails.get("baseline"));
                formFields.put("buildName", screenshotDetails.get("buildName"));
                formFields.put("customCropStatusBar", screenshotDetails.get("customCropStatusBar"));
                formFields.put("customCropNavigationBar", screenshotDetails.get("customCropNavigationBar"));
                formFields.put("cropStatusBar", screenshotDetails.get("cropStatusBar"));
                formFields.put("cropNavigationBar", screenshotDetails.get("cropNavigationBar"));

                for (Map.Entry<String, String> field : formFields.entrySet()) {
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

            SmartUIApp.log("Screenshot posted successfully: " + responseCode, "info");
        } catch (IOException e) {
            SmartUIApp.log("Network error while posting screenshot: " + e.getMessage(), "error");
            e.printStackTrace();
        } catch (Exception e) {
            SmartUIApp.log("Failed to post screenshot: " + e.toString(), "error");
            e.printStackTrace();
        }
    }
}
