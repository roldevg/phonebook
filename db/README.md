## Phonebook database

### Build

> docker build -t phonebook:db . 

### Run

> docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mySuperSecretPassword phonebook:db
