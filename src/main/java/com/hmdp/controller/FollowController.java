package com.hmdp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Follow;
import com.hmdp.entity.User;
import com.hmdp.service.IFollowService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Resource
    private IFollowService followService;

    @Resource
    private IUserService userService;

    @PutMapping("/{id}/{isFollow}")
    public Result follow(@PathVariable("id") Long followUserId,@PathVariable("isFollow") Boolean isFollow){
        return followService.follow(followUserId,isFollow);
    }

    @GetMapping("/or/not/{id}")
    public Result isFollow(@PathVariable("id") Long followUserId){
        return followService.isFollow(followUserId);
    }

    @GetMapping("/common/{id}")
    public Result followCommons(@PathVariable("id") Long id){
        return followService.followCommons(id);
    }

    @GetMapping("/myIdols")
    public Result queryMyIdols(){

        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Follow::getFollowUserId).eq(Follow::getUserId, userId);
        List<Long> collect = followService.list(queryWrapper).stream().map(Follow::getFollowUserId).collect(Collectors.toList());
        List<User> myIdols = new ArrayList();

        for (long followId : collect) {
            myIdols.add(userService.getById(followId));
        }
        return Result.ok(myIdols);
    }

    @GetMapping("/myFollowers")
    public Result queryMyFollow(){

        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Follow::getUserId).eq(Follow::getFollowUserId, userId);
        List<Long> collect = followService.list(queryWrapper).stream().map(Follow::getUserId).collect(Collectors.toList());
        List<User> myFollows  = new ArrayList();

        for (long followId : collect) {
            myFollows.add(userService.getById(followId));
        }

        return Result.ok(myFollows);
    }
}
