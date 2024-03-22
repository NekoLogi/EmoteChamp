package org.emotechamp.app;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = null;
        try {
            var html = new HtmlGenerator(new File(".").getCanonicalPath());
            filePath =  html.generate();
        } catch (Exception err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        Desktop.getDesktop().browse(new File(filePath).toURI());
        System.out.println();
        System.out.println("Press 'Enter' to close the console");
        System.in.read();
    }
}