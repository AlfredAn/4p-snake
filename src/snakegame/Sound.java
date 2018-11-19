package snakegame;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
{
    private Clip clip;
    
    public Sound(String path)
    {
        this(path, 0);
    }
    public Sound(String path, float volume)
    {
        URL url = getClass().getResource(path);
        
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            AudioFormat af = ais.getFormat();
            
            clip = AudioSystem.getClip();
            clip.open(ais);
            
            FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException e)
        {
            System.out.println("Error loading sound at: " + path);
            System.exit(0);
        }
    }
    
    public void play()
    {
        clip.setFramePosition(0);
        clip.start();
    }
    
    public void loop()
    {
        loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void loop(int loopAmount)
    {
        clip.setFramePosition(0);
        clip.loop(loopAmount);
    }
}
