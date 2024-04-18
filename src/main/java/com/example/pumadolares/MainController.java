package com.example.pumadolares;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(path = "/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestBody User newUser) {
        User n = new User();
        n.setName(newUser.getName());
        n.setEmail(newUser.getEmail());
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/findById")
    public @ResponseBody Optional<User> getMethodName(@RequestParam Integer id) {
        return userRepository.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id){
        userRepository.deleteById(id);
        return ResponseEntity.status(204).body(String.format("Eliminado usuario con id %2d exitosamente", id));
    }
    

    @PutMapping(path = "update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        User oldUser = userRepository.findById(id).orElse(null); 
        if(oldUser == null){
            return ResponseEntity.notFound().build();
        }

        oldUser.setName(updatedUser.getName());
        oldUser.setEmail(updatedUser.getEmail());
        final User user = userRepository.save(oldUser);
        return ResponseEntity.ok(user);
    }
}
