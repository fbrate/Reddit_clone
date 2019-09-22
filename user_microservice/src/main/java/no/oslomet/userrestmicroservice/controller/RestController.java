package no.oslomet.userrestmicroservice.controller;


import no.oslomet.userrestmicroservice.model.User;
import no.oslomet.userrestmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home() {
        return "This is a rest controller for user.";
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }




    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }
        @GetMapping("/users/findEmail/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }



    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id){
        System.out.println(userService.getUserById(id).getEmail());

        userService.deleteUserById(id);
    }


    @PostMapping("/users")
    public void saveUser(@RequestBody User newUser) {
        userService.saveUser(newUser);
    }



    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable long id, @RequestBody User newUser) {
        newUser.setId(id);
        userService.updateUser(id,newUser);
    }


}