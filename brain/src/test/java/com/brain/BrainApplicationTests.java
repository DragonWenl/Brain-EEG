package com.brain;

import com.brain.dao.UserDao;
import com.brain.model.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
class BrainApplicationTests {

    @Resource
    UserDao dao ;
    @Test
    public void contextLoads() {
        SysUser user = new SysUser();
        user.setId(220183222L);
        user.setUsername("alex-s");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        user.setEmail("220183222@qq.com");
        user.setTelephone("17839973876");
        user.setSex("男");
        user.setStatus(3);
        dao.save(user);
         //dao.changePassword(new Long(1), new BCryptPasswordEncoder().encode("admin"));
    }

}
