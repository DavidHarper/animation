#!/bin/bash

export APPCLASS=com.obliquity.animation.galilean.GalileanSatelliteApp

SCRIPT_DIR=`dirname $0`

exec ${SCRIPT_DIR}/runapp.sh "$@"
