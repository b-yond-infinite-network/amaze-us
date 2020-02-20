#!/bin/bash

# This script is tasked with copying the contents over to the target folders, the ADD and COPY functions, associated with the Dockerfile syntax tend to fail if source is outside the work folder.
cp -pr ../booster Booster/
cp -pr ../stage2  stage2/
cp -pr ../cargo   cargo/

