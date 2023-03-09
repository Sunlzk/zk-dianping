package com.hmdp;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hmdp.dto.Result;
import com.hmdp.entity.Follow;
import com.hmdp.entity.User;
import com.hmdp.service.IFollowService;
import com.hmdp.service.IUserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 姗欓洦涓�
 * @version 1.0
 * @description TODO
 * @date 2023/3/8 21:16
 */

@SpringBootTest
public class TestMy {
    @Autowired
    private IFollowService followService;
    @Resource
    private IUserService userService;

    @Test
    public void tt(){


        Long userId = 1041L;
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Follow::getFollowUserId).eq(Follow::getUserId,userId);
        List<Long> collect = followService.list(queryWrapper).stream().map(Follow::getFollowUserId).collect(Collectors.toList());
        List<User> myFollows = new ArrayList();

        for(long followId : collect){
            myFollows.add(userService.getById(followId));
        }
        for(User u : myFollows){
            System.out.println(u.toString());
        }


    }
}
