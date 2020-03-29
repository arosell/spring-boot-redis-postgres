#!/bin/sh
set -e

newpost=$(cat <<EOF
{
    "id":"MSG010",
    "message":"This is a new post",
    "user":{"userName":"asterix","name":"Asterix"},
    "date":"2020-03-28T12:07:43.883997"}
EOF
)

curl -s -d "$newpost" -H "Content-Type: application/json" -X POST -o /dev/null http://localhost:8081/posts/create
curl -s http://localhost:8081/posts/MSG010 -o /dev/null -w  "%{time_starttransfer}s\n"
curl -s http://localhost:8081/posts/MSG010 -o /dev/null -w  "%{time_starttransfer}s\n"
