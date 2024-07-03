package io.github.braayy.forum.features.reply;

import io.github.braayy.forum.features.topic.Topic;
import io.github.braayy.forum.features.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "replies")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String message;

    @Setter
    private Boolean solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private User author;

}
