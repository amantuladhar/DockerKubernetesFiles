#!/usr/bin/env bash

while true ; do curl localhost:9090/test/status-5xx ; echo "\n" ;  sleep 1 ; done;
