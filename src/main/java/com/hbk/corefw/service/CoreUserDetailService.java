package com.hbk.corefw.service;

import com.hbk.corefw.dto.UserDetailsDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnExpression("${auth.core-user-details.required:true}")
public class CoreUserDetailService  implements UserDetailsService {

    @Override
    public UserDetailsDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
