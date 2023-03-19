package javeriana.ms.rest;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import java.net.URI;

public class Main {
  public static final String BASE_URI = "http://" + System.getenv("SERVER_IP") + ":8080/";

  public static void main(String[] args) throws IOException {
    System.out.println("DATA:" + System.getenv("SERVER_IP"));
    new Main().doMain(args);
  }

  public void doMain(String[] args) throws IOException {
    final ResourceConfig rc = new ResourceConfig().packages("javeriana.ms.rest");
    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        server.shutdownNow();
      }
    }, "shutdownHook"));
    // run
    try {
      server.start();
      Thread.currentThread().join();
    } catch (Exception e) {
      System.out.println("There was an error while starting Grizzly HTTP server.");
    }
  }
}
