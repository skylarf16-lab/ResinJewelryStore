package com.example.resinjewelrystore.demo;



import com.example.resinjewelrystore.model.Role;
import com.example.resinjewelrystore.model.User;
import com.example.resinjewelrystore.service.RoleService;
import com.example.resinjewelrystore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        roleService.save(new Role("ROLE_USER"));
        roleService.save(new Role("ROLE_ADMIN"));

        userService.UserService(new User("Ashley Johnson", "ashley", "1234"));
        userService.UserService(new User("Chris Smith", "chris", "1234"));
        userService.UserService(new User("Jackie Carry", "jackie", "1234"));
        userService.UserService(new User("Sarah Anderson", "sarah", "1234"));

        roleService.addRoleToUser("ashley", "ROLE_USER");
        roleService.addRoleToUser("chris", "ROLE_ADMIN");
        roleService.addRoleToUser("jackie", "ROLE_USER");
        roleService.addRoleToUser("sarah", "ROLE_ADMIN");
        roleService.addRoleToUser("chris", "ROLE_USER");
    }
}
