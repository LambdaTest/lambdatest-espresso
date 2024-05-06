package io.github.lambdatest;

import androidx.test.platform.app.InstrumentationRegistry;
import io.github.lambdatest.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import io.github.lambdatest.constants.Constants;

public class LTApp {

    public static String LOGLEVEL = Constants.KeyConstants.info;
    private static boolean DEBUG = LOGLEVEL.equals(Constants.KeyConstants.debug);
    private static String LABEL = "[\u001b[35m" + ("lambdatest-espresso") + "\u001b[39m]";
    public static Boolean ignoreErrors = true;
    private final Utils utils;
    public LTApp() {
        this.utils = new Utils();
    }
    public String screenshot(String name) {
        return screenshot(name, "", "");
    }

    public String screenshot(String name, String customCropStatusBar, String customCropNavigationBar) {
        try {

            Map <String, String> screenshotDetails = new HashMap < > ();
            screenshotDetails.put(Constants.KeyConstants.screenshotName, name);
            screenshotDetails.put(Constants.KeyConstants.screenshotType, "lambdatest-espresso-java");
            screenshotDetails.put(Constants.KeyConstants.projectToken, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.projectToken));
            screenshotDetails.put(Constants.KeyConstants.buildName, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.buildName));
            screenshotDetails.put(Constants.KeyConstants.buildId, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.buildId));
            screenshotDetails.put(Constants.KeyConstants.deviceName, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.deviceName));
            screenshotDetails.put(Constants.KeyConstants.resolution, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.resolution));
            screenshotDetails.put(Constants.KeyConstants.os, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.os));
            screenshotDetails.put(Constants.KeyConstants.baseline, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.baseline));
            screenshotDetails.put(Constants.KeyConstants.browser, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.browser));
            screenshotDetails.put(Constants.KeyConstants.cropNavigationBar, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.cropNavigationBar));
            screenshotDetails.put(Constants.KeyConstants.cropStatusBar, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.cropStatusBar));
            screenshotDetails.put(Constants.KeyConstants.customCropStatusBar, customCropStatusBar);
            screenshotDetails.put(Constants.KeyConstants.customCropNavigationBar, customCropNavigationBar);

            // Checking for visual flag
            String visualStr = InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.visual);
            boolean visual = "true".equalsIgnoreCase(visualStr);
            String response = utils.screenshot(screenshotDetails);

            if (visual) {

                Map <String, Object > realDeviceScreenshotDetails = new HashMap <> ();
                realDeviceScreenshotDetails.put(Constants.KeyConstants.rdBuildId, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.rdBuildId));
                realDeviceScreenshotDetails.put(Constants.KeyConstants.deviceidCons, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.deviceId));
                realDeviceScreenshotDetails.put(Constants.KeyConstants.testId, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.testId));
                realDeviceScreenshotDetails.put(Constants.KeyConstants.orgId, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.orgId));
                realDeviceScreenshotDetails.put(Constants.KeyConstants.os, Constants.KeyConstants.android);
                realDeviceScreenshotDetails.put(Constants.KeyConstants.isAppAutomation, true);
                realDeviceScreenshotDetails.put(Constants.KeyConstants.screenshotId, name + "-" + UUID.randomUUID().toString());
                realDeviceScreenshotDetails.put(Constants.KeyConstants.url, InstrumentationRegistry.getArguments().getString(Constants.KeyConstants.screenshotHost));
                response = utils.realDeviceScreenshot(realDeviceScreenshotDetails);

            }
            return response;

        } catch (Exception e) {
            log("Error taking screenshot " + name);
            log(e.toString(), Constants.KeyConstants.error);
            if (!ignoreErrors) {
                throw new RuntimeException("Error taking screenshot " + name, e);
            }
        }
        return null;
    }

    public static void log(String message) {
        log(message, Constants.KeyConstants.info);
    }

    public static void log(String message, String logLevel) {
        if (logLevel == Constants.KeyConstants.debug && DEBUG) {
            System.out.println(LABEL + " " + message);
        } else if (logLevel == Constants.KeyConstants.info) {
            System.out.println(LABEL + " " + message);
        }
    }

}