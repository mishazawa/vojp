package net;

import javax.sound.sampled.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.Const.FORMAT;

public class VoiClient {

  public static void main (String[] argues) {
    DataLine.Info info = new DataLine.Info(TargetDataLine.class, FORMAT);
    DataLine.Info speakersInfo = new DataLine.Info(SourceDataLine.class, FORMAT);

    if (!AudioSystem.isLineSupported(info)) {
      System.out.println("Line is not supported.");
      return;
    }
    try {
      Socket socket = new Socket("localhost", 8080);
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());

      TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
      SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(speakersInfo);

      line.open(FORMAT);
      speakers.open(FORMAT);


      int numBytesRead;
      byte[] data = new byte[line.getBufferSize() / 5];

      // Begin audio capture.
      line.start();
//      speakers.start();

      // Here, stopped is a global boolean set by another thread.
      while (!socket.isOutputShutdown()) {
        // Read the next chunk of data from the TargetDataLine.
        numBytesRead =  line.read(data, 0, data.length);
        // Save this chunk of data.
        out.write(data, 0, numBytesRead);
//        speakers.write(data, 0, numBytesRead);
      }
    } catch (LineUnavailableException | IOException err) {
      err.printStackTrace();
    }
  }
}
