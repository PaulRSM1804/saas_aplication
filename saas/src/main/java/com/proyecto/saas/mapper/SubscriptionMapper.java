package com.proyecto.saas.mapper;

import org.springframework.stereotype.Component;

import com.proyecto.saas.dto.SubscriptionDTO;
import com.proyecto.saas.model.Subscription;

@Component
public class SubscriptionMapper {
    public SubscriptionDTO toDTO(Subscription subscription) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(subscription.getId());
        dto.setConsumerId(subscription.getConsumer().getId());
        dto.setCourseId(subscription.getCourse().getId());
        dto.setSubscriptionDate(subscription.getSubscriptionDate());
        dto.setCreatorName(subscription.getCourse().getCreator().getFirstName());
        dto.setConsumerName(subscription.getConsumer().getFirstName());
        dto.setCourseName(subscription.getCourse().getTitle());
        return dto;
    }

    public Subscription toEntity(SubscriptionDTO dto) {
        Subscription subscription = new Subscription();
        subscription.setId(dto.getId());
        // El consumidor y el curso se asignan en el servicio
        subscription.setSubscriptionDate(dto.getSubscriptionDate());
        return subscription;
    }
}
