package org.sid.web;

import org.sid.entities.AppUser;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterForm userForm){
        if(!userForm.getPassword().equals(userForm.getRepassword()))
            throw new RuntimeException("you must confirm your password");
        AppUser user=accountService.findUserByUsername(userForm.getUsername());
        if(user!=null)
            throw new RuntimeException("this user already exist");
        AppUser appUser= new AppUser();
        appUser.setUsername(userForm.getUsername());
        appUser.setPassword((userForm.getPassword()));

       accountService.saveUser(appUser);
       accountService.AddRoleToUser(userForm.getUsername(), "USER");
       return appUser;
    };
}
