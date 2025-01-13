import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        UrlManager urlManager = new UrlManager();
        UrlService urlService = new UrlService(urlManager);
        HttpRedirectServer server = new HttpRedirectServer(urlManager);

        // Запускаем HTTP-сервер
        new Thread(() -> {
            try {
                server.startServer();
            } catch (IOException e) {
                System.out.println("Ошибка при запуске сервера: " + e.getMessage());
            }
        }).start();

        // Консольное меню
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Меню ===");
            System.out.println("1. Сократить ссылку");
            System.out.println("2. Изменить лимиты для ссылки");
            System.out.println("3. Выйти");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                if (choice == 1) {
                    System.out.print("Введите оригинальный URL: ");
                    String originalUrl = scanner.nextLine();
                    System.out.print("Введите лимит переходов: ");
                    int limit = scanner.nextInt();
                    System.out.print("Введите время жизни ссылки (в минутах): ");
                    int lifetimeMinutes = scanner.nextInt();
                    scanner.nextLine();

                    String shortUrl = urlService.createShortUrl(originalUrl, limit, lifetimeMinutes);
                    System.out.println("Сокращённая ссылка: " + shortUrl);

                } else if (choice == 2) {
                    System.out.print("Введите полную сокращённую ссылку: ");
                    String shortUrl = scanner.nextLine();

                    UrlShortener urlShortener = urlManager.getUrl(shortUrl);
                    if (urlShortener == null) {
                        System.out.println("Ссылка не найдена.");
                        continue;
                    }

                    // Вывод текущих параметров ссылки
                    System.out.printf("Текущие параметры для ссылки %s:%n", shortUrl);
                    System.out.printf("- Переходов использовано: %d из %d%n", urlShortener.getUsageCount(), urlShortener.getLimit());
                    System.out.printf("- Время жизни: %.1f минут из %d%n",
                            urlShortener.getElapsedTimeMinutes(), urlShortener.getLifetimeMinutes());

                    // Запрос новых параметров
                    System.out.print("Введите новый лимит переходов: ");
                    int newLimit = scanner.nextInt();
                    System.out.print("Введите новое время жизни ссылки (в минутах): ");
                    int newLifetimeMinutes = scanner.nextInt();
                    scanner.nextLine();

                    // Обновление параметров
                    urlShortener.setLimit(newLimit);
                    urlShortener.setLifetimeMinutes(newLifetimeMinutes);
                    System.out.println("Лимиты успешно обновлены.");

                } else if (choice == 3) {
                    System.out.println("Выход из программы.");
                    System.exit(0);
                } else {
                    System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
