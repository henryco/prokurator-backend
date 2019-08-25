#!/bin/bash

docker rmi tindersamurai/prokurator_backend
docker build -t tindersamurai/prokurator_backend .
