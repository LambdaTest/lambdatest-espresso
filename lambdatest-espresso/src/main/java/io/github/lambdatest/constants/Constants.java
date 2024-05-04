package io.github.lambdatest.constants;

public class Constants {


    public interface ApiConstants {
        String UPLOAD_API = "https://api.lambdatest.com/visualui/1.0/screenshot/save";
        String REAL_UPLOAD_DEVICE_API = "https://mobile-api.lambdatest.com/framework/v1/espresso/screenshot";
    }

    public interface KeyConstants {

        String screenshotName = "screenshotName";
        String screenshotType = "screenshotType";
        String projectToken = "projectToken";
        String buildName = "buildName";
        String buildId = "buildId";
        String deviceName = "deviceName";
        String resolution = "resolution";
        String os = "os";
        String baseline = "baseline";
        String browser = "browser";
        String cropNavigationBar = "cropNavigationBar";
        String cropStatusBar = "cropStatusBar";
        String customCropStatusBar = "customCropStatusBar";
        String customCropNavigationBar = "customCropNavigationBar";

        String visual = "visual";

        // real device screenshot keys
        String rdBuildId = "rdBuildId";
        String deviceId = "deviceId";

        String testId = "testId";
        String orgId = "orgId";
        String android = "android";

        String isAppAutomation = "isAppAutomation";
        String screenshotId = "screenshotId";
        String screenshotHost = "screenshotHost";

    }


}