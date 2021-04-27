package id.ten.orderservice.common.order.controllers.models;

import id.ten.orderservice.common.order.domain.*;

import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static id.ten.orderservice.common.order.domain.OrderStatus.INITIATED_RESERVING_STOCK;

@Value
@Builder
@RequiredArgsConstructor
public class CreateOrderRequest {
  @NotEmpty
  private final String customerId;

  @NotEmpty
  @Valid
  private final List<OrderLine> orderLines;

  @NotNull
  @Valid
  private final Address shippingAddress;

  @NotNull
  @Valid
  private final Address billingAddress;

  @NotNull
  @Valid
  private final PaymentDetails paymentDetails;


  public Order toOrder() {
    return Order.builder()
      .billingAddress(billingAddress)
      .shippingAddress(shippingAddress)
      .orderLines(orderLines)
      .customerId(customerId)
      .paymentDetails(paymentDetails)
      .dateCreated(Instant.now())
      .status(INITIATED_RESERVING_STOCK)
      .build();
  }
}
