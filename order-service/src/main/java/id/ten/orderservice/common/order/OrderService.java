package id.ten.orderservice.common.order;

import id.ten.orderservice.common.order.clients.WarehouseServiceClient;
import id.ten.orderservice.common.order.domain.Order;
import id.ten.orderservice.common.order.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WarehouseServiceClient warehouseServiceClient;

    public Mono<Order> create(Order order) {
        return orderRepository.save(order);
    }

    public Mono<Order> updateStatus(String orderId, OrderStatus status) {
        return orderRepository.findById(orderId)
                .map(order -> order.withStatus(status).withDateUpdated(Instant.now()))
                .doOnNext(orderRepository::save);
    }

    public void reserveStock(Order order) {
        warehouseServiceClient.sendStockReservationEvent(order);
    }
}
