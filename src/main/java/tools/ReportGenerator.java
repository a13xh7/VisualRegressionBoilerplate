package tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator
{
    private int totalDesktopTests = 0;
    private int totalTabletTests = 0;
    private int totalMobileTests = 0;

    public void generateReport()
    {
        String htmlTemplate = null;

        try {
            htmlTemplate = new String(Files.readAllBytes(Paths.get(TestConfig.PATH_TO_REPORT_TEMPLATE)), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> map = this.generateMenu();
        Map<String, String> errors = this.generateErrorsMenu();

        htmlTemplate = htmlTemplate.replace("@@DESKTOP_MENU@@", errors.get("desktop") + map.get("desktop"));
        htmlTemplate = htmlTemplate.replace("@@TABLET_MENU@@", errors.get("tablet") + map.get("tablet"));
        htmlTemplate = htmlTemplate.replace("@@MOBILE_MENU@@", errors.get("mobile") + map.get("mobile"));

        htmlTemplate = htmlTemplate.replace("@@ERRORS_LOG@@", generateErrorsLog());

        htmlTemplate = htmlTemplate.replace("@@RESULTS@@", this.generateResults());

        htmlTemplate = htmlTemplate.replace("@@TOTAL_DESKTOP@@", Integer.toString(totalDesktopTests));
        htmlTemplate = htmlTemplate.replace("@@TOTAL_TABLET@@", Integer.toString(totalTabletTests));
        htmlTemplate = htmlTemplate.replace("@@TOTAL_MOBILE@@", Integer.toString(totalMobileTests));

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(TestConfig.PATH_TO_OUTPUT_REPORT_FILE, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.println(htmlTemplate);
        writer.close();
    }

    private Map<String, String> generateErrorsMenu()
    {
        String desktopHtml = "";
        String tabletHtml = "";
        String mobileHtml = "";

        // create menu item for errors log

        String pageId;

        File directory = new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS);
        File[] filesList = directory.listFiles();
        Arrays.sort(filesList);
        int id = 0;
        for (File file : filesList)
        {
            if (file.isFile())
            {
                id++;
                pageId = "page" + id;
                String isError = "";

                File errorFile = new File(TestConfig.PATH_TO_DIFF_SCREENSHOTS + file.getName());

                // skip if there isn't diff file
                if(errorFile.exists() == false) {
                   continue;
                }

                isError = "<b class='w3-red'>ERROR: </b>";

                String pageName = file.getName().replace(".png", "");
                pageName = pageName.substring(0, 1).toUpperCase() + pageName.substring(1);


                if(file.getName().contains("1920"))
                {
                    totalDesktopTests++;
                    desktopHtml += String.format("<button style='word-wrap: break-word;  width:100%%;' class='w3-bar-item w3-button w3-left-align tablink' onclick=\"openLink(this, '%s')\">", pageId);
                    desktopHtml += String.format("%s%s", isError, pageName);
                    desktopHtml += "</button>";
                }


                if(file.getName().contains("769") || file.getName().contains("768"))
                {
                    totalTabletTests++;
                    tabletHtml += String.format("<button style='word-wrap: break-word;  width:100%%;' class='w3-bar-item w3-button w3-left-align tablink' onclick=\"openLink(this, '%s')\">", pageId);
                    tabletHtml += String.format("%s%s", isError, pageName);
                    tabletHtml += "</button>";
                }

                if(file.getName().contains("360"))
                {
                    totalMobileTests++;
                    mobileHtml += String.format("<button style='word-wrap: break-word;  width:100%%;' class='w3-bar-item w3-button w3-left-align tablink' onclick=\"openLink(this, '%s')\">", pageId);
                    mobileHtml += String.format("%s%s", isError, pageName);
                    mobileHtml += "</button>";
                }
            }
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("desktop", desktopHtml);
        map.put("tablet", tabletHtml);
        map.put("mobile", mobileHtml);

        return map;
    }

    private Map<String, String> generateMenu()
    {
        String desktopHtml = "";
        String tabletHtml = "";
        String mobileHtml = "";

        // create menu item for errors log

        String pageId;

        File directory = new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS);
        File[] filesList = directory.listFiles();
        Arrays.sort(filesList);
        int id = 0;

        for (File file : filesList)
        {
            if (file.isFile())
            {
                id++;
                pageId = "page" + id;
                String isError = "";

                File errorFile = new File(TestConfig.PATH_TO_DIFF_SCREENSHOTS + file.getName());

                if(errorFile.exists()) {
                   continue;
                }

                String pageName = file.getName().replace(".png", "");
                pageName = pageName.substring(0, 1).toUpperCase() + pageName.substring(1);


                if(file.getName().contains("1920"))
                {
                    totalDesktopTests++;
                    desktopHtml += String.format("<button style='word-wrap: break-word; width:100%%;' class='w3-bar-item w3-button w3-left-align tablink' onclick=\"openLink(this, '%s')\">", pageId);
                    desktopHtml += String.format("%s%s", isError, pageName);
                    desktopHtml += "</button>";
                }


                if(file.getName().contains("769") || file.getName().contains("768"))
                {
                    totalTabletTests++;
                    tabletHtml += String.format("<button style='word-wrap: break-word;  width:100%%;' class='w3-bar-item w3-button w3-left-align tablink' onclick=\"openLink(this, '%s')\">", pageId);
                    tabletHtml += String.format("%s%s", isError, pageName);
                    tabletHtml += "</button>";
                }

                if(file.getName().contains("360"))
                {
                    totalMobileTests++;
                    mobileHtml += String.format("<button style='word-wrap: break-word;  width:100%%;' class='w3-bar-item w3-button w3-left-align tablink' onclick=\"openLink(this, '%s')\">", pageId);
                    mobileHtml += String.format("%s%s", isError, pageName);
                    mobileHtml += "</button>";
                }
            }
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("desktop", desktopHtml);
        map.put("tablet", tabletHtml);
        map.put("mobile", mobileHtml);

        return map;
    }

    private String generateResults()
    {
        String contentHtml = "";
        String pageId;

        File directory = new File(TestConfig.PATH_TO_ACTUAL_SCREENSHOTS);

        File[] filesList = directory.listFiles();
        Arrays.sort(filesList);
        int id = 0;

        for (File file : filesList)
        {
            if (file.isFile())
            {
                id++;
                pageId = "page" + id;

                String gifFilename = file.getName().replace("png", "gif");

                contentHtml += String.format("<div id='%s' class='w3-row w3-animate-right result' style='width: 80%%; margin-left: 20%%; display: none;'>", pageId);
                contentHtml += String.format("<h1 class='w3-center w3-cyan'>%s</h1>", file.getName());
                contentHtml += "<div class='w3-col' style='width:25%; padding:0 5px;'>";
                contentHtml += "<h2 class='w3-center'>EXPECTED</h2>";
                contentHtml += String.format("<img src='../%s%s'>", TestConfig.PATH_TO_EXPECTED_SCREENSHOTS, file.getName());
                contentHtml += "</div>";
                contentHtml += "<div class='w3-col' style='width:25%; padding:0 5px;'>";
                contentHtml += "<h2 class='w3-center'>ACTUAL</h2>";
                contentHtml += String.format("<img src='../%s%s'>", TestConfig.PATH_TO_ACTUAL_SCREENSHOTS, file.getName());
                contentHtml += "</div>";
                contentHtml += "<div class='w3-col' style='width:25%; padding:0 5px;'>";
                contentHtml += "<h2 class='w3-center'>DIFFERENCE</h2>";

                File diffFile = new File(TestConfig.PATH_TO_DIFF_SCREENSHOTS + file.getName());
                if(diffFile.exists()) {
                    contentHtml += String.format("<img src='../%s%s'>", TestConfig.PATH_TO_DIFF_SCREENSHOTS, file.getName());
                }

                contentHtml += "</div>";
                contentHtml += "<div class='w3-col' style='width:25%; padding:0 5px;'>";
                contentHtml += "<h2 class='w3-center'>GIF</h2>";

                File gifFile = new File(TestConfig.PATH_TO_GIF_SCREENSHOTS + gifFilename);
                if(gifFile.exists()) {
                    contentHtml += String.format("<img src='../%s%s'>", TestConfig.PATH_TO_GIF_SCREENSHOTS, gifFilename);
                }

                contentHtml += "</div>";
                contentHtml += "</div>";
            }
        }

        return contentHtml;
    }

    private String generateErrorsLog()
    {
        String errorsLog = "";

        try {
            errorsLog = new String(Files.readAllBytes(Paths.get(TestConfig.PATH_TO_ERRORS_LOG)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contentHtml = "";

        contentHtml += errorsLog;

        return contentHtml;
    }

}
