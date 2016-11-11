#!/bin/bash
# removeSecurityRule.sh
if [ -r lastIP ]; then
 currentIP=`cat lastIP`
 aws ec2 revoke-security-group-ingress --group-id sg-0433846d --ip-permissions "[{\"IpProtocol\": \"tcp\", \"FromPort\": 22, \"ToPort\": 22, \"IpRanges\": [{\"CidrIp\": \"$currentIP/32\"}]}]" && echo $currentIP > lastIP
else
 echo "$0: no file named lastIP found!"
 exit 1
fi
