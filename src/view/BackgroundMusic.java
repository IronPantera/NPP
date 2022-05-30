package view;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {
//    落子音效

    public static class MenuBgm {
        static File music=new File("resource/Menu.wav");
        static AudioInputStream ai= null;
        static Clip clip= null;
        public static void PlayMenuBgm(){
            try {
                ai = AudioSystem.getAudioInputStream(music);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                clip.open(ai);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void Stop(){
            if(ai!=null){
                clip.stop();
            }
        }
    }


    public static class MainMusic {
        static File music=new File("resource/MainMusic.wav");
        static AudioInputStream ai= null;
        static Clip clip= null;
        public static void PlayMainMusic(){
            try {
                ai = AudioSystem.getAudioInputStream(music);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                clip.open(ai);
                clip.start();
                clip.loop(-1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void Stop(){
            if(ai!=null){
                clip.stop();
            }
        }
    }


    public static class ClickSound {
        static File music=new File("resource/Click.wav");
        static AudioInputStream ai= null;
        static Clip clip= null;
        public static void PlayClickSound(){
            try {
                ai = AudioSystem.getAudioInputStream(music);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                clip.open(ai);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void Stop(){
            if(ai!=null){
                clip.stop();
            }
        }
    }
}
