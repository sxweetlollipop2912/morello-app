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
}
```

Or if a `Session` is opened with this `Balance Entry`:

```json
{
    "name": <string>,
    "description": <string>,
    @optional "session": {
        "start": <datetime>,
        "due": <datetime>,
        "amount_per_member": <float>,
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

### Get a specific balance entry

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
        "member_count": <int>,
        "paid_count": <int>,
        "amount_per_member": <float>,
        "member_ids": [<ID>, <ID>, ...],
    }
}
```

---

### Update a specific balance entry

`PUT/PATCH /entries/<int:entry_id>/`

#### Request Body

Create a session when updating a balance entry:

```json
{
    "name": <string>,
    "description": <string>,
    "session": {
        "start": <datetime>,
        "due": <datetime>,
        "amount_per_member": <float>,
        @optional "member_ids": [<ID>, <ID>, ...],
    },
}
```

Delete a session when updating a balance entry:

```json
{
    "name": <string>,
    "description": <string>,
    "amount": <float>, // required
    "session": null,
}
```

#### Response

```json
{
    "id": <int>,
    "name": <string>,
    "description": <string>,
    "current_amount": <float>,
    "expected_amount": <float>,
    "created_at": <datetime>,
    @optional "session_id": <ID>,
    @optional "modified_at": <datetime>,
}
```

---

### Delete a balance entry

`DELETE /entries/<int:entry_id>/`

#### Request Body

```json
None
```

#### Response

```json
None
```
