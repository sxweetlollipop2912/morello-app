## Group

Resource Endpoint: `/groups/`

### List groups which user is either the admin or a moderator of that group.

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
        "description": <string>,
        "is_leader": <bool>,
        @optional "avatar_url": <url>,
    },
    {
        "id": <int>,
        "name": <string>,
        "description": <string>,
        "is_leader": <bool>,
        @optional "avatar_url": <url>,
    }
]

```

---

### Get group leader of a specific group

`GET /<int:group_id>/leader`

#### Request Body

```json
None
```

#### Response

```json
{
    "id": <int>,
    "email": <email>,
    "name": <string>,
    @optional "avatar_url": <url>,
}
```

---

### Create a new group with the current user as the group leader

`POST /`

#### Request Body

```json
{
    "name": <string>,
    "description": <string>,
    // TODO: how to send image to the server?
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
    @optional "avatar_url": <url>,
}
```

---

### Get a specific group

`GET /<int:group_id>/?open_session_count=<int>&balance_entry_count=<int>`

#### Request Body

```json
None
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
    "description": <string>,
    "member_count": <int>,
    "balance": {
        "current": <float>,
        "expected": <float>,
    },
    "recent_open_sessions": [ // contains at most <open_session_count> sessions
        {
            "id": <ID>,
            "name": <string>,
            "start": <datetime>,
            "due": <datetime>,
            "is_open": <bool>,
            "member_count": <int>,
            "paid_count": <int>,
            "current_amount": <float>,
            "expected_amount": <float>,
            "amount_per_member": <float>,
        },
        {
            "id": <ID>,
            "name": <string>,
            "start": <datetime>,
            "due": <datetime>,
            "is_open": <bool>,
            "member_count": <int>,
            "paid_count": <int>,
            "current_amount": <float>,
            "expected_amount": <float>,
            "amount_per_member": <float>,
        },
    ],
    "recent_balance_entries": [ // contains at most <balance_entry_count> balance entries
        {
            "id": <ID>,
            "current_amount": <float>,
            "expected_amount": <float>,
            "description": <string>,
            "created_at": <datetime>,
            "session": {
                "id": <ID>,
                "start": <datetime>,
                "due": <datetime>,
                "is_open": <bool>,
                "member_count": <int>,
                "paid_count": <int>,
                "amount_per_member": <float>,
            },
        },
        {
            "id": <ID>,
            "current_amount": <float>,
            "expected_amount": <float>,
            "description": <string>,
            "created_at": <datetime>,
            @optional "session": NULL,
        },
    ],
    @optional "avatar_url": <url>,
}
```

---

### Update a specific group

`PUT/PATCH /<int:group_id>/`

#### Request Body

```json
{
    "name": <string>,
    "description": <string>,
    "leader_user_id": <ID>,
    @optional "avatar": <file>,
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
