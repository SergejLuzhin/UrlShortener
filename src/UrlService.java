import java.util.Random;

public class UrlService {
    private final UrlManager urlManager;

    public UrlService(UrlManager urlManager) {
        this.urlManager = urlManager;
    }

    public String createShortUrl(String originalUrl, int limit, int lifetimeMinutes) {
        String shortCode = generateShortCode(); // Генерация короткого кода
        String shortUrl = "http://localhost:8080/" + shortCode;
        urlManager.addUrl(shortUrl, originalUrl, limit, lifetimeMinutes);
        return shortUrl;
    }

    public String handleRedirect(String shortUrl) throws Exception {
        UrlShortener urlShortener = urlManager.getUrl(shortUrl);
        if (urlShortener == null) {
            throw new Exception("Ссылка не найдена.");
        }

        String accessCheck = urlShortener.checkAccess();
        if (accessCheck != null) {
            throw new Exception(accessCheck);
        }

        urlShortener.incrementUsage();
        return urlShortener.getOriginalUrl();
    }

    private String generateShortCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) { // Генерируем код длиной 8 символов
            shortCode.append(chars.charAt(random.nextInt(chars.length())));
        }
        return shortCode.toString();
    }
}
