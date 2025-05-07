package com.example.userservice.service;

import com.example.userservice.model.dto.SubscriptionDto;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.model.entity.Subscription;
import com.example.userservice.model.entity.User;
import com.example.userservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    public SubscriptionDto addSubscription(Long userId, SubscriptionDto dto) {
        User user = userService.getUserEntity(userId);
        Subscription subscription = Subscription.builder()
                .serviceName(dto.getServiceName())
                .user(user)
                .build();
        subscription = subscriptionRepository.save(subscription);
        logger.info("Added subscription {} to user {}", dto.getServiceName(), userId);
        return toDto(subscription);
    }

    public List<SubscriptionDto> getUserSubscriptions(Long userId) {
        User user = userService.getUserEntity(userId);
        return subscriptionRepository.findByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void deleteSubscription(Long userId, Long subId) {
        User user = userService.getUserEntity(userId);
        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (!subscription.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("Subscription does not belong to user");
        }
        subscriptionRepository.delete(subscription);
        logger.info("Deleted subscription {} from user {}", subId, userId);
    }

    public List<String> getTopSubscriptions(int limit) {
        List<Object[]> results = subscriptionRepository.findTopSubscriptions();
        return results.stream()
                .limit(limit)
                .map(obj -> (String) obj[0])
                .collect(Collectors.toList());
    }

    private SubscriptionDto toDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .id(subscription.getId())
                .serviceName(subscription.getServiceName())
                .build();
    }
}