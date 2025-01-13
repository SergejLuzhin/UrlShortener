import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpRedirectServer {

    private final UrlManager urlManager;

    public HttpRedirectServer(UrlManager urlManager) {
        this.urlManager = urlManager;
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RedirectHandler(urlManager));
        server.setExecutor(null); // Используем стандартный исполнитель
        server.start();
        System.out.println("Сервер запущен на http://localhost:8080");
    }

    private static class RedirectHandler implements HttpHandler {
        private final UrlManager urlManager;

        public RedirectHandler(UrlManager urlManager) {
            this.urlManager = urlManager;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String shortUrl = "http://localhost:8080" + exchange.getRequestURI().getPath(); // Формируем полный URL
            UrlShortener urlShortener = urlManager.getUrl(shortUrl);

            if (urlShortener == null) {
                sendResponse(exchange, 404, "The link was not found.");
                return;
            }

            String accessCheck = urlShortener.checkAccess();
            if (accessCheck != null) {
                sendResponse(exchange, 403, accessCheck);
                return;
            }

            // Увеличиваем счётчик и перенаправляем
            urlShortener.incrementUsage();
            String redirectUrl = urlShortener.getOriginalUrl();
            exchange.getResponseHeaders().add("Location", redirectUrl);
            exchange.sendResponseHeaders(302, -1);
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
            exchange.sendResponseHeaders(statusCode, message.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(message.getBytes());
            }
        }
    }
}
