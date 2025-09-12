package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;


    public static ExtentReports createInstance(String fileName){

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);

        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Organization","QualityStream");

        return extent;

    }

    public static String getReportPathWithClassName(String className){

        String baseDir = System.getProperty("user.dir");
        String reportDir = baseDir + "/reports";

        File directory = new File(reportDir);
        if(!directory.exists()){
            directory.mkdir();
        }

        return reportDir + "/" + className + "_" + new Date().getTime() + ".html";
    }

    private static boolean alreadyCleared = false;
    public static void clearReportsFolderOnce(){

        if(!alreadyCleared){

            File folder = new File(System.getProperty("user.dir")+"\\reports\\");

            if(folder.exists() && folder.isDirectory()){
                for (File file : folder.listFiles()){
                    file.delete();
                }
            }

            alreadyCleared = true;
        }

    }


}
