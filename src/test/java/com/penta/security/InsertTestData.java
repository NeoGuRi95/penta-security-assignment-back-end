package com.penta.security;

import com.penta.security.global.entity.SystemUser;
import com.penta.security.user.repository.SystemUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class InsertTestData {

    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void insertTestData() {
        String pw = bCryptPasswordEncoder.encode("admin");
        SystemUser user = SystemUser.builder()
            .userId("admin")
            .userNm("admin")
            .userPw(pw)
            .userAuth("SYSTEM_ADMIN")
            .build();

        systemUserRepository.save(user);
    }
}
