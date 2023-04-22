package com.yash.ems.services;

import java.util.Set;
import com.yash.ems.model.User;
import com.yash.ems.model.UserRole;



public interface UserService {

    //creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    //get user by username
    public User getUser(String username);

}
