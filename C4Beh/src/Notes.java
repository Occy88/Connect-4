
import lejos.nxt.Sound;
public class Notes {
   private static String[] notes = { "C3", "C#3", "Db3", "D3", "D#3", "Eb3",
         "E3", "F3", "F#3", "Gb3", "G3", "G#3", "Ab3", "A3", "A#3", "Bb3",
         "B3", "C4", "C#4", "Db4", "D4", "D#4", "Eb4", "E4", "F4", "F#4",
         "Gb4", "G4", "G#4", "Ab4", "A4", "A#4", "Bb4", "B4", "C5", "C#5",
         "Db5", "D5", "D#5", "Eb5", "E5", "F5", "F#5", "Gb5", "G5", "G#5",
         "Ab5", "A5", "A#5", "Bb5", "B5", "C6" };
   private static float[] freq = { 130.81f, 138.59f, 138.59f, 146.83f,
         155.56f, 155.56f, 164.81f, 174.61f, 185.0f, 185.0f, 196.0f,
         207.65f, 207.65f, 220.0f, 233.08f, 233.08f, 246.94f, 261.63f,
         277.18f, 277.18f, 293.66f, 311.13f, 311.13f, 329.63f, 349.23f,
         369.99f, 369.99f, 392.0f, 415.3f, 415.3f, 440.0f, 466.16f, 466.16f,
         493.88f, 523.25f, 554.37f, 554.37f, 587.33f, 622.25f, 622.25f,
         659.26f, 698.46f, 739.99f, 739.99f, 783.99f, 830.61f, 830.61f,
         880.0f, 932.33f, 932.33f, 987.77f, 1046.5f };
 
    // musicPlay uses the playTone method
    // note is a String of the musical notes above
    // duration is the length of time the music plays in ms. in 60bpm, 1 crochet would last one second, or 1000ms
    
   public void musicPlay(String note, int duration) {
      for (int i = 0; i < notes.length; i++) {
         if(note.equals(notes[i])) {
            Sound.playTone((int)freq[i], duration);
            Sound.pause(duration);
         }
      }
   }
}