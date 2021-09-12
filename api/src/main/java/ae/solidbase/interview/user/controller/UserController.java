package ae.solidbase.interview.user.controller;


import ae.solidbase.interview.user.model.User;
import ae.solidbase.interview.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Hero is the main UserController we'll be using for request and response
 *
 * @author Farooq Ahmed
 *
 */
@RestController
public class UserController {

    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
        try {
            List<User> users = new ArrayList<User>();
            if (name == null)
                users.addAll(userRepository.findAll());
            else
                users.addAll(userRepository.findByName(name));
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userRepository.findById(id);
        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setName(user.getName());
            _user.setSurname(user.getSurname());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/deleteAll/")
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
