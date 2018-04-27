#!/usr/bin/env bash

set -e -x
export JDBC_CMS_TEST_URL="jdbc:mysql://10.100.10.91:3306/mystaff_cms?user=cms_user\u0026password=hexad@123\u0026serverTimezone=UTC\u0026useSSL=false"
cd cms-service
./gradlew clean build --debug
