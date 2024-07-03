package io.github.braayy.forum.features.topic;

import io.github.braayy.forum.features.course.Course;
import io.github.braayy.forum.features.reply.Reply;
import io.github.braayy.forum.features.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Topic {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    private String message;

    @Setter
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Setter
    private TopicStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Course course;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

}
