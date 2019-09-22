package no.oslomet.channelpostsmicroservice.service;

import no.oslomet.channelpostsmicroservice.model.Channel;
import no.oslomet.channelpostsmicroservice.model.Post;
import no.oslomet.channelpostsmicroservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
        @Autowired
        private PostRepository postRepository;
        @Autowired
        private ChannelService channelService;
        @Autowired
        private UserService userService;


        public List<Post> getAllPosts()

        {
                return postRepository.findAll();
        }

        public Post getPostById(Long id) {
                return postRepository.findById(id).get();
        }

        public void savePost(Post post) {
                Channel channel = channelService.getChannelById(post.getChannelName());
                post.setChannel(channel);
                post.setUser(userService.getUserById(post.getUserId()));
                post.setUserName(post.getUser().getFirstName() + " " + post.getUser().getLastName());
                postRepository.save(post);
                channel.addPost(post);
                channelService.saveChannel(channel);
        }


        public void deletePostById(Long id) {
                postRepository.deleteById(id);
        }
}
