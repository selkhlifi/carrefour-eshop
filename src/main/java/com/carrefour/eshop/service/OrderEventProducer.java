package com.carrefour.eshop.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendOrderCreatedEvent(String orderId) {
    kafkaTemplate.send("eshop-orders", orderId, "Order Created");
  }
}
