package com.summitcore.infrastructure.persistence;

import com.summitcore.core.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventEntity{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String description;
        private String identifier;
        private String location;
        private Integer capacity;
        private LocalDateTime startEvent;
        private LocalDateTime endEvent;
        private String organizer;

        @Enumerated(EnumType.STRING)
        private EventType typeEvent;

}
