#!/bin/bash
echo "Value-$1"
sleep 3

if [[ "$1" == "error" ]]; then
    exit 10
fi