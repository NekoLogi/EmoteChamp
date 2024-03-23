package org.emotechamp.app;

import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String filePath = null;
        try {
            var html = new HtmlGenerator(new File(".").getCanonicalPath());
            filePath = html.generate();

            Desktop.getDesktop().browse(new File(filePath).toURI());
        } catch (Exception err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
    }
}