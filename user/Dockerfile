# 빌드 관련
FROM openjdk:17-alpine

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

# 만들어진 jar파일 복사
COPY build/libs/*.jar user.jar

# 실행할 명령어
ENTRYPOINT ["java", "-jar", "user.jar", "--spring.profiles.active=k8s"]
