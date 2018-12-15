#! /usr/bin/env bash

dind="vendor/kubeadm-dind-cluster/dind-cluster-v1.12.sh"
$dind down
$dind clean
