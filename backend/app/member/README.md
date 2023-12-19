## Member

Resource Endpoint: `/groups/<int:group_id>/members/`

### List all members in the current group

`GET /?is_archived=<bool>`

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
    },
    {
        "id": <int>,
        "name": <string>,
    }
]

```

---

### Create a new member in the current group

`POST /`

#### Request Body

```json
{
    "name": <string>,
}
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
}
```

---

### Get a specific member

`GET /<int:member_id>/`

#### Request Body

```json
None
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
    "total_due_amount": <float>,
    "related_sessions": [
        {
            "id": <int>,
            "name": <string>,
            "start": <datetime>,
            "due": <datetime>,
            "is_open": <bool>,
            "paid": <bool>,
            "due_amount": <float>,
        },
        {
            "id": <int>,
            "name": <string>,
            "start": <datetime>,
            "due": <datetime>,
            "is_open": <bool>,
            "paid": <bool>,
            "due_amount": <float>,
        }
    ],
    "is_archived": <bool>,
}
```

---

### Update/Archive a specific member

`PUT/PATCH /<int:member_id>/`

#### Request Body

```json
{
    "name": <string>,
    "is_archived": <bool>,
}
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
    "is_archived": <bool>,
}
```
