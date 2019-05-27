package com.emt.lab.usermanagement.events;

import com.emt.lab.usermanagement.model.UserRole;
import com.emt.lab.usermanagement.repository.UserRoleRepository;
import com.emt.lab.usermanagement.service.CreateAdminService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ApplicationReadyEvent {
    private final CreateAdminService createAdminService;
    private final UserRoleRepository userRoleRepository;

    public ApplicationReadyEvent(CreateAdminService createAdminService,
                                 UserRoleRepository userRoleRepository) {
        this.createAdminService = createAdminService;
        this.userRoleRepository = userRoleRepository;
    }


    @EventListener
    public void createAdminAndRolesAtStartup(ApplicationStartedEvent applicationStartedEvent) {

        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("USER");
        createRoleIfNotFound("EMPLOYEE");
        createRoleIfNotFound("MANAGER");

        System.out.println("Just started... Creating admin.");
        this.createAdminService.createAdmin();
    }

    @Transactional
    void createRoleIfNotFound(String name) {
        Optional<UserRole> userRole = userRoleRepository.findByName(name);

        if (!userRole.isPresent()) {
            userRole = Optional.of(new UserRole(name));
            userRoleRepository.save(userRole.get());
        }
    }
}
