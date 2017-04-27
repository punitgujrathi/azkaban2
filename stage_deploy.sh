#!/bin/bash
#stage-gamma ip : 10.32.65.3
#stage-bheema ip : 10.34.125.179
# pass the azkaban ip as argument for deployment


PROJ_BASE=/usr/share/fk-bigfoot-azkaban

## not needed now ## should be deleted in some time

#./gradlew distTar  --debug

AZKABAN_EXEC_SERVER=$PROJ_BASE/azkaban-exec-server
AZKABAN_WEB_SERVER=$PROJ_BASE/azkaban-web-server

#mkdir $AZKABAN_EXEC_SERVER $AZKABAN_WEB_SERVER

#Untar Azkaban Exec Server and WebServer
cd build/distributions/

tar -xvf azkaban-exec-server*.tar.gz
tar -xvf azkaban-web-server*.tar.gz

echo "---- (DEPLOYING ON $1)------"
ssh $1 sudo rm -rf /tmp/azkaban-exec-server /tmp/azkaban-web-server
ssh $1 sudo mkdir /tmp/azkaban-exec-server /tmp/azkaban-web-server
ssh $1 sudo chmod 777 /tmp/azkaban-exec-server /tmp/azkaban-web-server
scp -rC azkaban-exec-server-*/*  $1:/tmp/azkaban-exec-server
scp -rC azkaban-web-server-*/*  $1:/tmp/azkaban-web-server
ssh $1 sudo cp /tmp/azkaban-exec-server/lib/* /usr/share/fk-bigfoot-azkaban/azkaban-exec-server/lib
ssh $1 sudo cp /tmp/azkaban-web-server/lib/* /usr/share/fk-bigfoot-azkaban/azkaban-web-server/lib
ssh $1 find /usr/share/fk-bigfoot-azkaban -name '*.sh' | xargs chmod +x

