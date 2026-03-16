package com.example.order.tossController;

import com.example.order.service.OrderService;
import com.example.order.tossController.dto.ConfirmPaymentRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {
    //1. cart 진입

    //2. 주문하기 클릭 시 (1)OrderController의 createOrder 호출, order 생성 및 created상태 업데이트

    //3. 주문하기 클릭 시 (2)checkout 진입, 파라미터로 createOrder의 반환값들을 넘겨준다(orderId, totalPrice)

    //4. checkout에서 토스페이먼츠 실행, orderId와 totalPrice를 넘겨준다.
    //cf. 저 값들은 추후 백엔드 서버에서 현재 결제가 사용자가 진행하고자하는 결제가 맞는지 검증하기 위함이다.

    //5. 토스는 결제 정보가 맞다면 백엔드서버의 successUrl로 리다이렉트한다. successUrl을 success.html로 설정하자.
    //참고로 만약 프론트 서버가 있따면 토스는 프론트서버로 가는 걸로 이해해야 한다.
    //현재는 백엔드가 프론트를 제공하기에 백엔드의 successurl로 오는 것이지, successUrl은 원래 프론트가 제공한다.

    //6. 그게 프론트든 백엔드든 사실 상관은 없다. 토스가 그렇게 설명하니까 그런거지. 사실 중요한 건 successURL에서
    //백엔드로 결제 검증요청을 보내야 한다는 것이다. 그리고 백엔드는 검증이 완료되면 토스로 confirm api를 호출해야한다.

    //7. confirm성공 시 book, point 감소 요청, 만약 감소요청이 실패하면 백엔드에서 토스로 결제취소 api를 호출해야한다.

    //================================================================================================
    //여기까지가 monolithic버전이었고, 이제 msa관점에서 다시 설계해야 한다.
    //하지만 크게 고칠 부분은 없다. 다만, reserve와 confirm이 구분되고 또 분리 실행되어야 한다는 것.
    //confirm시기는 동일하다. reserve시기가 다를 뿐이다.
    //reserve는 결제하기 클릭 시 진행한다.

    //또한 monolithic버전에서는 재고와 포인트의 confirm을 모두 paymentController에서 처리했는데,
    //그 이유는 토스api와의 통신이 confirm에 포함됐기 때문이다. 하지만 reserve는 toss와 통신하지 않기에,
    //그것은 orderController에서 진행하려고 한다.

    private final OrderService orderService;

    //1단계
    @GetMapping({"/cart", "/cart.html"})
    public String cart() {
        return "cart";
    }

    //2단계(createOrder이후)
    @GetMapping({"/checkout", "/checkout.html"})
    public String checkout(@RequestParam String orderId,
                           @RequestParam Long totalPrice,
                           Model model) {

        model.addAttribute("orderId", orderId);
        model.addAttribute("totalPrice", totalPrice);
        return "checkout.html";

    }

    //3단계
    // templates/success.html
    @GetMapping({"/success", "/success.html"})
    public String success() {
        return "success.html";
    }

    // templates/fail.html
    @GetMapping({"/fail", "/fail.html"})
    public String fail() {
        return "fail.html";
    }


    //4단계
    //success.html 에서 이 메서드를 호출한다.
    //여기서 토스로 confirm 호출!
    @PostMapping("/api/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody ConfirmPaymentRequestDTO confirmPaymentRequestDTO,
                                                 HttpServletRequest req) throws Exception {

        log.info("[CONFIRM_API] incoming orderId={} remote={} ua={}",
                confirmPaymentRequestDTO.getOrderId(), req.getRemoteAddr(), req.getHeader("User-Agent"));

        if (!orderService.beforeConfirmApi(confirmPaymentRequestDTO)) {

            return ResponseEntity.badRequest().body("주문검증 실패");

        }

        ResponseEntity<String> response = orderService.confirmPayment(confirmPaymentRequestDTO.getPaymentKey(), confirmPaymentRequestDTO.getOrderId(),
                confirmPaymentRequestDTO.getAmount());

        if (response.getStatusCode().is2xxSuccessful()) {
            orderService.confirmOrder(Long.valueOf(confirmPaymentRequestDTO.getOrderId()));
        }

        return response;

    }


}
