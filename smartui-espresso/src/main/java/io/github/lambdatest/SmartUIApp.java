package io.github.lambdatest;

import androidx.test.platform.app.InstrumentationRegistry;
import io.github.lambdatest.lib.HttpClient;
import io.github.lambdatest.utils.Utils;
import java.util.HashMap;
import java.util.Map;

public class SmartUIApp {

    private HttpClient httpClient;
    public static String SMARTUI_LOGLEVEL = "info";
    private static boolean SMARTUI_DEBUG = SMARTUI_LOGLEVEL.equals("debug");
    private static String LABEL = "[\u001b[35m" + (SMARTUI_DEBUG ? "smartui:java" : "smartui") + "\u001b[39m]";
    public static Boolean ignoreErrors = true;

    public SmartUIApp() {
        this.httpClient = new HttpClient();
    }

    public void screenshot(String name) {
        screenshot(name, "", ""); // Defaults for status bar and navigation bar cropping
    }

    public void screenshot(String name, String customCropStatusBar, String customCropNavigationBar) {
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



            utils.screenshot(screenshotDetails);

        } catch (Exception e) {
            log("Error taking screenshot " + name);
            log(e.toString(), "debug");
            if (!ignoreErrors) {
                throw new RuntimeException("Error taking screenshot " + name, e);
            }
        }
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
