package net;

import net.util.Handler;

import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class VoiServer {
  ServerSocket server;
  SourceDataLine line;

  public VoiServer (int port, SourceDataLine line) throws IOException {
    this.server = new ServerSocket(port);
    this.line = line;
    System.out.println("Server created");
  }

  public void listen () throws IOException {
    while (true) {
      Socket client = this.server.accept();
      new Thread(new Handler(client, this.line)).start();
    }
  }

}
