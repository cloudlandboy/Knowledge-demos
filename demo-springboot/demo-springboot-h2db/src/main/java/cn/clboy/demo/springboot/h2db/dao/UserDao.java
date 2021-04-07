package cn.clboy.demo.springboot.h2db.dao;

import cn.clboy.demo.springboot.h2db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author clboy
 * @Date 2021/3/22 下午3:13
 * @Since 1.0.0
 */

public interface UserDao extends JpaRepository<User, Integer> {

}