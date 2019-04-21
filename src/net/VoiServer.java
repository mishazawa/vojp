package net;

import javax.sound.sampled.SourceDataLine;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class VoiServer {
  ServerSocket server;
  Socket client;
  SourceDataLine line;

  public VoiServer (int port, SourceDataLine line) throws IOException {
    this.server = new ServerSocket(port);
    this.line = line;
    System.out.println("Server created");
  }

  public void listen () throws IOException {
    this.client = this.server.accept();
    System.out.println("Connection accepted.");
    DataInputStream in = new DataInputStream(client.getInputStream());
    System.out.println("In-stream created");

    while (!client.isClosed()) {
      if (in.available() > 0) {
        int count = in.available();
        System.out.println("Read from client " + count + " bytes");
        byte[] buffer = new byte[count];
        in.read(buffer);
        System.out.println("Data => " + buffer);
        this.line.write(buffer, 0, buffer.length);
        this.line.start();
      }
    }

    System.out.println("Client disconected.");

    in.close();
    System.out.println("In-stream closed.");

    client.close();
    System.out.println("Client's socket was closed");
  }

}
