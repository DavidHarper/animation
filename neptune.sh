#!/bin/bash

export APPCLASS=com.obliquity.animation.neptune.NeptuneApp

SCRIPT_DIR=`dirname $0`

exec ${SCRIPT_DIR}/runapp.sh "$@"
