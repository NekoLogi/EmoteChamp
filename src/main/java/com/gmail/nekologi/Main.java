package com.gmail.nekologi;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        String filePath = null;
        try {
            Html html = new Html(new File(".").getCanonicalPath());
            filePath = html.generate();
            URI uri = new File(filePath).toURI();

            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
                desktop.browse(uri);
            else {
                StringSelection selection = new StringSelection(uri.toString());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                Thread.sleep(1000);

                System.out.println("EmoteChamp was not able to open the html page " +
                        "and added the URI now to the clipboard.");
                System.out.println(selection);
                System.out.println(uri.toString());
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
    }
}