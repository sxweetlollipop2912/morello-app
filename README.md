# morello-app

## See all endpoints

Go to `127.0.0.1:8000/api/docs/`

## How to login

First send a request to the server to get the access and refresh tokens:

```bash
POST 127.0.0.1:8000/api/users/token/
Body:
{
    "email": "admin@morello.app",
    "password": "admin"
}
```

Then for authorized requests, include Authorization header with the access token:

```bash
Authorization: Bearer <access_token>
```
