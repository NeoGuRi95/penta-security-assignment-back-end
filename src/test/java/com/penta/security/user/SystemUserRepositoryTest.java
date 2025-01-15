package com.penta.security.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.penta.security.user.repository.SystemUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class SystemUserRepositoryTest {

    @Autowired
    private SystemUserRepository systemUserRepository;

//    @Test
//    public void testFindAllBySearchWordPerformance() {
//        // given
//        String searchWord = "";
//        Pageable pageable = PageRequest.of(500, 100); // 페이지 요청: 100건씩 조회
//
//        // when
//        long startTime = System.currentTimeMillis(); // 시간 측정 시작
//        systemUserRepository.findPageBySearchWordWithLastUserIdx(searchWord, 49999, pageable);
//        long endTime = System.currentTimeMillis(); // 시간 측정 종료
//
//        // then
//        System.out.println("Execution Time: " + (endTime - startTime) + " ms");
//    }
}
