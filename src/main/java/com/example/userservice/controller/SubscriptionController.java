package com.example.userservice.controller;

import com.example.userservice.model.dto.SubscriptionDto;
import com.example.userservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<SubscriptionDto> addSubscription(@PathVariable Long id, @RequestBody SubscriptionDto dto) {
        return ResponseEntity.ok(subscriptionService.addSubscription(id, dto));
    }

    @GetMapping("/users/{id}/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(id));
    }

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id, @PathVariable Long subId) {
        subscriptionService.deleteSubscription(id, subId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<String>> getTopSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTopSubscriptions(3));
    }
}