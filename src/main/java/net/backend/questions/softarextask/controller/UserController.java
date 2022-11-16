package net.backend.questions.softarextask.controller;

import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getList() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user) {
        if (userService.create(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<User> findById(@PathVariable("user_id") Integer id) {
        User userFromDbById = userService.findById(id);
        return new ResponseEntity<>(userFromDbById, HttpStatus.OK);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<User> update(@PathVariable("user_id") Integer id, @RequestBody User user) {
        User userFromDB = userService.findById(id);
        userFromDB.setEmail(user.getEmail());
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setNumber(user.getNumber());
        userService.update(userFromDB);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<User> delete(@PathVariable Integer user_id) {
        //notify email after delete
        User byId = userService.findById(user_id);
        userService.delete(byId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
