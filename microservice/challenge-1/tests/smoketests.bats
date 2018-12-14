#!/usr/bin/env bats

teardown() {
  rm -f "${BATS_TMPDIR}/*"
}

BASE_URL="http://localhost:8080"

@test "Application installed in ROOT"  {
  tmpfile=$(mktemp "${BATS_TMPDIR}/root.XXXXX")
  code=$(curl -s -o "${tmpfile}" -w "%{http_code}" "${BASE_URL}")
  ([ "${code}" = "200" ] && grep -i 'ShepHertz' "${tmpfile}" 2>&1 > /dev/null)
}

@test "Fetch logo" {
  code=$(curl -s -o /dev/null -w "%{http_code}" "${BASE_URL}/images/logo.png")
  [ "${code}" = "200" ]
}

@test "/home endpoint" {
  code=$(curl -s -o /dev/null -w "%{http_code}" "${BASE_URL}/home")
  [ "${code}" = "200" ]
}
