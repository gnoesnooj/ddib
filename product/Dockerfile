# 빌드 관련
FROM openjdk:17-alpine

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

# 만들어진 jar파일 복사
COPY build/libs/*.jar product.jar

# 실행할 명령어
ENTRYPOINT ["java", "-jar", "product.jar", "--spring.profiles.active=prod"]