package no.oslomet.channelpostsmicroservice.controller;


import no.oslomet.channelpostsmicroservice.model.Channel;
import no.oslomet.channelpostsmicroservice.model.Post;
import no.oslomet.channelpostsmicroservice.model.User;
import no.oslomet.channelpostsmicroservice.repository.UserRepository;
import no.oslomet.channelpostsmicroservice.service.ChannelService;
import no.oslomet.channelpostsmicroservice.service.PostService;
import no.oslomet.channelpostsmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    ChannelService channelService;

    @GetMapping("/")
    public String home() {
        updateAllUsersFromDatabase();
        return "This is a rest controller for forum.";
    }

    // updates entire database from server.
    @GetMapping("/updateUserDataBase")
    public void updateAllUsersFromDatabase(){
        userService.updateAllUserDataFromServer();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/posts")
    public  List<Post> getAllPosts() {return postService.getAllPosts();}
    @GetMapping("/channels")
    public  List<Channel> getAllChannels() {return  channelService.getAllChannels();}

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable long id) {
        return postService.getPostById(id);
    }
    @GetMapping("/channels/{id}")
    public Channel getChannelById(@PathVariable String id){return channelService.getChannelById(id);}

    @PutMapping("users/{id}")
    public void updateUser(@PathVariable long id, @RequestBody User newUser) {
        newUser.setId(id);
        userService.saveUser(newUser);
    }




    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id){
        // did it this bad way cause of org.springframework.dao.DataIntegrityViolationException
        System.out.println(id);
        User user = userService.getUserById(id);
        user.setLastName("");
        user.setFirstName("");
        user.setPosts(null);
        user.setFollowing(null);
        user.setChannels(null);
        userService.saveUser(user);
    }
    @PostMapping("/users")
    public void saveUser(@RequestBody User newUser) {
        userService.saveUser(newUser);
    }
    @PostMapping("/channels")
    public void saveChannel(@RequestBody Channel newChannel) {
        channelService.saveChannel(newChannel);
    }

    @PostMapping("/posts")
    public void savePost(@RequestBody Post newPost)
    {
        postService.savePost(newPost);
    }

    @GetMapping("/posts/user{id}")
    public List<Post> getPostsByUser(@PathVariable Long id){
        return userService.getUserById(id).getPosts();
    }
    @PostMapping("users/subto/{channel}")
    public void subscribeToChannel(@PathVariable String channel, @RequestBody User user)
    {
        System.out.println(channel + "  " + user.getId());
        System.out.println(channelService.getChannelById(channel).getPosts().size());
        System.out.println(userService.getUserById(user.getId()).getPosts().size() + " size use rpost");
        User userB = userService.getUserById(user.getId());
        List<Channel> channels = userB.getChannels();
        boolean alreadySubbed = false;
        for(Channel c : channels)
        {
            if (c.getTitle().equals(channel))
            {
                alreadySubbed = true;
                break;
            }
        }
        if(!alreadySubbed) {
            channels.add(channelService.getChannelById(channel));
            userB.setChannels(channels);
            userService.saveUser(userB);
        }

    }
    @PostMapping("posts/likePost/{id}")
    public void likePost(@RequestBody Post post, @PathVariable long id)
    {
        System.out.println("post liked");
        Post post2 = postService.getPostById(post.getId());

        Collection<Long> liked = post2.getUserIdLiked();
        boolean alreadyLiked = false;
        for(Long i : liked)
        {
            if(i == id)
            {
                alreadyLiked = true;
                break;
            }
        }
        if(!alreadyLiked) {
            liked.add(id);
            post2.setUserIdLiked(liked);
            post2.setLikes(post2.getUserIdLiked().size());
            postService.savePost(post2);
        }

    }
    @PostMapping("users/followUser/{id}")
    public void followUser(@PathVariable Long id, @RequestBody User user)
    {
        User userSubbing= userService.getUserById(user.getId());
        List<User> following = userSubbing.getFollowing();
        boolean alreadyFollowing = false;
        for(User u : following)
        {
            if (u.getId() == id)
            {
                alreadyFollowing = true;
                break;
            }
        }
        if(!alreadyFollowing) {
            following.add(userService.getUserById(id));
            userSubbing.setFollowing(following);
            userService.saveUser(userSubbing);
        }
        System.out.println("follow finito");

    }

    @GetMapping("/posts/fromchan/{id}")
    public List<Post> getPostsInChannel(@PathVariable String id){
        Channel channel = channelService.getChannelById(id);
        List<Post> posts = channel.getPosts();
        return posts;
    }
    @GetMapping("users/getFollowing/{id}")
    public List<User> getFollowingList(@PathVariable long id)
    {
        User user = userService.getUserById(id);
        List<User> following = user.getFollowing();
        return following;
    }
    @GetMapping("channels/chaninuser/{id}")
    public List<Channel> getChanInUser(@PathVariable long id)
    {
        User user = userService.getUserById(id);
        List<Channel> channels = user.getChannels();
        List<Channel> toReturn = new ArrayList<>();
        for(Channel c : channels)
        {
            toReturn.add(c);
        }
        return toReturn;
    }

    @GetMapping("posts/postsByUser/{id}")
    public List<Post> getPostsByUser(@PathVariable long id)
    {
        return userService.getUserById(id).getPosts();
    }

}