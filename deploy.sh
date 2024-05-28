#!/usr/bin/env bash
mvn clean package
echo 'Copy files...'
scp ./target/restfull-0.0.1-SNAPSHOT.jar root@95.163.222.35:/home/ammolite/web/askme_donation
echo 'Restart server...'
ssh -t root@95.163.222.35 << EOF
pgrep java | xargs kill -9
nohup java --enable-preview -jar /home/ammolite/web/askme_donation/restfull-0.0.1-SNAPSHOT.jar > /home/ammolite/web/askme_donation/log.txt &
EOF
echo 'Bye'