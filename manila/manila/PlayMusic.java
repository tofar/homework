package manila;

import javax.swing.*;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class PlayMusic {
    public static AudioClip loadSound(String filename) {
        URL url = null;
        try {
            url = new URL("file:" + filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return JApplet.newAudioClip(url);
    }

    public void play() {
        AudioClip christmas = loadSound("src/music/music1.wav");
        christmas.play();
//        christmas.loop();
    }
}