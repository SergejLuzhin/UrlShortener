import java.util.HashMap;
import java.util.Map;

public class UrlManager {
    private final Map<String, UrlShortener> storage = new HashMap<>();

    public void addUrl(String shortUrl, String originalUrl, int limit, int lifetimeMinutes) {
        storage.put(shortUrl, new UrlShortener(shortUrl, originalUrl, limit, lifetimeMinutes));
    }

    public UrlShortener getUrl(String shortUrl) {
        return storage.get(shortUrl);
    }

    public boolean updateLimits(String shortUrl, int newLimit, int newLifetimeMinutes) {
        UrlShortener urlShortener = storage.get(shortUrl);
        if (urlShortener != null) {
            // Вывод текущих параметров до изменения
            System.out.printf("Текущие параметры ссылки %s:%n", shortUrl);
            System.out.printf("- Переходов использовано: %d из %d%n", urlShortener.getUsageCount(), urlShortener.getLimit());
            System.out.printf("- Время жизни: %.1f минут из %d%n",
                    urlShortener.getElapsedTimeMinutes(), urlShortener.getLifetimeMinutes());

            // Обновление параметров
            urlShortener.setLimit(newLimit);
            urlShortener.setLifetimeMinutes(newLifetimeMinutes);
            return true;
        }
        return false;
    }


}
