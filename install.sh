#!/bin/bash
if ! test -f /etc/systemd/system/optaplannercohort.service
then
touch /etc/systemd/system/optaplannercohort.service
echo "[Unit]
Description=Java Backend Cohorts
Requires=After=cohortsserver.service

[Service]
ExecStart=/var/www/java/cohorts/run.sh
WorkingDirectory=/var/www/java/cohorts
Restart=always
# Restart service after 10 seconds if node service crashes
RestartSec=10
# Output to syslog
StandardOutput=syslog
StandardError=syslog
SyslogIdentifier=cohorts
#User=<alternate user>
#Group=<alternate group>

[Install]
WantedBy=multi-user.target" >> /etc/systemd/system/optaplannercohort.service
fi

if ! test -f /etc/systemd/system/angularcohort.service
then
touch /etc/systemd/system/angularcohort.service
printf "[Unit]
Description=Node Js Server for Cohorts
Requires=After=mongod.service

[Service]
Environment=SECRET_TOKEN=\"%s\"
ExecStart=/var/www/nodejs/cohortFrontEnd/start.sh
WorkingDirectory=/var/www/nodejs/cohortFrontEnd
Restart=always
# Restart service after 10 seconds if node service crashes
RestartSec=10
# Output to syslog
StandardOutput=syslog
StandardError=syslog
SyslogIdentifier=cohorts
#User=<alternate user>
#Group=<alternate group>

[Install]
WantedBy=multi-user.target" $(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 30; echo '') >>  /etc/systemd/system/angularcohort.service
fi
