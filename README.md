### Technologies
- Java
- Spring boot, Spring Data JPA, Spring Security
- PostgreSQL
- JWT Tokens
- Docker
- Swagger
-------------
### Setup

#####Docker setup:
В папке проекта откройте терминал.
Введите данные команды:

` ./mvnw clean package -DskipTests`

` docker-compose up`

#####IDE:
Откройте проект в вашей IDE
Укажите название вашей базы данных в файле **application.yml**
Запустите проект

-------------
После запуска в консоль будет выведен SuperUser token, что бы не аутентифицироваться каждый раз.

Ниже будет представлена краткая документация, полную документацию  можно посмотреть перейдя по ссылке:
http://localhost:8080/swagger-ui/index.html#

| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
| POST   |/auth/register| Регистрация  |
| POST     | /auth/authenticate       |   Аутентификация |
| POST | /auth/refresh-token        |    обновление токена (в Postman в графу Bearer token (или нажав на замок напротив метода) передать refresh-token. В ответ получим новый access-token) |

| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
| POST   |/posts/{userId}| Создать пост  |
| POST     | /posts/image/{postId}       |   Прикрепить изображение (Что бы добавить изображение, в postman -> body-> form-data: для ключа 'image' выбрать и добавить файл из системы) |
| PATCH | /posts/{postId}     |    изменить пост |
| DELETE | /posts/{postId}   |    удалить пост |
| GET | /posts/{postId}     |    посмотреть  пост |
| GET | /posts/of/{userId}     |    получить все посты пользователя |
| GET | /posts/activity/{userId}    | Лента активности для пользователя |

| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
| GET  |/users/{userId}| Получить информацию о пользователе  |
| PATCH  |/users/{userId}}| Изменить информацию о пользователе  |
| DELETE  |/users/{userId}}| Удалить пользователя |
| GET  |/users/{userId}/subscriptions}| Список подписок пользователя |
| GET  |/users/{userId}/friends|Список друзей пользователя
| GET  |/users/{userId}/friendRequests| Запросы в друзья пользователю |
| GET  |/users/{userId}/followers| Список подписчиков  |
| POST  |/users/{senderId}/friend-request/{receiverId}| Отправить заявку в друзья |
| POST  |/users/{receiverId}/accept-friend-request/{senderId}| Принять заявку|
| POST  |/users/{receiverId}/reject-friend-request/{senderId}| Отклонить заявку |
| POST  |/users/{userId}/remove-friend/{friendId}| Удалить из друзей |
| POST  |/users/{userId}/unfollow/{subscriptionId}| Отписаться |
-------------

Пример запроса на создание поста
```json
{
  "title": "My title",
  "content": "mnogo texta"
}
```
Пример запроса на регистрацию пользователя
```json
{
  "username": "user",
  "email": "user@gmail.com",
  "password": "userpassword",
  "role": "USER"
}
```
Пример запроса на аутентификацию пользователя
```json
{
  "email": "user@gmail.com",
  "password": "userpassword"
}
```

Взаимодействие между пользователями:

[![](https://i.ibb.co/Q8Dq8rN/chrome-03t-Xc-NEqqs.png)](https://ibb.co/4s1DsPT)

