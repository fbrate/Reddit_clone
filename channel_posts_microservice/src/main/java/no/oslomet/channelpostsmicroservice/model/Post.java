package no.oslomet.channelpostsmicroservice.model;

import java.util.Collection;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String title;
        private String postValue;
        private String channelName;
        private String userName;
        private long userId;
        private long likes;

        @ElementCollection
        Collection<Long> userIdLiked;

        @ManyToOne
        @JoinColumn(name = "user")
        @JsonIgnore
        private User user;

        @ManyToOne
        @JoinColumn(name = "channel")
        @JsonIgnore
        private Channel channel;

}
