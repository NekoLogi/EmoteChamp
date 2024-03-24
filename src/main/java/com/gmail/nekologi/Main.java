package com.gmail.nekologi;

import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String filePath = null;
        try {
            Html html = new Html(new File(".").getCanonicalPath());
            filePath = html.generate();

            Desktop.getDesktop().browse(new File(filePath).toURI());
        } catch (Exception err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
    }
}