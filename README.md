# Juleluka API

Christmas calendar API - 2017 edition.

## Dev

### Build
```bash
./gradlew build
```

#### Prerequisites

### Install and run mongodb

```bash
brew update
brew install mongodb --with-openssl
mongod --config /usr/local/etc/mongod.conf
```


## Login


## Resource Owner Password

```bash
curl --request POST \
  --url 'https://juleluka.eu.auth0.com/oauth/token' \
  --header 'content-type: application/json' \
  --data '{"grant_type":"password","username": "bolla.pinnsvin@udp.no","password": "jul2017","audience": "https://api.juleluka.no", "scope": "openid profile", "client_id": "B2zmiuoFzheyL4fdW1wFdfJ7l371vQVn", "client_secret": "1YEQZmveGL-TW86jcx8DwQrnNVQunf5cX8d4D71DFc5Ph9-hnnH-OeGrlvBEK_S_", "invitation": "abc123"}'
```
  
```
https://juleluka.eu.auth0.com/authorize?
    audience=https://api.juleluka.no&
    scope=openid profile&
    response_type=code&
    client_id=B2zmiuoFzheyL4fdW1wFdfJ7l371vQVn&
    redirect_uri=http://local/callback&
    state=SOME_OPAQUE_VALUE&
    invitation=abc123
```
