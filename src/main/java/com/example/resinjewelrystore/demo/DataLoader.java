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

        userService.UserService(new User("John Doe", "john", "1234"));
        userService.UserService(new User("James Smith", "james", "1234"));
        userService.UserService(new User("Jane Carry", "jane", "1234"));
        userService.UserService(new User("Chris Anderson", "chris", "1234"));

        roleService.addRoleToUser("john", "ROLE_USER");
        roleService.addRoleToUser("james", "ROLE_ADMIN");
        roleService.addRoleToUser("jane", "ROLE_USER");
        roleService.addRoleToUser("chris", "ROLE_ADMIN");
        roleService.addRoleToUser("chris", "ROLE_USER");
    }
}
