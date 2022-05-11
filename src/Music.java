import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private Clip clip;
    private AudioInputStream sound;
    private File file;

    public Music(String soundFileName){
        this.file = new File(soundFileName);
    }

    public void setFile(String soundFileName) {
        try {
            file = new File(soundFileName);
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
        }
    }

    public void play() {
        try {
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
        }
        clip.start();
    }

    public void stop() throws IOException {
        sound.close();
        clip.close();
        clip.stop();
    }
}