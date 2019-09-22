package no.oslomet.channelpostsmicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
        @Id
        private long id;
        private String firstName;
        private String lastName;

        @OneToMany(mappedBy = "user")
        private List<Post> posts;

        @ManyToMany(mappedBy="following")
        @JsonIgnore
        private List<User> beingFollowed;


        @ManyToMany(cascade = {CascadeType.ALL})
        @JoinTable(
                name="userId_following_userId",
                joinColumns = {@JoinColumn(name = "user_following")},
                inverseJoinColumns = {@JoinColumn(name = "user_beingfollowed")}
        )
        private List<User> following;
        @ManyToMany(cascade = {CascadeType.ALL})
        @JoinTable (
                name = "Channel_User",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "channel_id")}
        )
        private List<Channel> channels;


}