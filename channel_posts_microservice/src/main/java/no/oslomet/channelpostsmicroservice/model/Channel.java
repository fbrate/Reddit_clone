package no.oslomet.channelpostsmicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "channel")
public class Channel {
        @Id
        @Column(name = "channelId")
        private String title;

        @OneToMany(mappedBy = "channel")
        private List<Post> posts;


        @ManyToMany(mappedBy = "channels")
        @JsonIgnore
        private List<User> users;

        public void addPost(Post post)
        {
                posts.add(post);

        }

}
