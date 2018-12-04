#!/bin/bash
mvn package -Pdist -DskipTests -Dtar -Dmaven.javadoc.skip=true
