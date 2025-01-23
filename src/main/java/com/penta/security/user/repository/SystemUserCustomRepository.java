package com.penta.security.user.repository;

import com.penta.security.entity.SystemUser;
import java.util.List;

public interface SystemUserCustomRepository {

    List<SystemUser> findByUsername(String username);
}
