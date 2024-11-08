package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.exception.UserNotFoundException;
import com.code.fullstack_backend.model.User;
import com.code.fullstack_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Endpoint para adicionar um novo usuário
    @PostMapping("/user")
    public User newUser(@RequestBody User newUser) {
        // Verifica se o RG já existe no banco de dados
        if (userRepository.existsByRg(newUser.getRg())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RG já cadastrado!"); // Lança exceção caso o RG já exista
        }
        return userRepository.save(newUser);
    }

    // Endpoint para verificar se o RG já existe
    @GetMapping("/user/rg/{rg}")
    public boolean checkRgExists(@PathVariable String rg) {
        return userRepository.existsByRg(rg); // Verifica se o RG existe no banco
    }

    // Endpoint para pegar todos os usuários
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Endpoint para pegar um usuário pelo ID
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    // Endpoint para atualizar um usuário pelo ID
    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setNeighborhood(newUser.getNeighborhood());
                    user.setCity(newUser.getCity());
                    user.setRg(newUser.getRg());
                    user.setStreet(newUser.getStreet());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // Endpoint para deletar um usuário pelo ID
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted successfully";
    }
}
