## Endpoints

GET /messages

Response

```json
    [
  {
    "sender": "@atul",
    "text": "message text",
    "id": "messageID",
    "createdAt": "timestamp"
  }
]
```


POST /create-message

Request body

```json
{
  "sender": "@atul",
  "text": "Hello world!"
}
```

Response
```json
{
  "sender": "@atul",
  "text": "Hello world!",
  "id": "messageID",
  "createdAt": "timestamp"
}
```



