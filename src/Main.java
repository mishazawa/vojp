import net.VoiServer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    SourceDataLine line;
    AudioFormat format = new AudioFormat(44100f, 8, 2, true, true);
    try {
      line = AudioSystem.getSourceDataLine(format);
      line.open(format);
      VoiServer server = new VoiServer(8080, line);
      server.listen();
    } catch (IOException | LineUnavailableException err) {
      err.printStackTrace();
    }
  }
}
