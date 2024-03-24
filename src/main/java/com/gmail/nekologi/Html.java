package com.gmail.nekologi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Html {
    private String path = "/assets";

    public Html(String dirPath) throws Exception {
        path = dirPath + path;

        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs())
                throw new Exception("Failed to Create directory: " + path);
        }
    }


    public String generate() throws Exception {
        Header header = new Header(path);
        Body body = new Body(path);

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        for (String content : header.content)
            html.append(content).append("\n");
        html.append(body.content).append("\n");
        html.append("</html>");

        createHtmlFile(html.toString());
        Utils utils = new Utils();
        utils.generateCSS(path);
        utils.generateJS(path);

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
                ArrayList<String> header = new ArrayList<>();
                File data = new File(path);
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
                    "<head>\n" +
                    "<title>Emote Champ</title>\n" +
                    "<link rel=\"stylesheet\" href=\"index.css\"/>\n" +
                    "<script src=\"index.js\"></script>\n" +
                    "</head>\n";
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

        private final String relativePath = "media";
        private String absolutePath = null;

        public Body(String dirPath) throws Exception {
            absolutePath = dirPath + "/" + relativePath;
            System.out.println("You can add emotes and sounds at: " + absolutePath);
            System.out.println("Emotes and sound must have the same name!");

            File dir = new File(absolutePath);
            if (!dir.exists()) {
                if (!dir.mkdirs())
                    throw new Exception("Failed to Create directory: " + absolutePath);
            }
            content = generateBody();
        }

        private String generateBody() {
            StringBuilder builder = new StringBuilder();

            builder.append("<body>\n");
            builder.append("<img id=\"preview\" src=\"\" height=\"500px\" width=\"500px\" onerror=\"stopAudio()\"/><br/>\n");
            builder.append("<button id=\"audio-controller\" type=\"button\" onclick=\"stopAudio()\">Stop Sound</button>\n");
            builder.append("<section id=\"emotes\">\n");
            for (Emote emote : getEmotes())
                builder.append(emote.content);
            builder.append("</section>\n");
            builder.append("</body>\n");

            return builder.toString();
        }

        private Emote[] getEmotes() {
            File folder = new File(absolutePath);
            File[] files = folder.listFiles();

            ArrayList<String> emotes = new ArrayList<String>();
            ArrayList<String> sounds = new ArrayList<String>();
            for (File value : Objects.requireNonNull(files)) {
                String file = value.getName();
                String fileEnding = file.split("\\.")[1];
                switch (fileEnding.toLowerCase()) {
                    case "png":
                    case "jpeg":
                    case "jpg":
                    case "gif":
                    case "svg":
                        emotes.add(file);
                        break;
                    case "mp3":
                    case "wav":
                    case "m4a":
                        sounds.add(file);
                        break;
                }
            }
            ArrayList<Emote> newEmotes = new ArrayList<Emote>();
            for (String emote : emotes) {
                String result = getEmoteSound(emote.split("\\.")[0], sounds.toArray(new String[0]));
                String content = getContent(emote, result);
                newEmotes.add(new Emote(emote, !result.isEmpty(), content));
            }

            return newEmotes.toArray(new Emote[0]);
        }

        private String getContent(String emote, String sound) {
            return "<div class=\"emote-container\" onclick=\"switchEmote(this)\">\n" +
                    String.format("<img class=\"emote\" src=\"%s\" height=\"70px\" width=\"70px\"/>\n", relativePath + "/" + emote) +
                    String.format("<audio class=\"sound\" src=\"%s\"></audio>\n", sound.isEmpty() ? "" : relativePath + "/" + sound) +
                    String.format("<p class=\"emote-title\">%s</p>\n", emote.split("\\.")[0]) +
                    "</div>\n";
        }

        private String getEmoteSound(String emote, String[] sounds) {
            for (String sound : sounds) {
                if (Objects.equals(sound.split("\\.")[0], emote))
                    return sound;
            }
            return "";
        }
    }
}
