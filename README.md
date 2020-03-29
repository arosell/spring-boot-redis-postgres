# Spring Boot & Postgres & Redis

This repository is a showcase how to speed up a web service by caching its data in Redis.

This showcase consists of 1) a simple Sprint Boot web service which impements an API for posts. The service's data is stored in 2) a Postgres DB. As cache 3) a Redis instance is uesd.

Objective of this showcase is to demonstrate the response time decrease in case of a cache hit (*). Furthermore, it shows a simple cache eviction strategy, which prevents that the web service shows outdated data which is still in cache.

(*) Database requests are throttled down. Otherwise, the performance difference would not be significant in this simple showcase. For comparing the performance of Postgres and Redis directly just alter [src/main/java/com/example/caching/service/PostService.java](src/main/java/com/example/caching/service/PostService.java).

## Build & Run

Included in this repository is a Docker Compose file, which starts up Postgres, Redis, and the example Spring Boot application.

```bash
docker-compose build
docker-compose up -d
```

Docker Compose will start up three containers (see `docker ps`):

```bash
CONTAINER ID   IMAGE                 COMMAND                  PORTS                    NAMES
xxxxxxxxxxxx   postservice:0.0.1     "java -jar postservi…"   0.0.0.0:8081->8080/tcp   spring-boot-redis-postgres_postservice_1
xxxxxxxxxxxx   postgres:12.2-alpine  "docker-entrypoint.s…"   0.0.0.0:5432->5432/tcp   spring-boot-redis-postgres_postgres_1
xxxxxxxxxxxx   redis:5.0.8-alpine    "docker-entrypoint.s…"   0.0.0.0:6379->6379/tcp   spring-boot-redis-postgres_redis_1
```

While starting up, the Postgres DB is populated with example data (see [etc/init_db.sql](etc/init_db.sql))

## Test Case 1 - Lazy Loading

The web service caches each post after it is requested for the first time:

To run this test case request [http://localhost:8081/posts/MSG001](http://localhost:8081/posts/MSG001) twice:

```bash
curl -s http://localhost:8081/posts/MSG001 -w  "\n%{time_starttransfer} s\n"
```

OR run [etc/testcase1.sh](etc/testcase1.sh).

When requesting the same URL twice, the second request will be answered much faster:

```bash
0.973975 s
0.027163 s
```

## Test Case 2 - Write-Through

A second feature of the web service is that it updates the cache immediately if data changes on runtime.

Test case 2 ([etc/testcase2.sh](etc/testcase2.sh)) shows that a new post is returned fast immediately after adding it.

```bash
0.033648s
0.025535s
```

## Test Case 3 - Write-Through 2

Besides the cache for storing posts indiviually, the web service maintains a second cache which contains the results of /posts/all. The web service implements a simple strategy for keeping that cache up to date. Everytime data is changed that cache is evicated and will be repopulated by the next request.

Test case 3 ([etc/testcase2.sh](etc/testcase2.sh)) shows that:

```bash
Result of /posts/all is cached after the first request
0.993738s
0.024830s
Cache of /posts/all evicted after adding a new post
0.979322s
0.021939s
Cache of /posts/all evicted after deleting a post, too
0.962831s
0.022656s
```

## Reference Documentation

For further reference, please consider the following sections:

* [Whitepaper about caching strategies](https://d0.awsstatic.com/whitepapers/Database/database-caching-strategies-using-redis.pdf)
