package org.emotechamp.app;

public class Emote {
    public String name = "";
    public boolean hasSound = false;
    public String content = null;

    public Emote(String name, boolean hasSound, String content) {
        this.name = name;
        this.hasSound = hasSound;
        this.content = content;
    }
}
