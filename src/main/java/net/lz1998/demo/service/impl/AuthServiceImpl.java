package net.lz1998.demo.service.impl;

import net.lz1998.demo.entity.Auth;
import net.lz1998.demo.repository.AuthRepository;
import net.lz1998.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Override
    public Boolean isAuth(Long groupId) {
        Auth auth = authRepository.findAuthByGroupId(groupId);
        return auth != null && auth.getIsAuth();
    }

    @Override
    public Boolean setAuth(Long groupId, Boolean isAuth, Long adminId) {
        Auth auth = Auth.builder()
                .groupId(groupId)
                .isAuth(isAuth)
                .adminId(adminId)
                .build();
        return authRepository.save(auth).getIsAuth();
    }

}
