## Phonebook database

### Build

> docker build -t phonebook:db

### Run

> docker run -p 8080:8080 -e MYSQL_ROOT_PASSWORD=mySuperSecretPassword phonebook:db