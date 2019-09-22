package no.oslomet.authenticationfrontendmicroservice.controller;

import no.oslomet.authenticationfrontendmicroservice.model.Channel;
import no.oslomet.authenticationfrontendmicroservice.model.ForumUser;
import no.oslomet.authenticationfrontendmicroservice.model.Post;
import no.oslomet.authenticationfrontendmicroservice.model.User;
import no.oslomet.authenticationfrontendmicroservice.service.ChannelService;
import no.oslomet.authenticationfrontendmicroservice.service.ForumUserService;
import no.oslomet.authenticationfrontendmicroservice.service.PostService;
import no.oslomet.authenticationfrontendmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private PostService postService;
    @Autowired
    private ForumUserService forumUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @GetMapping("")
    public String home(Model model) {
        List<Post> posts = postService.getAllPosts();
        if(postService.getAllPosts().size() < 1)
        {
            dataDump();
        }
        model.addAttribute("posts", posts);
        return "login";
    }

    @GetMapping("home")
    public String homePage(Model model, @RequestParam(required = false, name="searchedTitle") String searchedTitle) {
        User user = getCurrentAuth();
        model.addAttribute("user", user);
        model.addAttribute("admin", isAdmin(user));
        List<Channel> channels;
        if(searchedTitle == null){
            channels = channelService.getAllChannels();
        }
        else {
            channels = channelService.searchListByString(searchedTitle);
        }
        model.addAttribute("searchedChannels", channels);
        model.addAttribute("searchedTitle", searchedTitle);
        model.addAttribute("channels", channelService.getAllChannels());

        return "index";
    }


    @GetMapping("signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("login&error")
    public String login() { return "login";}

    @PostMapping("processRegistration")
    public String register(@ModelAttribute("user") User user) {
        String noopPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(noopPassword));
        user.setRoles("user");
        userService.saveUser(user);
        return "redirect:/index";
    }
    @GetMapping("submitPost/{channel}")
    public String createPost(Model model, @PathVariable String channel)
    {
        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("channel", channel);
        return "submitPost";
    }
    @PostMapping("/savePost")
    public String savePost(RedirectAttributes redirectAttributes
            , @RequestParam("title") String title, @RequestParam("postValue") String postValue, @RequestParam("channel") String channel)
    {
        Post post = new Post();
        post.setChannelName(channel);
        post.setTitle(title);
        post.setPostValue(postValue);
        post.setUserId(getCurrentAuth().getId());
        postService.savePost(post);
        redirectAttributes.addAttribute("title", channel);
        return "redirect:/getChannelPosts";
    }


    @GetMapping("/getChannelPosts")
    public String getChannelPosts(@RequestParam("title") String title, Model model){
        List<Post> posts = postService.getPostsInChannel(title);

        model.addAttribute("posts", posts);
        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("channel", title);
        return "channelPosts";
    }

    @PostMapping("/likePost")
    public String likePost(@RequestParam("postId") long id)
    {
        postService.likePost(postService.getPostById(id), getCurrentAuth().getId());
        return "redirect:/home";
    }

    @PostMapping("subscribeToChannel")
    public String subscribeToChannel(@RequestParam("title2") String title)
    {
        System.out.println(title);
        long id = getCurrentAuth().getId();
         ForumUser forumUser = forumUserService.getForumUserById(id);
         forumUserService.addSubscription(forumUser, title);
         return "redirect:/home";
    }

    @GetMapping("/allPosts")
    public String allPosts(Model model, @RequestParam(required = false, name="searchedTitle") String searchedTitle)
    {
        List<Post> posts = new ArrayList<>();
        if(searchedTitle == null) {
            posts= postService.getAllPosts();
        }
        else {
            posts = postService.searchListByString(searchedTitle); // get searchy
        }
        model.addAttribute("searchedPosts", posts);
        model.addAttribute("searchedTitle", searchedTitle);
        model.addAttribute("user", getCurrentAuth());
        return "allPosts";
    }
//    public String homePage(Model model, @RequestParam(required = false, name="searchedTitle") String searchedTitle) {
//        User user = getCurrentAuth();
//        model.addAttribute("user", user);
//        model.addAttribute("admin", isAdmin(user));
//        List<Channel> channels;
//        if(searchedTitle == null){
//            channels = channelService.getAllChannels();
//        }
//        else {
//            channels = channelService.searchListByString(searchedTitle);
//        }
//        model.addAttribute("searchedChannels", channels);
//        model.addAttribute("searchedTitle", searchedTitle);
//        model.addAttribute("channels", channelService.getAllChannels());
//
//        return "index";
//    }

    public boolean isAdmin(User user){
        if(user.getRoles().equals("admin"))
        {
            return true;
        }
        else{
            return false;
        }
    }
    public User getCurrentAuth()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();
        return user;
    }

    @GetMapping("folUserPage")
    public String followUserPage(@RequestParam("folUserId") long id, Model model)
    {
        model.addAttribute("followedUser", userService.getUserById(id));
        model.addAttribute("followedUserPosts", postService.getPostsByUser(id));
        model.addAttribute("user", getCurrentAuth());
        return "followedUserPage";
    }

    @GetMapping("followSubscribePosts")
    public String followSubscripePosts(Model model){
        ForumUser user = forumUserService.getForumUserById(getCurrentAuth().getId());
        List<Post> posts = new ArrayList<>();
        List<Channel> subscribedChannels = channelService.getChannelInUser(user.getId());
        for(Channel c : subscribedChannels)
        {
            List<Post> postToAdd = postService.getPostsInChannel(c.getTitle());
            for(Post p : postToAdd)
            {
                posts.add(p);
            }
        }

        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("posts", posts);
        return "subscribedPosts";
    }
    @GetMapping("userPage")
    public String userPage(Model model){
        User user = getCurrentAuth();
        model.addAttribute("user", user);
        List<ForumUser> following = forumUserService.getFollowing(user.getId());
        model.addAttribute("following", following);
        return "userPage";
    }
    @GetMapping("admin")
    public String admin(Model model){
        User user = getCurrentAuth();
        model.addAttribute("user", user);
        if(user.getRoles().equals("admin"))
        {
        model.addAttribute("admin", true);
        }
        else{
            model.addAttribute("admin", false);
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "admin";
    }
    @PostMapping("followUser")
    public String followUser(@RequestParam("userId") Long id)
    {
        long currId = getCurrentAuth().getId();
        ForumUser forumUser = forumUserService.getForumUserById(currId);
        forumUserService.followUser(forumUser, id);
        return "redirect:/home";
    }
    @PostMapping("updateUser")
    public String changePassword(@RequestParam("userIda") Long id, @RequestParam("password") String password,
                                 @RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,
                                 @RequestParam("email") String email)
    {
        User user = userService.getUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userService.updateUser(id, user);
        if(isAdmin(getCurrentAuth())) {
            return "redirect:/admin";
        }
        else
        {
            return "redirect:/userPage";
        }
    }
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId)
    {

        if(getCurrentAuth().getId() == userId)
        {
            userService.deleteUserById(userId);
            return "redirect:/signup";
        }
        else{
            userService.deleteUserById(userId);
            return "redirect:/admin";
        }
    }


    public String dataDump()
    {
        Channel ch1 = new Channel();
        ch1.setTitle("Art");
        channelService.saveChannel(ch1);
        Channel ch2 = new Channel();
        ch2.setTitle("Bitcoin");
        channelService.saveChannel(ch2);
        Channel ch3 = new Channel();
        ch3.setTitle("Forest Gump");
        channelService.saveChannel(ch3);
        Channel ch4 = new Channel();
        ch4.setTitle("Weather");
        channelService.saveChannel(ch4);
        Channel ch5 = new Channel();
        ch5.setTitle("Photography");
        channelService.saveChannel(ch5);

        User user = new User();
        user.setPassword("1234");
        String noopPassword = user.getPassword();
        user.setFirstName("James");
        user.setLastName("Clarke");
        user.setEmail("james@clarke.com");
        user.setRoles("user");
        user.setPassword(passwordEncoder.encode(noopPassword));
        userService.saveUser(user);

        User user2 = new User();
        user2.setPassword("1234");
        String noopPassword2 = user2.getPassword();
        user2.setFirstName("admin");
        user2.setLastName("admin");
        user2.setEmail("admin@admin.com");
        user2.setRoles("admin");
        user2.setPassword(passwordEncoder.encode(noopPassword2));
        userService.saveUser(user2);

        User user3 = new User();
        user3.setPassword("1234");
        String noopPassword3 = user3.getPassword();
        user3.setFirstName("Timothy");
        user3.setLastName("Jacobs");
        user3.setEmail("timothy@jacobs.com");
        user3.setRoles("user");
        user3.setPassword(passwordEncoder.encode(noopPassword3));
        userService.saveUser(user3);

        User user4 = new User();
        user4.setPassword("1234");
        String noopPassword4 = user4.getPassword();
        user4.setFirstName("Jaina");
        user4.setLastName("Proudmoose");
        user4.setEmail("jaina@proudmoose.com");
        user4.setRoles("user");
        user4.setPassword(passwordEncoder.encode(noopPassword4));
        userService.saveUser(user4);

        Post post = new Post();
        post.setChannelName(ch1.getTitle());
        post.setTitle("New Art Coming?");
        post.setPostValue("Great new art is being discovered all around the world.");
        post.setUserId(1);
        postService.savePost(post);

        Post post2 = new Post();
        post2.setChannelName(ch1.getTitle());
        post2.setTitle("What your favorite art?");
        post2.setPostValue("I prefer german art from the 15th century.");
        post2.setUserId(3);
        postService.savePost(post2);
        Post post3 = new Post();
        post3.setChannelName(ch1.getTitle());
        post3.setTitle("Are less people watching art?");
        post3.setPostValue("Shocking news tells us less people go to art shows and more go to football games.");
        post3.setUserId(2);
        postService.savePost(post3);
        Post post4 = new Post();
        post4.setChannelName(ch2.getTitle());
        post4.setTitle("Am I the only one who wants to discuss bitcoin?");
        post4.setPostValue("This forum seems... slow. Where is everybody?!");
        post4.setUserId(4);
        postService.savePost(post4);

        Post post5 = new Post();
        post5.setChannelName(ch3.getTitle());
        post5.setTitle("So what is ur favorite scene?");
        post5.setPostValue("I love it when he gets off the boat after some sweet shrimping.");
        post5.setUserId(3);
        postService.savePost(post5);

        Post post6 = new Post();
        post6.setChannelName(ch3.getTitle());
        post6.setTitle("Whats the best time of year to watch this movie?");
        post6.setPostValue("Seems to be... well. All over?");
        post6.setUserId(2);
        postService.savePost(post6);

        Post post7 = new Post();
        post7.setChannelName(ch4.getTitle());
        post7.setTitle("Favorite Weather");
        post7.setPostValue("Rain is awesome if u have hayfever..");
        post7.setUserId(1);
        postService.savePost(post7);

        Post post8 = new Post();
        post8.setChannelName(ch4.getTitle());
        post8.setTitle("They say global warming is coming");
        post8.setPostValue("All I've read the last days is that the winter is coming..");
        post8.setUserId(4);
        postService.savePost(post8);

        Post post9 = new Post();
        post9.setChannelName(ch5.getTitle());
        post9.setTitle("What shutters speed is best for waterfalls in broad datlight?");
        post9.setPostValue("A friend told me I should but some stop filters and just try until I get it.");
        post9.setUserId(1);
        postService.savePost(post9);

        Post post10 = new Post();
        post10.setChannelName(ch1.getTitle());
        post10.setTitle("Nikon vs Canon?");
        post10.setPostValue("Well who knows? depends on what lenses u have laying around I suppose");
        post10.setUserId(2);
        postService.savePost(post10);
        return "redirect:";
    }
}
