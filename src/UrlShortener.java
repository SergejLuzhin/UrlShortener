import java.time.Duration;
import java.time.LocalDateTime;

public class UrlShortener {
    private String shortUrl;
    private String originalUrl;
    private int limit; // Лимит переходов
    private int usageCount; // Количество использованных переходов
    private int lifetimeMinutes; // Время жизни в минутах
    private LocalDateTime createdAt; // Время создания ссылки

    public UrlShortener(String shortUrl, String originalUrl, int limit, int lifetimeMinutes) {
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
        this.limit = limit;
        this.usageCount = 0;
        this.lifetimeMinutes = lifetimeMinutes;
        this.createdAt = LocalDateTime.now();
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public int getLimit() {
        return limit;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public int getLifetimeMinutes() {
        return lifetimeMinutes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void incrementUsage() {
        usageCount++;
    }

    public void setLimit(int newLimit) {
        this.limit = newLimit;
    }

    public void setLifetimeMinutes(int newLifetimeMinutes) {
        this.lifetimeMinutes = newLifetimeMinutes;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(createdAt.plusMinutes(lifetimeMinutes));
    }

    public String checkAccess() {
        if (isExpired()) {
            return "The link has expired.";
        }
        if (usageCount >= limit) {
            return "The limit of redirects has been reached.";
        }
        return null; // Access is allowed
    }
    
    public double getElapsedTimeMinutes() {
        return Duration.between(createdAt, LocalDateTime.now()).toMinutes();
    }
}
