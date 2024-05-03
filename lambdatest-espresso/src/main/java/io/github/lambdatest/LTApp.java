package io.github.lambdatest;

import androidx.test.platform.app.InstrumentationRegistry;
import io.github.lambdatest.lib.HttpClient;
import io.github.lambdatest.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LTApp {

    private HttpClient httpClient;
    public static String SMARTUI_LOGLEVEL = "info";
    private static boolean SMARTUI_DEBUG = SMARTUI_LOGLEVEL.equals("debug");
    private static String LABEL = "[\u001b[35m" + (SMARTUI_DEBUG ? "smartui:java" : "smartui") + "\u001b[39m]";
    public static Boolean ignoreErrors = true;
    private static final String REAL_DEVICE_API = "http://10.150.0.47:5652/v1.0/capturescreenshot";


    public LTApp() {
        this.httpClient = new HttpClient();
    }

    public String screenshot(String name) {
        String response = screenshot(name, "", "");
        return response;
    }

    public String screenshot(String name, String customCropStatusBar, String customCropNavigationBar) {
        try {
            Utils utils = new Utils();

            Map<String, String> screenshotDetails = new HashMap<>();
            screenshotDetails.put("screenshotName", name);
            screenshotDetails.put("screenshotType", "lambdatest-espresso-java");
            screenshotDetails.put("projectToken", InstrumentationRegistry.getArguments().getString("projectToken"));
            screenshotDetails.put("buildName", InstrumentationRegistry.getArguments().getString("buildName"));
            screenshotDetails.put("buildId", InstrumentationRegistry.getArguments().getString("buildId"));
            screenshotDetails.put("deviceName", InstrumentationRegistry.getArguments().getString("deviceName"));
            screenshotDetails.put("resolution", InstrumentationRegistry.getArguments().getString("resolution"));
            screenshotDetails.put("os", InstrumentationRegistry.getArguments().getString("os"));
            screenshotDetails.put("baseline", InstrumentationRegistry.getArguments().getString("baseline"));
            screenshotDetails.put("browser", InstrumentationRegistry.getArguments().getString("browser"));
            screenshotDetails.put("cropNavigationBar", InstrumentationRegistry.getArguments().getString("cropNavigationBar"));
            screenshotDetails.put("cropStatusBar", InstrumentationRegistry.getArguments().getString("cropStatusBar"));
            screenshotDetails.put("customCropStatusBar", customCropStatusBar);
            screenshotDetails.put("customCropNavigationBar", customCropNavigationBar);

            // Checking for visual flag
            String visualStr = InstrumentationRegistry.getArguments().getString("visual");
            boolean visual = "true".equalsIgnoreCase(visualStr);



            String response = utils.screenshot(screenshotDetails);

            if(visual){

                Map<String, Object> realDeviceScreenshotDetails = new HashMap<>();
                realDeviceScreenshotDetails.put("rdBuildId", InstrumentationRegistry.getArguments().getString("rdBuildId"));
                realDeviceScreenshotDetails.put("deviceid", InstrumentationRegistry.getArguments().getString("deviceId"));
                realDeviceScreenshotDetails.put("testId", InstrumentationRegistry.getArguments().getString("testId"));
                realDeviceScreenshotDetails.put("orgId", InstrumentationRegistry.getArguments().getString("orgId"));
                realDeviceScreenshotDetails.put("os", "android");
                realDeviceScreenshotDetails.put("env", "stage");
                realDeviceScreenshotDetails.put("isAppAutomation", true);
                realDeviceScreenshotDetails.put("screenshotId", UUID.randomUUID().toString());
                realDeviceScreenshotDetails.put("url", REAL_DEVICE_API);

                response = utils.realDeviceScreenshot(realDeviceScreenshotDetails);

            }

            return response;

        } catch (Exception e) {
            log("Error taking screenshot " + name);
            log(e.toString(), "debug");
            if (!ignoreErrors) {
                throw new RuntimeException("Error taking screenshot " + name, e);
            }
        }
        return null;
    }

    public static void log(String message) {
        log(message, "info");
    }

    public static void log(String message, String logLevel) {
        if (logLevel == "debug" && SMARTUI_DEBUG) {
            System.out.println(LABEL + " " + message);
        } else if (logLevel == "info") {
            System.out.println(LABEL + " " + message);
        }
    }

}
