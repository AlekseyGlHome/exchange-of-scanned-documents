package ru.mizer.edo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mizer.edo.model.entity.User;
import ru.mizer.edo.service.UserService;

@RequiredArgsConstructor
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByNameAndActive(username)
                .orElseThrow(()->new UsernameNotFoundException("user "+username+" not found"));
        return UserDetailsImpl.fromUser(user);
    }
}
