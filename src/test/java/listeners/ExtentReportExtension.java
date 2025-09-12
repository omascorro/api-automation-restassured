package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

public class ExtentReportExtension implements TestWatcher, BeforeAllCallback, AfterAllCallback {


    private static final Logger logger = LoggerFactory.getLogger(ExtentReportExtension.class);
    private static ExtentReports extent;
    private static ExtentTest test;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        ExtentManager.clearReportsFolderOnce();

        String className = context.getRequiredTestClass().getSimpleName();
        String reportPath = ExtentManager.getReportPathWithClassName(className);

        extent = ExtentManager.createInstance(reportPath);

    }


    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        TestWatcher.super.testDisabled(context, reason);

        test = extent.createTest(context.getDisplayName());

        String logText = "<b> Test Case: " + context.getDisplayName() + "SKIPPED </b>";

        Markup m = MarkupHelper.createLabel(logText, ExtentColor.AMBER);
        test.skip(m);

    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        TestWatcher.super.testSuccessful(context);

        test = extent.createTest(context.getDisplayName());

        String logText = "<b> Test Case: " + context.getDisplayName() + " PASSED </b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        test.pass(m);

    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        TestWatcher.super.testAborted(context, cause);

        test = extent.createTest(context.getDisplayName());
        String logText = "<b> Test Case: " + context.getDisplayName() + " ABORTED </b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.AMBER);
        test.skip(m);

    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        TestWatcher.super.testFailed(context, cause);

        test = extent.createTest(context.getDisplayName());

        String exceptionMessage = Arrays.toString(cause.getStackTrace());

        test.fail("<details><summary><b><font color=red>Exception ocurred: " +
                "Click to see</font></b></summary>"+
                exceptionMessage.replaceAll(",","</br>")+"</details>");

        String failureLog = "TEST CASE FAILED";
        Markup m = MarkupHelper.createLabel(failureLog,ExtentColor.RED);
        test.log(Status.FAIL,m);

    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

        if(extent!=null){
            extent.flush();
        }
    }



}
