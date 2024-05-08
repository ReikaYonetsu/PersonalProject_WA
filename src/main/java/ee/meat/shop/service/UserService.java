package ee.meat.shop.service;

import ee.meat.shop.model.User;
import ee.meat.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        // Before saving, you'd usually handle encoding the password
        return userRepository.save(user);
    }

}
