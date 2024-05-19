package com.ddib.user.notification.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Schema(description = "알림")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "알림 식별키")
    private Integer notificationId;

    @Schema(description = "제목")
    @Column(nullable = false)
    private String title;

    @Schema(description = "내용")
    @Column(nullable = false)
    private String content;

    @ColumnDefault(value = "false")
    @Column(nullable = false)
    @Schema(description = "읽음 여부")
    private boolean isRead;
}
