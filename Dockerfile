FROM maven:3.6.3-jdk-11 AS MAVEN_TOOL_CHAIN

RUN apt-get install --assume-yes openssl curl

# Pre build commands
USER root

COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/

EXPOSE 8080

# Build the app
RUN wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/backend-project/springboot/maven/2.x/build.sh
RUN chmod 775 ./build.sh
RUN sh build.sh

# Add extra docker commands here (if any)...

RUN chmod 775 ./get-cj.sh
RUN sh get-cj.sh

# Run the app
RUN wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/backend-project/springboot/maven/2.x/run.sh
RUN chmod 775 ./run.sh
CMD sh run.sh