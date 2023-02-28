package com.sofball.demo.utils;

import com.sofball.demo.dao.RoleRepository;
import com.sofball.demo.dao.UserRepository;
import com.sofball.demo.model.Role;
import com.sofball.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Initializing Softball App");

        Role role = roleRepository.findOne("ROLE_ADMIN");
        if(role == null) {
            role = new Role();
            role.setAuthority("ROLE_ADMIN");
            roleRepository.save(role);
        }

        User user = userRepository.findByUsernameIgnoreCase("erlandgreen");
        if(user == null) {
            user = new User();
            user.setUsername("erland");
            user.setPassword("123");
            user.setEmail("milik15@hotmail.com");
            user.setFirstName("Erland");
            user.setLastName("Green");
            user.getRoles().add(role);
            userRepository.save(user);
        }
        log.info("Softball App is Up");
    }
}
