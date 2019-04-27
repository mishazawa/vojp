package net.util;

import javax.sound.sampled.SourceDataLine;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Handler implements Runnable {
  private Socket client;
  private SourceDataLine line;

  public Handler (Socket client, SourceDataLine line) {
    this.client = client;
    this.line = line;
  }

  @Override
  public void run() {
    if (this.client == null) return;

    try {
      String remoteAddr = this.client.getRemoteSocketAddress().toString() ;
      System.out.println("Client connection accepted.");
      DataInputStream in = new DataInputStream(this.client.getInputStream());
      System.out.println("In-stream created w/ " + remoteAddr);

      while (!isClosed()) {

        if (in.available() > 0) {
          int count = in.available();
          byte[] buffer = new byte[count];
          in.read(buffer);
          this.line.write(buffer, 0, buffer.length);
          this.line.start();
        }

      }
      System.out.println("Client " + remoteAddr + " disconected.");
      in.close();
      client.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean isClosed() {
    return this.client.isClosed();
  }
}
