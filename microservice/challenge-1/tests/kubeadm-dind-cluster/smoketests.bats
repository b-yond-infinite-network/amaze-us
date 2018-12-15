#!/usr/bin/env bats

@test "3 Pods are running" {
  n=$(kubectl get pods -l app=app42 | tail -n +2 | wc -l)
  [ $n -eq 3 ]
}

@test "2 Services are running" {
  n=$(kubectl get svc -l app=app42 | tail -n +2 | wc -l)
  [ $n -eq 2 ]
}

@test "All fronts are up and running" {

  fronts=$(kubectl get pods -l app=app42 -l tier=frontend -o wide | tail -n +2 | awk '{ print $6 }')
  for ip in $fronts; do
    code=$(docker exec kube-master curl -s -o /dev/null -w "%{http_code}" "${ip}:8080/ping")
    [ "${code}" = "200" ]
    code=$(docker exec kube-master curl -s -o /dev/null -w "%{http_code}" "${ip}:8080/ready")
    [ "${code}" = "200" ]
  done

}

@test "Frontend service is up and running" {
  code=$(kubectl run curl -it --rm --image="appropriate/curl" --restart=Never -- curl -s -o /dev/null -w "%{http_code}\n" "myapp.default:8080/ready" | head -n1 | tr -d '\r\n')
  [ "${code}" = "200" ]
}