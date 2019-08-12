package com.gknoone.cloud.plus.auth.service;

import com.gknoone.cloud.plus.auth.model.om.User;

/**
 * @author gknoone
 * @date 2019-08-09 10:13
 */
public interface AuthService {
    String getUser(Long id);
    int register(User userToAdd );
    String login( String username, String password );
}
