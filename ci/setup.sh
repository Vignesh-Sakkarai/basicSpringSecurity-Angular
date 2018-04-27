#!/bin/bash

while [ $# -gt 0 ]; do
  case "$1" in
    --ip=*)
      ip="${1#*=}"
      ;;
    --job=*)
      job="${1#*=}"
      ;;
   --config=*)
      config="${1#*=}"
      ;;
   --load-vars-from=*)
      load_vars_from="${1#*=}"
      ;;
   --pipeline=*)
      pipeline="${1#*=}"
      ;;
    *)
      printf "***************************\n"
      printf " [USAGE] \n"
      printf "./setup.sh --ip=10.100.10.87 --pipeline=cms-service-pipeline --config=pipeline.yml --job=job-build-cms-service --load-vars-from=variables.yml\n"
      printf "***************************\n"
      exit 1
  esac
  shift
done

set -eu

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
export fly_target=${fly_target:-my-staff}
export ip=${ip:-127.0.0.1}
export job=${job:-job-build-cms-service}
export config=${config:-pipeline.yml}
export pipeline=${pipeline:-cms-service-pipeline}
export load_vars_from=${load_vars_from:-variables.yml}
echo "Concourse API target ${fly_target}"
echo " [ip] ${ip}"
echo " [job] ${job}"
echo " [config] ${config}"
echo " [pipeline] ${pipeline}"
echo " [load_vars_from] ${load_vars_from}"


pushd ${DIR}
    echo "[Logging In]"
    if [ "$ip" == "10.100.10.87" ]; then
        fly -t ${fly_target} login -c http://${ip}:8080 -u mystaff -p Hexad.2018
    else
        fly -t ${fly_target} login -c http://${ip}:8080
    fi

    echo "[Syncing fly version]"
    fly -t ${fly_target} sync

    echo "[Setting up pipeline]"
    fly -t ${fly_target} set-pipeline -p ${pipeline} -c ${config} --load-vars-from ${load_vars_from} -n

    echo "[Un-pausing pipeline]"
    fly -t ${fly_target} unpause-pipeline -p ${pipeline}
popd
