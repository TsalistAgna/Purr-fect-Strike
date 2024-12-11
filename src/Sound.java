import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent;

public class Sound {
    private final URL shoot;
    private final URL hit;
    private final URL dead;

    public Sound() {
        URL tempShoot = null;
        URL tempHit = null;
        URL tempDead = null;
        try {
            tempShoot = new File("Assets\\Sounds\\laser2.wav").toURI().toURL();
            tempHit = new File("Assets\\Sounds\\hit.wav").toURI().toURL();
            tempDead = new File("Assets\\Sounds\\dead.wav").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        shoot = tempShoot;
        hit = tempHit;
        dead = tempDead;
    }

    public void soundShoot(){
        play(shoot);
    }

    public void soundHit(){
        play(hit);
    }

    public void soundDead(){
        play(dead);
    }

    private void play(URL url){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            audioIn.close();
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
