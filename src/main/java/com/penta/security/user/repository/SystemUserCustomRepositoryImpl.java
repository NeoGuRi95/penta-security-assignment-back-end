package com.penta.security.user.repository;

import com.penta.security.entity.QSystemUser;
import com.penta.security.entity.SystemUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SystemUserCustomRepositoryImpl implements SystemUserCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SystemUser> findByUsername(String username) {
        QSystemUser systemUser = QSystemUser.systemUser;

        return queryFactory
            .select(systemUser)
            .from(systemUser)
            .where(systemUser.userNm.eq(username))
            .fetch();
    }
}
