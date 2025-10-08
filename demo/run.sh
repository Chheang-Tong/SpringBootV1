#!/usr/bin/env bash
set -e

# set the env for this run
export APP_JWT_SECRET=$(openssl rand -base64 48)

mvn spring-boot:run
