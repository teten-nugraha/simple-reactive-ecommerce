package id.ten.orderservice.common.order.domain;

public enum OrderStatus {
  INITIATED_RESERVING_STOCK,
  RESERVED_PROCESSING_PAYMENT,
  PAYED_PREPARING_FOR_SHIPMENT,

  CANCELLED_OUT_OF_STOCK,
  CANCELLED_PAYMENT_REJECTED
}
