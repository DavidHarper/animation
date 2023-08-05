#!/bin/bash

if [ -z "${APPCLASS}" ]
then
    echo "Set APPCLASS and re-run"
    exit 1
fi

SCRIPT_DIR=`dirname $0`

java ${JAVA_OPTS} -classpath ${SCRIPT_DIR}/build/libs/\*:${SCRIPT_DIR}/build/extlibs/\* ${APPCLASS} "$@"
