package com.gmail.nekologi;

import java.io.*;

public class Utils {
    public void generateCSS(String path) throws Exception {
        String content =
                "html {\n" +
                "    user-select: none;\n" +
                "    background-color: transparent !important;\n" +
                "}\n" +
                "\n" +
                "#preview {\n" +
                "    background-color: transparent !important;\n" +
                "    visibility: hidden;\n" +
                "}\n" +
                "\n" +
                "#emotes {\n" +
                "    margin-top: 10rem;\n" +
                "}\n" +
                ".emote {\n" +
                "    border: 5px white solid;\n" +
                "    border-radius: 10px;\n" +
                "}\n" +
                ".emote-container {\n" +
                "    float: left;\n" +
                "    margin: auto;\n" +
                "    border: 1px black solid;\n" +
                "    padding: 1%;\n" +
                "    background-color: #363636;\n" +
                "}\n" +
                ".emote-container:hover {\n" +
                "    background: rgb(95, 95, 95) !important;\n" +
                "}\n" +
                ".emote-title {\n" +
                "    font-size: 12pt;\n" +
                "    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;\n" +
                "    color: white !important;\n" +
                "    text-shadow: black 0px 0px 3px !important;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                "#audio-controller {\n" +
                "    height: 50px;\n" +
                "    width: 200px;\n" +
                "    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;\n" +
                "    font-size: 16pt;\n" +
                "    background-color: darkred !important;\n" +
                "    color: white !important;\n" +
                "    margin-top: 5rem;\n" +
                "}\n" +
                "#audio-controller:hover {\n" +
                "    background-color: rgb(170, 0, 0) !important;\n" +
                "}";
        createFile(path + "/index.css", content);
    }

    public void generateJS(String path) throws Exception {
        String content =
                "var lastSelected = null;\n" +
                        "var lastAudio = null;\n" +
                        "\n" +
                        "function switchEmote(emote) {\n" +
                        "    var preview = document.getElementById(\"preview\");\n" +
                        "    if (preview.src == emote.querySelector(\"img\").src) {\n" +
                        "        preview.src = \"\";\n" +
                        "        preview.style.visibility = \"hidden\";\n" +
                        "        stopAudio();\n" +
                        "    }\n" +
                        "    else {\n" +
                        "        preview.style.visibility = \"visible\";\n" +
                        "        preview.src = emote.querySelector(\"img\").src;\n" +
                        "        playSound(emote);\n" +
                        "    }\n" +
                        "\n" +
                        "    changeSelectionColor(emote);\n" +
                        "}\n" +
                        "\n" +
                        "function changeSelectionColor(emote) {\n" +
                        "    var img = emote.querySelector(\"img\");\n" +
                        "    if (lastSelected == null) {\n" +
                        "        lastSelected = emote;\n" +
                        "        img.style.border = \"5px #00ff00 solid\";\n" +
                        "    }\n" +
                        "    else if (lastSelected == emote) {\n" +
                        "        lastSelected.querySelector(\"img\").style.border = \"5px white solid\";\n" +
                        "        lastSelected = null;\n" +
                        "    }\n" +
                        "    else {\n" +
                        "        lastSelected.querySelector(\"img\").style.border = \"5px white solid\";\n" +
                        "        lastSelected = emote;\n" +
                        "        img.style.border = \"5px #00ff00 solid\";\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "function playSound(emote) {\n" +
                        "    var audio = emote.querySelector(\"audio\");\n" +
                        "\n" +
                        "    if (lastAudio != null) {\n" +
                        "        lastAudio.pause();\n" +
                        "        lastAudio.currentTime = 0;\n" +
                        "    }\n" +
                        "    audio.play();\n" +
                        "    lastAudio = audio;\n" +
                        "}\n" +
                        "\n" +
                        "function stopAudio() {\n" +
                        "    if (lastAudio != null) {\n" +
                        "        lastAudio.pause();\n" +
                        "        lastAudio.currentTime = 0;\n" +
                        "        lastAudio = null;\n" +
                        "    }\n" +
                        "}";
        createFile(path + "/index.js", content);
    }

    private static void createFile(String path, String content) throws Exception {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(content);
            writer.close();
        } catch (Exception err) {
            throw new Exception(err.getMessage());
        }
    }
}
