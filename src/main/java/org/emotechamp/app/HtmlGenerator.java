package org.emotechamp.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class HtmlGenerator {
    private String path = "/assets";

    public HtmlGenerator(String dirPath) throws Exception {
        path = dirPath;

        var dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs())
                throw new Exception("Failed to Create directory: " + path);
        }
    }


    public boolean generate() throws Exception {
        var header = new Header(path);
        var body = new Body(path);

        var html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        for (var content : header.content)
            html.append(content).append("\n");
        for (var content : body.content)
            html.append(content).append("\n");
        html.append("</html>");

        createHtmlFile(html.toString());
        return true;
    }

    private void createHtmlFile(String content) throws Exception {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(content);
            writer.close();
        } catch (Exception err) {
            throw new Exception(err.getMessage());
        }
    }


    private class Header {
        public String[] content = null;

        private String path = "/head.txt";

        public Header(String dirPath) throws Exception {
            path = dirPath + path;

            var dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs())
                    throw new Exception("Failed to Create directory: " + path);
                newHeader();
            }
            content = getHeader();
        }


        private String[] getHeader() {
            try {
                var header = new ArrayList<String>();
                var data = new File(path);
                Scanner myReader = new Scanner(data);
                while (myReader.hasNextLine())
                    header.add(myReader.nextLine());
                myReader.close();
                return header.toArray(new String[0]);
            } catch (FileNotFoundException err) {
                System.out.println(err.getMessage());
                System.out.println(Arrays.toString(err.getStackTrace()));
                return null;
            }
        }

        private void newHeader() throws Exception {
            String content =
                    """
                    <head>
                        <title>Emote Champ</title>
                        <link rel="stylesheet" href="index.css"/>
                        <script src="index.js"></script>
                    </head>
                    """;
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(content);
                writer.close();
            } catch (Exception err) {
                throw new Exception(err.getMessage());
            }
        }
    }

    private class Body {
        public String[] content = null;

        private String path = "/media";

        public Body(String dirPath) throws Exception {
            path = dirPath + path;

            var dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs())
                    throw new Exception("Failed to Create directory: " + path);
            }
        }
    }
}