#!/bin/bash

docker rm -f prokurator_backend

PAR="$(pwd)"

docker run \
  -itd -p 34123:34123 \
  -v ${PAR}/data:/resources \
  --name=prokurator_backend \
  --memory="800m" \
  --restart on-failure \
  tindersamurai/prokurator_backend
  
docker ps -a | grep prokurator_backend

docker logs prokurator_backend
