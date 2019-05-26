package com.emt.lab.usermanagement.ports.scheduled;

import com.emt.lab.usermanagement.model.User;
import com.emt.lab.usermanagement.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CleanNotVerifiedAccounts {
    private final UserRepository userRepository;

    public CleanNotVerifiedAccounts(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void deleteNotVerifiedAccounts() {
        List<User> users = userRepository.findAll();

        for (User user : users)
            if (user.isExpired())
                this.userRepository.deleteById(user.id);
    }
}
