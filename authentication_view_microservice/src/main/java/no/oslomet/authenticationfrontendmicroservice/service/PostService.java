package no.oslomet.authenticationfrontendmicroservice.service;

import no.oslomet.authenticationfrontendmicroservice.model.Channel;
import no.oslomet.authenticationfrontendmicroservice.model.ForumUser;
import no.oslomet.authenticationfrontendmicroservice.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class PostService {
        String BASE_URL = "http://localhost:9091/posts";
        private RestTemplate restTemplate = new RestTemplate();


        public List<Post> getAllPosts() {
                return Arrays.stream(
                        restTemplate.getForObject(BASE_URL, Post[].class)
                ).collect(Collectors.toList());
        }

        public Post getPostById(long id) {
                Post post = restTemplate.getForObject(BASE_URL + "/" + id, Post.class);
                return post;
        }
        public List<Post> getPostsInChannel(String channel)
        {
                return Arrays.stream(
                restTemplate.getForObject(BASE_URL+"/fromchan/"+channel, Post[].class)
        ).collect(Collectors.toList());
        }
        public List<Post> getPostsByUser(long id)
        {
                return Arrays.stream(
                        restTemplate.getForObject(BASE_URL+"/postsByUser/"+id, Post[].class)
                ).collect(Collectors.toList());
        }
        public void likePost(Post post, long id)
        {
                restTemplate.postForObject(BASE_URL+"/likePost/"+ id, post , Post.class);
        }

        public void savePost(Post post) {
                restTemplate.postForObject(BASE_URL, post, Post.class);

        }
        public List<Post> searchListByString(String search)
        {
                List<Post> postsToReturn = new ArrayList<>();
                List<Post> posts = getAllPosts();
                for(Post p : posts)
                {
                        if(p.getTitle().toLowerCase().contains(search.toLowerCase())) {
                                postsToReturn.add(p);
                        }
                }
                return postsToReturn;
        }


}