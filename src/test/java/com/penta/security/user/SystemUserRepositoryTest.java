package com.penta.security.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.penta.security.global.entity.SystemUser;
import com.penta.security.user.repository.SystemUserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class SystemUserRepositoryTest {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Test
    public void findByUsernameTest() {
        // given
        String username = "testNm";
        SystemUser systemUser = SystemUser.builder()
            .userId("testId")
            .userNm(username)
            .userPw("testPw")
            .userAuth("testAuth")
            .build();
        systemUserRepository.save(systemUser);

        // when
        List<SystemUser> systemUsers = systemUserRepository.findByUsername(username);

        // then
        assertThat(systemUsers).contains(systemUser);
    }
}
