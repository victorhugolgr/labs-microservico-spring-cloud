FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/loja-0.0.1-SNAPSHOT.jar /app/

RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
RUN chmod +x wait-for-it.sh
RUN apk add --no-cache bash

CMD ["./wait-for-it.sh", "localhost:8888/loja/default" , "--timeout=30" , "--" ,"java", "-jar", "loja-0.0.1-SNAPSHOT.jar"]