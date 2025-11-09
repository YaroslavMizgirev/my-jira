# postgres-18-alpine

## Build image

```bash
docker build -f /mnt/c/Projects/docker/postgres-18-alpine/postgres-18-alpine.dockerfile --no-cache -t postgres-18-alpine /mnt/c/Projects/docker/postgres-18-alpine/
```

## Run container

```bash
docker run --name test1 --env-file /mnt/c/Projects/docker/postgres-18-alpine/postgres-18-alpine.env -v /home/mym/projects/docker/postgres-18-alpine/data/:/var/lib/postgresql/data -p 5433:5432 -d postgres-18-alpine
```
