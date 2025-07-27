package com.example.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<DemoModel>> getAllUsers() {
        List<DemoModel> users = demoService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get")
    public ResponseEntity<DemoModel> getDemoModelById(@RequestParam Integer Id) {
        Optional<DemoModel> demoModel = demoService.getUserById(Id);
        return demoModel.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody DemoModel demoModel) {
        DemoModel savedUser = demoService.addUser(demoModel);
        if (savedUser != null) {
            return ResponseEntity.ok("User created successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to create user.");
        }
    }
}
