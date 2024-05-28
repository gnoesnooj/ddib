package com.ddib.waiting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserQueueService {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Value("${scheduler.enabled}")
    private Boolean scheduling = false;
    private final String USER_QUEUE_WAIT_KEY = "users:queue:%s:wait"; // 대기열 키
    private final String USER_QUEUE_WAIT_KEY_FOR_SCAN = "users:queue:*:wait"; // 스캔 대상 대기열 키
    private final String USER_QUEUE_PROCEED_KEY = "users:queue:%s:proceed"; // 진행 중인 대기열 키

    // 대기열 등록
    public Mono<Long> registerWaitQueue(final String queue, final Long userId) {
        // 먼저 등록한 사람이 높은 랭크를 갖도록 redis의 sortedset<userId,unix timestamp> 사용.
        // 등록과 동시에 몇 번째 대기인지 리턴
        long unixTimestamp = Instant.now().getEpochSecond();
        return reactiveRedisTemplate.opsForZSet()
                .add(USER_QUEUE_WAIT_KEY.formatted(queue), "userId : ". concat(userId.toString()), unixTimestamp) // ZSet에 사용자 추가
                .filter(i -> i) // 조건 필터링
                .switchIfEmpty(Mono.error(new Error("이미 존재함"))) // 오류 처리
                .flatMap(i -> reactiveRedisTemplate.opsForZSet().rank(USER_QUEUE_WAIT_KEY.formatted(queue), "userId : ". concat(userId.toString()))) // 랭크 조회
                .map(i -> i >= 0 ? i + 1 : i); // 랭크 반환
    }

    // 진입 가능 여부: wait큐 사용자 제거 - proceed큐 사용자 추가
    public Mono<Long> allowUser(final String queue, final Long count) {
        return reactiveRedisTemplate.opsForZSet().popMin(USER_QUEUE_WAIT_KEY.formatted(queue), count) // 최소값 pop
                .flatMap(member -> reactiveRedisTemplate.opsForZSet()
                        .add(USER_QUEUE_PROCEED_KEY.formatted(queue), member.getValue(), Instant.now().getEpochSecond())) // 진행큐에 추가
                .count(); // 허용된 사용자 수 반환
    }

    // 토큰을 통한 사용자 진입 가능 여부 확인
    public Mono<Boolean> isAllowedByToken(final String queue, final Long userId) {
        return this.generateToken(queue, userId) // 토큰 생성
                .map(i -> true) // 허용 여부 반환
                .defaultIfEmpty(false); // 기본값 설정
    }

    // 사용자 순위 조회
    public Mono<Long> getRank(final String queue, final Long userId) {

        Mono<Long> a = reactiveRedisTemplate.opsForZSet().rank(USER_QUEUE_WAIT_KEY.formatted(queue), "userId : ". concat(userId.toString())) // 랭크 조회
//                .defaultIfEmpty(-1L) // 기본값 설정
                .defaultIfEmpty(-1L) // 기본값 설정
                .map(rank -> rank >= 0 ? rank + 1 : rank);// 순위 반환

        return a;
    }

    // 토큰 생성
    public Mono<String> generateToken(final String queue, final Long userId) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256"); // SHA-256 알고리즘으로 해시 생성

            String input = "user-queue-%s-%d".formatted(queue, userId); // 입력 문자열 생성
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8)); // 해시 생성

            StringBuilder hexString = new StringBuilder();
            for (byte aByte : encodedHash) {
                hexString.append(String.format("%02x", aByte));
            }

            return Mono.just(hexString.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 1000) // 주기적으로 메서드 실행을 스케줄링, 서버 시작 후 5초 지연 후 10초마다 실행
    public void scheduleAllowUser() {
        if (!scheduling) {
            log.info("passed scheduling");
            return;
        }

        log.info("called scheduling...");

        Long maxAllowUserCount = 30L;

        reactiveRedisTemplate.scan(ScanOptions.scanOptions()
                        .match(USER_QUEUE_WAIT_KEY_FOR_SCAN)
                        .count(100)
                        .build())
                .map(key -> key.split(":")[2])
                .flatMap(queue -> allowUser(queue, maxAllowUserCount)
                        .map(allowed -> Tuples.of(queue, allowed)))
                .doOnNext(tuple -> log.info("Tried %d and allowed %d members of %s queues".formatted(maxAllowUserCount, tuple.getT2(), tuple.getT1()))) // 로그 출력
                .subscribe();
    }

}
