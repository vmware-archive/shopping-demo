#!/bin/bash

set -Eeuo pipefail

function tear_down() {
  riff streaming processor delete "ad" &>/dev/null || true
  riff function delete "ad-recommender" &>/dev/null || true
  riff streaming stream delete "cart-events" &>/dev/null || true
  riff streaming stream delete "ad-events" &>/dev/null || true
  riff streaming stream delete "user-ads" &>/dev/null || true
}

function set_up() {
  riff streaming stream create "cart-events" \
    --provider franz-kafka-provisioner \
    --content-type 'application/json'

  riff streaming stream create "ad-events" \
    --provider franz-kafka-provisioner \
    --content-type 'application/json'

  riff streaming stream create "user-ads" \
    --provider franz-kafka-provisioner \
    --content-type 'application/json'

  riff function create "ad-recommender" \
    --git-repo https://github.com/sbawaska/shopping-demo.git \
    --sub-path ad-recommender/ \
    --tail

  riff streaming processor create "ad" \
    --function-ref ad-recommender \
    --input "cart-events" \
    --input "ad-events" \
    --output "user-ads"

  sleep 10

  echo "💡 about to expose the gateway to local port 8080"
  echo "🚀️ open another terminal and start ingesting events️"
  kubectl -n "riff-system" port-forward "svc/riff-streaming-http-gateway" "8080:80" &
  kubectl port-forward "svc/franz-kafka-liiklus" "6565:6565"
}

function main() {
  tear_down
  set_up
}

main
