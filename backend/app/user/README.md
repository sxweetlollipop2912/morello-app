## User

Resource endpoint: `/`

### Get the current user profile

`GET /me/`

#### Request Body

```json
None
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
    "email": <email>,
    "created_at": <datetime>,
    @optional "avatar_url": <url>,
}
```

---

### Update the current user profile

`PUT/PATCH /me/`

#### Request Body

```json
{
    "name": <string>,
    "password": <string>,
    @optional "avatar": // TODO: how to send image to the server?,
}
```

#### Response

```json
{
    "name": <string>,
    "password": <string>,
    @optional "avatar_url": <url>,
}
```

---

### Delete the current user profile

`DELETE /me/`

#### Request Body

```json
None
```

#### Response

```json
None
```

---

### Get the current user's token

`POST /token/`

#### Request Body

```json
{
    "email": <email>,
    "password": <string>,
}
```

#### Response

```json
{
    "refresh": <string>,
    "access": <string>,
}
```

---

### Refresh the current user's token

`POST /token/refresh/`

#### Request Body

```json
{
    "refresh": <string>,
}
```

#### Response

```json
{
    "refresh": <string>,
    "access": <string>,
}
```
