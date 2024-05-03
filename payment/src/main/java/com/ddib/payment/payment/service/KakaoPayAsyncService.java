package com.ddib.payment.payment.service;

import com.ddib.payment.order.domain.Order;
import com.ddib.payment.order.domain.OrderStatus;
import com.ddib.payment.order.repository.OrderRepository;
import com.ddib.payment.payment.domain.Payment;
import com.ddib.payment.payment.domain.PaymentStatus;
import com.ddib.payment.payment.dto.request.KakaoReadyRequestDto;
import com.ddib.payment.payment.dto.response.KakaoApproveResponseDto;
import com.ddib.payment.payment.dto.response.KakaoReadyResponseDto;
import com.ddib.payment.payment.dto.response.KakaoRefundResponseDto;
import com.ddib.payment.payment.repository.PaymentRepository;
import com.ddib.payment.product.repository.ProductRepository;
import com.ddib.payment.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional
public class KakaoPayAsyncService {

    @Value("${pay.kakao.cid}")
    private String cid;
    @Value("${pay.kakao.secret-key}")
    private String secretKey;

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private KakaoReadyResponseDto kakaoReadyResponseDto;
    private String partnerOrderId;

    /**
     * 1. Ready (결제 준비)
     * 서버에서 카카오페이 서버로 결제 정보 전달
     * Secret Key를 헤더에 담아 파라미터 값들과 함께 POST로 요청
     * 결제 고유번호(TID)와 redirect URL을 응답받음
     */
    @Async
    public CompletableFuture<KakaoReadyResponseDto> kakaoPayReady(KakaoReadyRequestDto kakaoReadyRequestDto, String orderId) {

        log.info("===== Thread Name : " + Thread.currentThread().getName() + " =====");

        partnerOrderId = orderId;

        // 카카오페이 요청 양식
        Map<String, Object> params = new HashMap<>();
        params.put("cid", cid); // 가맹점 코드
        params.put("partner_order_id", orderId); // 가맹점 주문번호
        params.put("partner_user_id", "DDIB"); // 가맹점 회원 ID
        params.put("item_name", kakaoReadyRequestDto.getItemName()); // 상품명
        params.put("quantity", kakaoReadyRequestDto.getQuantity()); // 상품 수량
        params.put("total_amount", kakaoReadyRequestDto.getTotalAmount()); // 상품 총액
        params.put("tax_free_amount", kakaoReadyRequestDto.getTaxFreeAmount()); // 상품 비과세 금액
        params.put("approval_url", "http://localhost:8083/api/payment/success?product_id=" + kakaoReadyRequestDto.getProductId() + "&quantity=" + kakaoReadyRequestDto.getQuantity()); // 결제 성공 시 redirect url (인증이 완료되면 approval_url로 redirect)
        params.put("cancel_url", "http://localhost:8083/api/payment/cancel?partner_order_id=" + partnerOrderId); // 결제 취소 시 redirect url
        params.put("fail_url", "http://localhost:8083/api/payment/fail?partner_order_id=" + partnerOrderId); // 결제 실패 시 redirect url

        // 파라미터, 헤더 담기
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, this.getHeaders());
//        log.info("requestEntity: {}", requestEntity);
//        log.info("===== Thread Name : " + Thread.currentThread().getName() + " httpEntity 생성 완료 =====");

        // 외부 API 호출 및 Server to Server 통신을 위해 사용
        RestTemplate restTemplate = new RestTemplate();

        // 결제정보를 담아 카카오페이 서버에 post 요청 보내기
        // 결제 고유번호(TID), URL 응답받음


//        log.info("===== 카카오페이 서버로 post 요청 전송 =====");
        kakaoReadyResponseDto = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);
        log.info("kakaoReadyResponseDto: {}", kakaoReadyResponseDto);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 카카오페이 서버에서 데이터 응답 완료 =====");

        return CompletableFuture.completedFuture(kakaoReadyResponseDto);
    }

    @Async
//    public void insertOrderData(KakaoReadyRequestDto kakaoReadyRequestDto, String orderId, Principal principal) {
    public void insertOrderData(KakaoReadyRequestDto kakaoReadyRequestDto, String orderId) {

        log.info("===== Thread Name : " + Thread.currentThread().getName() + " =====");
//        log.info("===== Thread Name : " + Thread.currentThread().getName() + " insertOrderData 메서드 진입 =====");

        // 주문 테이블에 Data Insert
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 주문 데이터 insert 시작 =====");
        Order order = Order.builder()
                .orderId(orderId)
//                .user(userRepository.findByEmail(principal.getName()))
                .user(userRepository.findByEmail("tpwls101@naver.com"))
                .product(productRepository.findById(kakaoReadyRequestDto.getProductId()).get())
                .orderDate(new Timestamp(System.currentTimeMillis()))
                .productCount(kakaoReadyRequestDto.getQuantity())
                .totalPrice(kakaoReadyRequestDto.getTotalAmount())
                .receiverName(kakaoReadyRequestDto.getReceiverName())
                .receiverPhone(kakaoReadyRequestDto.getReceiverPhone())
                .orderRoadAddress(kakaoReadyRequestDto.getOrderRoadAddress())
                .orderDetailAddress(kakaoReadyRequestDto.getOrderDetailAddress())
                .orderZipcode(kakaoReadyRequestDto.getOrderZipcode())
                .status(OrderStatus.PAYMENT_COMPLETED)
                .build();
        orderRepository.save(order);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 주문 데이터 insert 완료 =====");
    }

    /**
     *
     * 2. Approve (결제 승인)
     * 사용자가 결제 수단을 선택하고 비밀번호를 입력해 결제 인증을 완료한 뒤, 최종적으로 결제 완료 처리를 하는 단계
     * 인증 완료시(테스트의 경우 비밀번호 입력 안하므로 결제하기 버튼 클릭시) 응답받은 pg_token과 tid로 최종 승인 요청함
     * 결제 승인 API를 호출하면 결제 준비 단계에서 시작된 결제건이 승인으로 완료 처리됨
     */
    @Transactional
    public KakaoApproveResponseDto kakaoPayApprove(String pgToken, Principal principal) {

        // 카카오페이 요청 양식
        Map<String, String> params = new HashMap<>();
        params.put("cid", cid);
        params.put("tid", kakaoReadyResponseDto.getTid());
        params.put("partner_order_id", partnerOrderId);
        params.put("partner_user_id", "DDIB");
        params.put("pg_token", pgToken);

        // 파라미터, 헤더 담기
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(params, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponseDto kakaoApproveResponseDto = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseDto.class);

        // 배송 정보 등 필요한 응답 데이터 추가 업데이트
        Order order = orderRepository.findByOrderId(partnerOrderId);
        kakaoApproveResponseDto.updateKakaoApproveResponseDto(order);

        // 결제 테이블에 Data Insert
        log.info("===== 결제 테이블에 Data Insert =====");

        Payment payment = Payment.builder()
                .tid(kakaoApproveResponseDto.getTid())
                .totalAmount(kakaoApproveResponseDto.getAmount().getTotal())
                .taxFree(kakaoApproveResponseDto.getAmount().getTax_free())
                .paymentMethodType(kakaoApproveResponseDto.getPayment_method_type())
                .paymentDate(kakaoApproveResponseDto.getApproved_at())
//                .user(userRepository.findByEmail(principal.getName()))
                .user(userRepository.findByEmail("tpwls101@naver.com"))
                .order(orderRepository.findByOrderId(partnerOrderId))
                .status(PaymentStatus.PAYMENT_COMPLETED)
                .build();
        paymentRepository.save(payment);

        return kakaoApproveResponseDto;
    }

    /**
     * 결제 진행 중 취소
     * 결제 준비 요청에서 insert된 주문 데이터 삭제
     */
    @Async
    @Transactional
    public void cancel(String orderId) {
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 결제 진행 중 취소로 인한 주문데이터 삭제 시작 =====");
        orderRepository.deleteByOrderId(orderId);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 결제 진행 중 취소로 인한 주문데이터 삭제 완료 =====");
    }

    /**
     * 결제 실패
     * 결제 준비 요청에서 insert된 주문 데이터 삭제
     */
    @Async
    @Transactional
    public void fail(String orderId) {
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 결제 실패로 인한 주문데이터 삭제 시작 =====");
        orderRepository.deleteByOrderId(orderId);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 결제 실패로 인한 주문데이터 삭제 완료 =====");
    }

    /**
     * 환불 (결제 취소)
     *
     * 결제 고유번호(tid)에 해당하는 결제건에 대해 지정한 금액만큼 결제 취소를 요청
     * 취소 요청시 비과세(tax_free_amount)와 부가세(vat_amount)를 맞게 요청해야 함
     */
    @Async
    @Transactional
    public void refund(String orderId) {

        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 카카오페이 환불 시작 =====");

        Payment payment = paymentRepository.findByOrderId(orderId);
        Order order = orderRepository.findByOrderId(orderId);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 환불 해당 주문건 조회 완료 =====");

        // 카카오 요청 양식
        Map<String, Object> params = new HashMap<>();
        params.put("cid", cid); // 가맹점 코드
        params.put("tid", payment.getTid()); // 결제 고유번호
        params.put("cancel_amount", payment.getTotalAmount()); // 취소 금액
        params.put("cancel_tax_free_amount", payment.getTaxFree()); // 취소 비과세 금액

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, this.getHeaders());
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " httpEntity 생성 완료 =====");
//        try {
//            Thread.sleep(3000L);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        RestTemplate restTemplate = new RestTemplate();

        KakaoRefundResponseDto kakaoRefundResponseDto = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/cancel",
                requestEntity,
                KakaoRefundResponseDto.class);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 카카오페이 서버에서 데이터 응답 완료 =====");

        // 주문 및 결제 상태 바꾸기
        payment.updateStatus(PaymentStatus.REFUND_COMPLETED);
        order.updateStatus(OrderStatus.CANCELED);
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 주문 및 결제 상태 변경 완료 =====");

        // 환불 시간 기록
        payment.updateRefundedAt(kakaoRefundResponseDto.getCanceled_at());
        log.info("===== Thread Name : " + Thread.currentThread().getName() + " 환불 시간 기록 완료 =====");
    }

    /**
     * http 헤더에 카카오 요구 헤더값인 secret key 담기
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String authorization = "SECRET_KEY " + secretKey;

        httpHeaders.set("Authorization", authorization);
        httpHeaders.set("Content-Type", "application/json");

        return httpHeaders;
    }

}
