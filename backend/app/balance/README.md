## Balance and Balance Entry

Resource Endpoint: `/groups/<int:group_id>/balance/`

### Get current and expected balance of the current group.

`GET /`

#### Request Body

```json
None
```

#### Response

```json
{
    "current_balance": <float>,
    "expected_balance": <float>, // total real amount of entries whose session is closed or not set + total expected amount of entries whose session is open
}
```

---

### Create a new balance entry of the current group

`POST /entries/`

#### Request Body

```json
{
    "name": <string>,
    "amount": <float>,
    "description": <string>,
    "created_at": <datetime>,
    @optional "session": {
        "start": <datetime>,
        "due": <datetime>,
        "is_open": <bool>,
        @optional "member_ids": [<ID>, <ID>, ...],
    },
}
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
    "current_amount": <float>,
    "expected_amount": <float>,
    "description": <string>,
    "created_at": <datetime>,
    @optional "session_id": <ID>,
}
```

---

### Get a specific Balance Entry

`GET /entries/<int:entry_id>/`

#### Request Body

```json
None
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
    "current_amount": <float>,
    "expected_amount": <float>,
    "description": <string>,
    "created_at": <datetime>,
    @optional "session": {
        "id": <ID>,
        "start": <datetime>,
        "due": <datetime>,
        "is_open": <bool>,
        "member_ids": [<ID>, <ID>, ...],
    }
}
```

---

### Update a specific Balance Entry

`PUT/PATCH /entries/<int:entry_id>/`

#### Request Body

```json
{
    "name": <string>,
    "description": <string>,
    "expected_amount": <float>,
}
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
    "description": <string>,
    "leader_user_id": <ID>,
    "created_at": <datetime>,
    @optional "modified_at": <datetime>,
    @optional "avatar_url": <url>,
}
```

---

### Delete a specific group

`DELETE /<int:group_id>/`

#### Request Body

```json
None
```

#### Response

```json
None
```
