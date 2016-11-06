#!/bin/bash
# addSecurityRule.sh
[ -r lastIP ] && [ -r removeSecurityRule.sh ] && ./removeSecurityRule.sh
currentIP=`curl -s http://ipinfo.io/ip`
aws ec2 authorize-security-group-ingress --group-id sg-4d175d25 --ip-permissions "[{\"IpProtocol\": \"tcp\", \"FromPort\": 22, \"ToPort\": 22, \"IpRanges\": [{\"CidrIp\": \"$currentIP/32\"}]}]" && echo $currentIP > lastIP
