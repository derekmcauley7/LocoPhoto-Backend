package com.loco.photo.locophoto.controller

import com.loco.photo.locophoto.repository.UserRepository
import com.loco.photo.locophoto.bean.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @Autowired
    UserRepository userRepository

    @GetMapping("/users")
    List<User> allUsers(){
        return userRepository.findAll()
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable String id){
        String userId = id
        return userRepository.findOne(userId)
    }

    @GetMapping(value="/user/email/{email}")
    User getUserByEmail(@PathVariable String email){
        String userEmail = email
        return userRepository.findDistinctEmail(userEmail)
    }

    @PostMapping(value="/user")
    User createUser(@RequestBody Map<String, String> body) {
        String name = body.get("name")
        Optional<String> imageUrl = body.get("imageUrl")
        
        if (!imageUrl.isPresent()) {
           imageUrl = "https://accessibility-checker.s3-eu-west-1.amazonaws.com/default.jpg"
        }
        String email = body.get("email")
        
        Optional<User> user1 = userRepository.findDistinctEmail(email)
        
        return user1.orElse(userRepository.save(new User(email, name, imageUrl, false , "")))
    }


    @PostMapping(value="/updateUser")
    User updateUser(@RequestBody Map<String, String> body) {
        String name = body.get("name")
        String bio = body.get("bio")
        String email = body.get("email")
        User user = userRepository.findDistinctEmail(email)
        if(user != null) {
           user.setBio(bio)
            user.setName(name)
            userRepository.save(user)
            return  user
        }
        return userRepository.save(new User(email, name, "", false , bio))
    }
}
