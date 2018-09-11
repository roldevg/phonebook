# Phonebook webapp backend

## Docker

- Build:

```
docker build -t phonebook:demo .
```

- Run:

```
docker run -p 8080:8080 phonebook:demo
```

- Stop:

```
docker stop $(docker ps | grep phonebook:db | awk '{print $1'})
```

