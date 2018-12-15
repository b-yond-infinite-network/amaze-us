#! /usr/bin/env bash

SECRETS_DIR="k8s"
dind="vendor/kubeadm-dind-cluster/dind-cluster-v1.12.sh"

function generate_secret(){
  sec_name="$1"
  key_name="$2"
  sec_fn="${SECRETS_DIR}/00-secret-${sec_name}.yaml"
  if [ ! -f "${sec_fn}" ]; then
    echo "generating $sec_fn..."
    the_secret=$(od -An -N10 -x /dev/random | sed -e 's/ //g' | base64)
cat > "${sec_fn}" <<EOD
apiVersion: v1
kind: Secret
metadata:
  name: ${sec_name}
type: Opaque
data:
  $key_name: ${the_secret}
EOD
  fi
}

generate_secret mysql-root-password db_root_password
generate_secret mysql-user-password db_user_password

echo "//"
echo "// Starting K8s DinD cluster (coffee time!)..."
echo "//"

$dind up

echo "//"
echo "// \"Uploading\" application image to your DinD cluster"
echo "//"

$dind copy-image app42front

echo "//"
echo "// Deploying"
echo "//"
kubectl apply -f k8s/
