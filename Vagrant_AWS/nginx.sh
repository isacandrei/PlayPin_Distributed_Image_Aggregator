sudo su
apt-get update
apt -y install docker.io
service docker start
export DOCKER_IP='172.31.25.5'
export CLIENT_IP='curl -s http://ipinfo.io/ip'
docker run -d --net=host --name consul consul agent -server -bind=$DOCKER_IP -bootstrap -client=$DOCKER_IP -ui consul
docker run -d --name nginx -p 80:80 isac/nginx-consul-template:1.2

