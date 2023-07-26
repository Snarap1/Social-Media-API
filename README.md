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

` ./mvnw clean package -DskipTests`

` docker-compose up`

#####IDE:

1) clone project
2) Set db name at  **application.yml**
3) start project

-------------
Look at console to get SuperUser authentication token.

U can see short documentation, **follow link to get full documentation:**
http://localhost:8080/swagger-ui/index.html# 
https://app.swaggerhub.com/apis/Snarap1/social-media-api/1.0.0  

| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
| POST   |/auth/register| Registration  |
| POST     | /auth/authenticate       |   Authentication |
| POST | /auth/refresh-token        |    token refresh (set Bearer-token in postman, or paste it in Swagger) |

| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
| POST   |/posts/{userId}| Create post |
| POST     | /posts/image/{postId}       | attach image(Postman -> body-> form-data: key 'image': chose file in your system) |
| PATCH | /posts/{postId}     |    edit post |
| DELETE | /posts/{postId}   |    delete post |
| GET | /posts/{postId}     |   get 1 post |
| GET | /posts/of/{userId}     |    get all User  posts  |
| GET | /posts/activity/{userId}    | action feed of those to whom the user is following |

| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
| GET  |/users/{userId}| Get information about user |
| PATCH  |/users/{userId}}| edit user information |
| DELETE  |/users/{userId}}| delete user |
| GET  |/users/{userId}/subscriptions}|subscriptions list for user |
| GET  |/users/{userId}/friends| Users friend list|
| GET  |/users/{userId}/friendRequests| friend requests list |
| GET  |/users/{userId}/followers| User  Followers list  |
| POST  |/users/{senderId}/friend-request/{receiverId}| send friend request |
| POST  |/users/{receiverId}/accept-friend-request/{senderId}| Accept friend request|
| POST  |/users/{receiverId}/reject-friend-request/{senderId}| Decline friend request |
| POST  |/users/{userId}/remove-friend/{friendId}| Remove friend|
| POST  |/users/{userId}/unfollow/{subscriptionId}| Unfollow |
-------------

Create post example: 
```json
{
  "title": "My title",
  "content": "mnogo texta"
}
```
User registration example: 
```json
{
  "username": "user",
  "email": "user@gmail.com",
  "password": "userpassword",
  "role": "USER"
}
```
User authentication request example 
```json
{
  "email": "user@gmail.com",
  "password": "userpassword"
}
```

[![](https://i.ibb.co/Q8Dq8rN/chrome-03t-Xc-NEqqs.png)](https://ibb.co/4s1DsPT)

