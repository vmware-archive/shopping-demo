#!/bin/bash

set -Eeuo pipefail

function error_usage() {
  local username=$(whoami)
  echo >&2 "Example usages:"
  echo >&2 "DOCKER_REPO=\"${username}\" ${0} ad"
  echo >&2 "DOCKER_REPO=\"${username}\" ${0} cart"
}

function tear_down() {
  local stream_name=${1}
  local application=${2}

  riff streaming stream delete "${stream_name}" &>/dev/null || true
  riff application delete "${application}" &>/dev/null || true
  riff core deployer delete "${application}" &>/dev/null || true
}

function set_up() {
  local docker_repo="${1}"
  local stream_name=${2}
  local application=${3}
  local path=${4}
  local port=${5}

  riff streaming stream create "${stream_name}" \
    --provider franz-kafka-provisioner \
    --content-type 'application/json'

  riff application create "${application}" \
    --image "${docker_repo}/${application}" \
    --git-repo https://github.com/sbawaska/shopping-demo \
    --sub-path "${path}" \
    --tail

  riff core deployer create "${application}" \
    --application-ref "${application}" \
    --tail

  echo "💡 about to expose the ingester to local port ${port}"
  echo "🚀️ open another terminal and start ingesting events️"
  echo
  kubectl port-forward "svc/${application}-deployer" "${port}":80
}

function main() {
  docker_repo="${1}"
  pipeline_type="${2}"

  stream_name="cart-events"
  application="cart-ingest"
  port=9090
  if [ "$pipeline_type" == "ad" ]; then
    stream_name="ad-events"
    application="ad-ingest"
    port=8080
  fi

  tear_down "${stream_name}" "${application}"
  set_up "${docker_repo}" "${stream_name}" "${application}" "./${application}" "${port}"
}

REPO=${DOCKER_REPO:-}
if [ -z "${REPO}" ]; then
  echo >&2 "❌ DOCKER_REPO not set: aborting ☠️"
  error_usage
  exit 42
fi
if [ "${1}" != "ad" ] && [ "${1}" != "cart" ]; then
  echo >&2 "❌ pipeline type not properly set: aborting ☠"
  error_usage
  exit 43
fi
main "${REPO}" "${1}"
