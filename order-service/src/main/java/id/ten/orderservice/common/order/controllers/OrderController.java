package id.ten.orderservice.common.order.controllers;

import id.ten.orderservice.common.order.OrderService;
import id.ten.orderservice.common.order.controllers.models.CreateOrderRequest;
import id.ten.orderservice.common.order.controllers.models.CreateOrderResponse;
import id.ten.orderservice.common.order.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<CreateOrderResponse> initiate(@Validated @RequestBody CreateOrderRequest request) {
        log.info("initiate order request from customer {}", request.getCustomerId());
        return orderService.create(request.toOrder())
                .doOnNext(orderService::reserveStock)
                .map(Order::getId)
                .map(CreateOrderResponse::new);
    }
}
