## Moderator

Resource endpoint: `/groups/<int:group_id>/moderators/`

### List all moderators of the current group

`GET /`

#### Request Body

```json
None
```

#### Response

```json
[
    {
        "id": <int>,
        "name": <string>,
        "email": <email>,
        "joined_at": <datetime>,
        @optional "avatar_url": <url>,
    },
    {
        "id": <int>,
        "name": <string>,
        "email": <email>,
        "joined_at": <datetime>,
        @optional "avatar_url": <url>,
    }
]

```

---

### Add a new moderator to the current group

`POST /`

#### Request Body

```json
{
    "emails": [<email>, <email>, ...]
}
```

#### Response

```json
[
    {
        "id": <int>,
        "email": <email>,
        "name": <string>,
        "joined_at": <datetime>,
        @optional "avatar_url": <url>,
    },
    {
        "id": <int>,
        "email": <email>,
        "name": <string>,
        "joined_at": <datetime>,
        @optional "avatar_url": <url>,
    }
]
```

---

### Get a specific moderator of the current group

`GET /<int:moderator_id>/`

#### Request Body

```json
None
```

#### Response

```json
Add
{
    "id": <int>,
    "email": <email>,
    "name": <string>,
    "joined_at": <datetime>,
    @optional "avatar_url": <url>,
}
```

---

### Delete a specific moderator

`DELETE /<int:moderator_id>/`

#### Request Body

```json
None
```

#### Response

```json
None
```
