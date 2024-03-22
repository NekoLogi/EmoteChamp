package org.emotechamp.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class HtmlGenerator {
    private String path = "/assets";

    public HtmlGenerator(String dirPath) throws Exception {
        path = dirPath + path;

        var dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs())
                throw new Exception("Failed to Create directory: " + path);
        }
    }


    public String generate() throws Exception {
        var header = new Header(path);
        var body = new Body(path);

        var html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        for (var content : header.content)
            html.append(content).append("\n");
        html.append(body.content).append("\n");
        html.append("</html>");

        createHtmlFile(html.toString());
        var utils = new Utils();
        utils.generateCSS(path);
        utils.generateJS(path);
        if (!new File(path + "/default.png").exists()) {
            System.out.println();
            System.out.println("'default.png' doesn't exist in :" + path);
            System.out.println("If you want a greenscreen, add a png into this path and name it 'default.png'");
            System.out.println();
        }

        return path + "/index.html";
    }

    private void createHtmlFile(String content) throws Exception {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/index.html"));
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

            newHeader();
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
        public String content = null;

        private String path = "/media";

        public Body(String dirPath) throws Exception {
            path = dirPath + path;
            System.out.println("You can add emotes and sounds at: " + path);
            System.out.println("Emotes and sound must have the same name!");

            var dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs())
                    throw new Exception("Failed to Create directory: " + path);
            }
            content = generateBody();
        }

        private String generateBody() {
            var builder = new StringBuilder();

            builder.append("<body>\n");
            builder.append("<img id=\"preview\" src=\"default.png\" height=\"500px\" width=\"500px\" onerror=\"stopAudio()\"/><br/>\n");
            builder.append("<button id=\"audio-controller\" type=\"button\" onclick=\"stopAudio()\">Stop Sound</button>\n");
            builder.append("<section id=\"emotes\">\n");
            for (var emote : getEmotes())
                builder.append(emote.content);
            builder.append("</section>\n");
            builder.append("</body>\n");

            return builder.toString();
        }

        private Emote[] getEmotes() {
            File folder = new File(path);
            File[] files = folder.listFiles();

            var emotes = new ArrayList<String>();
            var sounds = new ArrayList<String>();
            for (File value : Objects.requireNonNull(files)) {
                String file = value.getName();
                String fileEnding = file.split("\\.")[1];
                switch (fileEnding.toLowerCase()) {
                    case "png", "jpeg", "jpg", "gif", "svg":
                        emotes.add(file);
                        break;
                    case "mp3", "wav", "m4a":
                        sounds.add(file);
                        break;
                }
            }
            var newEmotes = new ArrayList<Emote>();
            for (var emote : emotes) {
                String result = getEmoteSound(emote.split("\\.")[0], sounds.toArray(new String[0]));
                var content = getContent(emote, result);
                newEmotes.add(new Emote(emote, !result.isEmpty(), content));
            }

            return newEmotes.toArray(new Emote[0]);
        }

        private String getContent(String emote, String sound) {
            return "<div class=\"emote-container\" onclick=\"switchEmote(this)\">\n" +
                    String.format("<img class=\"emote\" src=\"%s\" height=\"70px\" width=\"70px\"/>\n", path + "/" + emote) +
                    String.format("<audio class=\"sound\" src=\"%s\"></audio>\n", sound.isEmpty() ? "" : path + "/" + sound) +
                    String.format("<p class=\"emote-title\">%s</p>\n", emote.split("\\.")[0]) +
                    "</div>\n";
        }

        private String getEmoteSound(String emote, String[] sounds) {
            for (var sound : sounds) {
                if (Objects.equals(sound.split("\\.")[0], emote))
                    return sound;
            }
            return "";
        }
    }
}