#!/bin/sh
set -e

newpost=$(cat <<EOF
{
    "id":"MSG011",
    "message":"This is another new post",
    "user":{"userName":"obelix","name":"Obelix"},
    "date":"2020-03-28T12:07:43.883997"}
EOF
)

echo "Result of /posts/all is cached after the first request"
curl -s http://localhost:8081/posts/clearCache -o /dev/null
curl -s http://localhost:8081/posts/all -o /dev/null -w  "%{time_starttransfer}s\n"
curl -s http://localhost:8081/posts/all -o /dev/null -w  "%{time_starttransfer}s\n"

echo "Cache of /posts/all evicted after adding a new post"
curl -s -d "$newpost" -H "Content-Type: application/json" -X POST -o /dev/null http://localhost:8081/posts/create
curl -s http://localhost:8081/posts/all -o /dev/null -w  "%{time_starttransfer}s\n"
curl -s http://localhost:8081/posts/all -o /dev/null -w  "%{time_starttransfer}s\n"

echo "Cache of /posts/all evicted after deleting a post, too"
curl -s -X DELETE -o /dev/null http://localhost:8081/posts/delete/MSG011
curl -s http://localhost:8081/posts/all -o /dev/null -w  "%{time_starttransfer}s\n"
curl -s http://localhost:8081/posts/all -o /dev/null -w  "%{time_starttransfer}s\n"
