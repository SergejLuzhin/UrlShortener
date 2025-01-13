# URL Shortener - Консольное приложение

## Описание

URL Shortener — это консольное приложение для сокращения ссылок с поддержкой:
- Лимита переходов по ссылке.
- Времени жизни ссылки (в минутах).
- Управления созданными ссылками (изменение лимитов).

Приложение позволяет сокращать ссылки, контролировать доступ по ним, а также изменять параметры уже созданных ссылок.

---

## Как пользоваться

### Запуск программы

1. Убедитесь, что установлен Java версии 17 или выше.
2. Скомпилируйте программу:
   ```bash
   javac Main.java UrlManager.java UrlService.java UrlShortener.java HttpRedirectServer.java
   ```
3. Запустите программу:
   ```bash
   java Main
   ```
4. Программа запустится и вы увидите главное меню:

   ```
   === Меню ===
   1. Сократить ссылку
   2. Изменить лимиты для ссылки
   3. Выйти
   Выберите действие:
   ```

### Основные команды

#### 1. Сократить ссылку
- **Описание:** Создаёт сокращённую ссылку с указанным лимитом переходов и временем жизни.
- **Как использовать:**
  - Выберите пункт меню `1`.
  - Введите оригинальный URL.
  - Укажите лимит переходов.
  - Укажите время жизни ссылки в минутах.
- **Пример:**
  ```
  Введите оригинальный URL: https://example.com
  Введите лимит переходов: 5
  Введите время жизни ссылки (в минутах): 10
  Сокращённая ссылка: http://localhost:8080/a1B2c3D4
  ```

#### 2. Изменить лимиты для ссылки
- **Описание:** Позволяет изменить лимит переходов и время жизни для уже созданной ссылки.
- **Как использовать:**
  - Выберите пункт меню `2`.
  - Введите полную сокращённую ссылку (например, `http://localhost:8080/a1B2c3D4`).
  - Программа выведет текущие параметры ссылки.
  - Введите новый лимит переходов и время жизни.
- **Пример:**
  ```
  Введите полную сокращённую ссылку: http://localhost:8080/a1B2c3D4
  Текущие параметры для ссылки http://localhost:8080/a1B2c3D4:
  - Переходов использовано: 3 из 5
  - Время жизни: 8.2 минут из 10
  Введите новый лимит переходов: 10
  Введите новое время жизни ссылки (в минутах): 20
  Лимиты успешно обновлены.
  ```

#### 3. Выйти
- **Описание:** Завершает работу приложения.
- **Как использовать:** Выберите пункт меню `3`.

---

## Тестирование

Для тестирования приложения выполните следующие шаги:

1. **Создайте несколько сокращённых ссылок:**
   - Укажите разные лимиты переходов и времена жизни.
2. **Откройте ссылки в браузере:**
   - Убедитесь, что переход работает корректно до исчерпания лимитов.
   - Убедитесь, что приложение выдаёт сообщения на английском языке, если лимиты исчерпаны:
     - "The link has expired."
     - "The limit of redirects has been reached."
3. **Измените параметры ссылки:**
   - Убедитесь, что новые лимиты и время жизни применяются корректно.
4. **Попробуйте открыть ссылку после её истечения:**
   - Проверьте, что приложение сообщает об истечении времени жизни.

---


