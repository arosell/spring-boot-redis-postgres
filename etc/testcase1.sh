#!/bin/sh
set -e

curl -s http://localhost:8081/posts/clearCache -o /dev/null
curl -s http://localhost:8081/posts/MSG001 -o /dev/null -w  "%{time_starttransfer}s\n"
curl -s http://localhost:8081/posts/MSG001 -o /dev/null -w  "%{time_starttransfer}s\n"
