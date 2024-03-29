/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.helsinedea.CRUDspringbootthymeleaf.controler;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tech.helsinedea.CRUDspringbootthymeleaf.domain.User;
import tech.helsinedea.CRUDspringbootthymeleaf.repository.UserRepository;

/**
 *
 * @author Helsine
 */
@Controller
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    @GetMapping("/signup")
    public String showSignUpForm(User user){
        return "add-user";
    }
    
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result,Model model){
        if (result.hasErrors()) {
            return "add-user";
        }
      userRepository.save(user);
      model.addAttribute("users",userRepository.findAll());
      return "index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        User user=userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid user Id :"+id));
        model.addAttribute("user",user);
        return "update-user";  
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model ){
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }
        userRepository.save(user);
        model.addAttribute("users",userRepository.findAll());
        return "index";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id")Long id, Model model){
        User user =userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid User Id :"+id));
        userRepository.delete(user);
        model.addAttribute("users",userRepository.findAll());
        return "index";
    }
}
