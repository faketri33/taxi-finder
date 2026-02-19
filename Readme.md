# 🚕 Taxi Finder

Backend - система для поиска такси в реальном времени. Построена на микросервисной архитектуре с асинхронной обработкой событий, геопространственным поиском и реактивным программированием.

---
# Модули проекта
- [Марштуризатор запросов](common/gateway/Readme.md)
- [Eureka server](common/eureka/Readme.md)
- [Сервис поиска водителей для заказа](core/dispatching-service/Readme.md)
- [Сервис геопозиции](core/location-service/Readme.md)
- [Сервис поездок](core/ride-service/Readme.md)
- [Сервис управления пользователями](core/user-service/Readme.md)
- [Отправка уведомлений на телефон](common/notification-service/Readme.md)

---

## Стек технологий

| Слой               | Технологии                           |
|--------------------|--------------------------------------|
| Основной бэкенд    | Java 17, Spring Boot, Spring WebFlux |
| Очередь событий    | Apache Kafka                         |
| Геопоиск / кэш     | Redis (Redis Geo)                    |
| Сервис обнаружения | Netflix Eureka                       |
| API Gateway        | Spring Cloud Gateway                 |
| Уведомления        | TypeScript / Node.js                 |
| Инфраструктура     | Docker, Docker Compose               |
| Другое             | Go (вспомогательные сервисы)         |

---

## Архитектура

```
                        ┌─────────────────┐
                        │   API Gateway   │  ← единая точка входа
                        └────────┬────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
     ┌────────▼───────┐ ┌────────▼───────┐ ┌────────▼───────┐
     │  user-service  │ │  ride-service  │ │location-service│
     │                │ │                │ │  (Redis Geo)   │
     └────────────────┘ └────────┬───────┘ └────────▲───────┘
                                 │ ride.creatу      │ getNearby
                                 ▼                  │
                        ┌────────────────┐          │
                        │     Kafka      │          │
                        └────────┬───────┘          │
                                 │                  │
                        ┌────────▼───────┐          │
                        │dispatching-svc │──────────┘
                        │  (WebFlux)     │
                        └────────┬───────┘
                                 │ notify drivers
                        ┌────────▼───────┐
                        │notification-svc│
                        │  (TypeScript)  │
                        └────────────────┘
```

Все сервисы регистрируются в **Eureka Server** и взаимодействуют через **API Gateway**.

---

## Модули

### `core/ride-service`
Управляет поездками. При создании заказа публикует событие `ride.create` в Kafka, которое запускает процесс поиска водителя.

### `core/dispatching-service` _(Spring WebFlux, реактивный)_
Центральный модуль поиска. Подписывается на `ride.create`, создаёт объект `DispatchState` и запускает раундовый поиск водителей:

- **3 раунда**, каждый с увеличивающимся радиусом поиска
- В каждом раунде: запрос в `location-service` → фильтрация свободных водителей → отправка уведомления
- Если за 3 раунда водитель не найден — поездка переходит в статус ошибки
- Полностью реактивный пайплайн на `Mono` / `Flux`

```
DispatchState:
  - rideId
  - carType (ECONOMY / COMFORT / BUSINESS)
  - round (1–3)
  - status (DISPATCHING / ACCEPTED / ERROR / ...)
  - notifiedDrivers[]
```

### `core/location-service`
Хранит геопозицию водителей в **Redis Geo** и их состояние (статус, класс автомобиля). Предоставляет API для поиска ближайших свободных водителей по координатам, радиусу и классу.

### `core/user-service`
Управление пользователями и водителями: регистрация, авторизация, профили.

### `common/notification-service` _(TypeScript)_
Принимает события из Kafka и отправляет push-уведомления водителям. В разработке: WebSocket для прямой доставки уведомлений в мобильное приложение.

### `common/gateway`
API Gateway — маршрутизация запросов, балансировка, единая точка входа.

### `common/eureka`
Service Discovery — все сервисы регистрируются и находят друг друга через Eureka.

---

## Запуск локально

### Требования
- Docker
- Docker Compose

### Шаги

```bash
# 1. Клонировать репозиторий
git clone https://github.com/faketri33/taxi-finder.git
cd taxi-finder

# 2. Настроить переменные окружения
cp env/.env.example env/.env
# Отредактировать env/.env под своё окружение

# 3. Запустить
docker-compose \
  -f devops/docker/docker-compose.yml \
  -f devops/docker/docker-compose.database.yml \
  -f devops/docker/docker-compose.tools.yml \
  up --build
```

### Сервисы после запуска

| Сервис           | URL                     |
|------------------|-------------------------|
| API Gateway      | `http://localhost:800`  |
| Eureka Dashboard | `http://localhost:8761` |


---

## Лицензия

[MIT](LICENSE)