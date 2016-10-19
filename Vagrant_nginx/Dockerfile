FROM nginx

#Install Unzip and Wget
RUN apt-get update -qq && apt-get install -y unzip && apt-get install -y wget

#Install Consul Template
RUN wget "https://releases.hashicorp.com/consul-template/0.16.0/consul-template_0.16.0_linux_amd64.zip"
RUN unzip consul-template_0.16.0_linux_amd64.zip -d /usr/local/bin

#Setup Consul Template Files
RUN mkdir /etc/consul-templates
COPY ./app.conf.tmpl /etc/consul-templates/app.conf

# Remove all other conf files from nginx
RUN rm /etc/nginx/conf.d/*

#Default Variables
ENV CONSUL 192.168.56.104:8500

CMD service nginx start && consul-template -consul=$CONSUL -template "/etc/consul-templates/app.conf:/etc/nginx/conf.d/app.conf:service nginx restart || true"