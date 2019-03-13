# template
A templating service

## Build
```
mvn clean install
```
## Run
```json
mvn spring-boot:run
```
## Endpoints
### template
#### Supports:
* GET
* PUT
* POST
* DELETE
#### URL format:
http://localhost:8080/template/{{templateId}}
#### json
```json
{
  "id":1,
  "content":"dummy content",
  "channelTypes": [
    "SMS"
  ]
}
```
### Message
#### supports:
* POST
#### URL format:
https://localhost:8080/message
#### json
```json
{
  "template":1,
  "channel":"SMS",
  "content": {
    "name":"Bob"
  }
}
```
