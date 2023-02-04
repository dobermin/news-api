FROM adoptopenjdk:16-jre-hotspot

VOLUME /tmp

EXPOSE 8082

ARG JAR_FILE=target/news-1.jar

ADD ${JAR_FILE} news-1.jar

ENTRYPOINT ["java","-jar","/news-1.jar"]