package vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class VertxTest {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        HttpServer httpServer = vertx.createHttpServer()
                .requestHandler(req -> System.out.println("Request received in thread " + Thread.currentThread().getName()))
                .listen(8080);
    }
}
