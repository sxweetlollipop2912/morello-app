## Collect Session and Collect Session Entry

Resource Endpoint: `/groups/<int:group_id>/sessions/`

### List all collect sessions of the current group

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
        "name": <string>, // from Balance Entry
        "description": <string>, // from Balance Entry
        "start": <datetime>,
        "due": <datetime>,
        "is_open": <bool>,
        "member_count": <int>,
        "paid_count": <int>,
        "amount_per_member": <float>,
        "current_amount": <float>,
    },
]
```

---

### Get a specific collect session

`GET /<int:session_id>/`

#### Request Body

```json
None
```

#### Response

```json
{
    "id": <int>,
    "name": <string>, // from Balance Entry
    "description": <string>, // from Balance Entry
    "start": <datetime>,
    "due": <datetime>,
    "is_open": <bool>,
    "paid_count": <int>,
    "amount_per_member": <float>,
    "member_ids": [<ID>, <ID>, ...],
    "balance_entry_id": <ID>,
},
```

---

### Update/Close/Open a specific collect session

`PUT/PATCH /<int:session_id>/`

#### Request Body

```json
{
    "start": <datetime>,
    "due": <datetime>,
    "is_open": <bool>,
    "amount_per_member": <float>,
    "member_ids": [<ID>, <ID>, ...],
}
```

#### Response

```json
{
    "id": <int>,
    "name": <string>, // from Balance Entry
    "description": <string>, // from Balance Entry
    "start": <datetime>,
    "due": <datetime>,
    "is_open": <bool>,
    "paid_count": <int>,
    "amount_per_member": <float>,
    "member_ids": [<ID>, <ID>, ...],
    "balance_entry_id": <ID>,
    @optional "modified_at": <datetime>,
},
```

---

### List all members in a specific collect session

`GET /<int:session_id>/members/?paid=<bool>`

#### Request Body

```json
None
```

#### Response

```json
[
    {
        "session_id": <int>,
        "member_id": <int>,
        "member_name": <string>,
        "paid": <bool>,
        "paid_date": <datetime | NULL>,
    },
]
```

---

### Get a specific member in a collect session

`GET /<int:session_id>/members/<int:member_id>`

#### Request Body

```json
None
```

#### Response

```json
{
    "session_id": <int>,
    "member_id": <int>,
    "member_name": <string>,
    "paid": <bool>,
    "paid_date": <datetime | NULL>,
},
```

---

### Update/Mark as paid/Mark as unpaid a member in a collect session

`PUT/PATCH /<int:session_id>/members/<int:member_id>`

#### Request Body

```json
{
    "paid": <bool>,
}
```

#### Response

```json
{
    "session_id": <int>,
    "member_id": <int>,
    "member_name": <string>,
    "paid": <bool>,
    "paid_date": <datetime | NULL>,
},
```

---

### Delete a member from a collect session

`DELETE /<int:session_id>/members/<int:member_id>`

#### Request Body

```json
None
```

#### Response

```json
None
```
