package org.emotechamp.app;

import java.io.*;

public class Utils {
    public void generateCSS(String path) throws Exception {
        String content =
                """
                html {
                    user-select: none;
                    background-color: #1f1f1f !important;
                }
                                
                #preview {
                    background-color: blueviolet;
                }
                                
                #emotes {
                    margin-top: 10rem;
                }
                .emote {
                    border: 5px white solid;
                    border-radius: 10px;
                }
                .emote-container {
                    float: left;
                    margin: auto;
                    border: 1px black solid;
                    padding: 1%;
                    background-color: #363636;
                }
                .emote-container:hover {
                    background: rgb(95, 95, 95) !important;
                }
                .emote-title {
                    font-size: 12pt;
                    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
                    color: white !important;
                    text-shadow: black 0px 0px 3px !important;
                    text-align: center;
                }
                                
                #audio-controller {
                    height: 50px;
                    width: 200px;
                    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
                    font-size: 16pt;
                    background-color: darkred !important;
                    color: white !important;
                    margin-top: 5rem;
                }
                #audio-controller:hover {
                    background-color: rgb(170, 0, 0) !important;
                }
                """;
        createFile(path + "/index.css", content);
    }

    public void generateJS(String path) throws Exception {
        String content =
                """
                var lastSelected = null;
                var lastAudio = null;
                                
                function switchEmote(emote) {
                    var preview = document.getElementById("preview");
                    if (preview.src == emote.querySelector("img").src) {
                        preview.src = "default.png";
                        stopAudio();
                    }
                    else {
                        preview.src = emote.querySelector("img").src;
                        playSound(emote);
                    }
                   
                    changeSelectionColor(emote);
                }
                                
                function changeSelectionColor(emote) {
                    var img = emote.querySelector("img");
                    if (lastSelected == null) {
                        lastSelected = emote;
                        img.style.border = "5px #00ff00 solid";
                    }
                    else if (lastSelected == emote) {
                        lastSelected.querySelector("img").style.border = "5px white solid";
                        lastSelected = null;
                    }
                    else {
                        lastSelected.querySelector("img").style.border = "5px white solid";
                        lastSelected = emote;
                        img.style.border = "5px #00ff00 solid";
                    }
                }
                                
                function playSound(emote) {
                    var audio = emote.querySelector("audio");
                                
                    if (lastAudio != null) {
                        lastAudio.pause();
                        lastAudio.currentTime = 0;
                    }
                    audio.play();
                    lastAudio = audio;
                }
                                
                function stopAudio() {
                    if (lastAudio != null) {
                        lastAudio.pause();
                        lastAudio.currentTime = 0;
                        lastAudio = null;
                    }
                }
                """;
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
