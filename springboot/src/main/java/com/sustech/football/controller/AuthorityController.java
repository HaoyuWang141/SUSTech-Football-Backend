package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.football.entity.FirstLevelAuthority;
import com.sustech.football.entity.SecondLevelAuthority;
import com.sustech.football.entity.ThirdLevelAuthority;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.service.FirstLevelAuthorityService;
import com.sustech.football.service.SecondLevelAuthorityService;
import com.sustech.football.service.ThirdLevelAuthorityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/authority")
@Tag(name = "Authority Controller", description = "三级权限制相关的接口")
public class AuthorityController {
    @Autowired
    private FirstLevelAuthorityService firstLevelAuthorityService;
    @Autowired
    private SecondLevelAuthorityService secondLevelAuthorityService;
    @Autowired
    private ThirdLevelAuthorityService thirdLevelAuthorityService;


    @PostMapping("/check/first")
    public boolean checkFirstAuthority(@RequestBody FirstLevelAuthority firstLevelAuthority) {
        if (firstLevelAuthority == null) {
            throw new BadRequestException("传入权限为空");
        }
        if (firstLevelAuthority.getUsername() == null || firstLevelAuthority.getUsername().isEmpty()) {
            throw new BadRequestException("传入权限的用户名为空");
        }
        if (firstLevelAuthority.getPassword() == null || firstLevelAuthority.getPassword().isEmpty()) {
            throw new BadRequestException("传入权限的密码为空");
        }
        QueryWrapper<FirstLevelAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", firstLevelAuthority.getUsername());
        queryWrapper.eq("password", firstLevelAuthority.getPassword());
        return firstLevelAuthorityService.count(queryWrapper) > 0;
    }

    @PostMapping("/check/second")
    public int checkSecondAuthority(@RequestBody SecondLevelAuthority secondLevelAuthority) {
        if (secondLevelAuthority == null) {
            throw new BadRequestException("传入权限为空");
        }
        if (secondLevelAuthority.getUsername() == null || secondLevelAuthority.getUsername().isEmpty()) {
            throw new BadRequestException("传入权限的用户名为空");
        }
        if (secondLevelAuthority.getPassword() == null || secondLevelAuthority.getPassword().isEmpty()) {
            throw new BadRequestException("传入权限的密码为空");
        }
        QueryWrapper<SecondLevelAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", secondLevelAuthority.getUsername());
        queryWrapper.eq("password", secondLevelAuthority.getPassword());
        if (secondLevelAuthorityService.count(queryWrapper) > 0) {
            return secondLevelAuthorityService.getOne(queryWrapper).getAuthorityId().intValue();
        } else {
            return -1;
        }
    }

    @PostMapping("/check/third")
    public boolean checkThirdAuthority(Long userId) {
        if (userId == null) {
            throw new BadRequestException("传入权限的ID为空");
        }
        QueryWrapper<ThirdLevelAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return thirdLevelAuthorityService.count(queryWrapper) > 0;
    }

    @PutMapping("/first/update")
    public void updateFirstAuthority(@RequestBody FirstLevelAuthority firstLevelAuthority) {
        if (firstLevelAuthority == null) {
            throw new BadRequestException("传入权限为空");
        }
        if (firstLevelAuthority.getUsername() == null || firstLevelAuthority.getUsername().isEmpty()) {
            throw new BadRequestException("传入权限的用户名为空");
        }
        if (firstLevelAuthority.getPassword() == null || firstLevelAuthority.getPassword().isEmpty()) {
            throw new BadRequestException("传入权限的密码为空");
        }
        if (!firstLevelAuthorityService.updateById(firstLevelAuthority)) {
            throw new BadRequestException("更新权限失败");
        }
    }

    @PostMapping("/secondAuthority/create")
    public void createSecondAuthority(@RequestBody SecondLevelAuthority secondLevelAuthority) {
        if (secondLevelAuthority == null) {
            throw new BadRequestException("传入权限为空");
        }
        if (secondLevelAuthority.getAuthorityId() != null) {
            throw new BadRequestException("传入权限的ID不为空");
        }
        if (!secondLevelAuthorityService.save(secondLevelAuthority)) {
            throw new BadRequestException("创建权限失败");
        }
    }

    @GetMapping("/secondAuthority/getAll")
    public List<SecondLevelAuthority> getAllSecondAuthority() {
        return secondLevelAuthorityService.list()
                .stream()
                .sorted(Comparator.comparing(SecondLevelAuthority::getAuthorityId))
                .toList();
    }

    @PutMapping("/secondAuthority/update")
    public void updateSecondAuthority(@RequestBody SecondLevelAuthority secondLevelAuthority) {
        if (secondLevelAuthority == null) {
            throw new BadRequestException("传入权限为空");
        }
        if (secondLevelAuthority.getAuthorityId() == null) {
            throw new BadRequestException("传入权限的ID为空");
        }
        if (secondLevelAuthority.getUsername() == null || secondLevelAuthority.getUsername().isEmpty()) {
            throw new BadRequestException("传入权限的用户名为空");
        }
        if (secondLevelAuthority.getPassword() == null || secondLevelAuthority.getPassword().isEmpty()) {
            throw new BadRequestException("传入权限的密码为空");
        }
        SecondLevelAuthority old = secondLevelAuthorityService.getById(secondLevelAuthority.getAuthorityId());
        if (old == null) {
            throw new ResourceNotFoundException("传入权限的ID不存在");
        }
        secondLevelAuthority.setCreateUserId(old.getCreateUserId());
        if (!secondLevelAuthorityService.updateById(secondLevelAuthority)) {
            throw new BadRequestException("更新权限失败");
        }
    }

    @DeleteMapping("/secondAuthority/delete")
    public void deleteSecondAuthority(Long authorityId) {
        if (authorityId == null) {
            throw new BadRequestException("传入权限的ID为空");
        }
        if (!secondLevelAuthorityService.removeById(authorityId)) {
            throw new BadRequestException("删除权限失败");
        }
    }

    @PostMapping("thirdAuthority/create")
    public void createThirdAuthority(@RequestBody ThirdLevelAuthority thirdLevelAuthority) {
        if (thirdLevelAuthority == null) {
            throw new BadRequestException("传入权限为空");
        }
        if (thirdLevelAuthority.getAuthorityId() != null) {
            throw new BadRequestException("传入权限的ID不为空");
        }
        if (!thirdLevelAuthorityService.save(thirdLevelAuthority)) {
            throw new BadRequestException("创建权限失败");
        }
    }

    @GetMapping("thirdAuthority/getAll")
    public List<ThirdLevelAuthority> getAllThirdAuthority() {
        return thirdLevelAuthorityService.list()
                .stream()
                .sorted(Comparator.comparing(ThirdLevelAuthority::getAuthorityId))
                .toList();
    }

    @GetMapping("thirdAuthority/getBySecond")
    public List<ThirdLevelAuthority> getThirdAuthorityBySecond(Long authorityId) {
        if (authorityId == null) {
            throw new BadRequestException("传入权限的ID为空");
        }
        QueryWrapper<ThirdLevelAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("second_level_authority_id", authorityId);
        return thirdLevelAuthorityService.list(queryWrapper);
    }

    @DeleteMapping("thirdAuthority/delete")
    public void deleteThirdAuthority(Long authorityId) {
        if (authorityId == null) {
            throw new BadRequestException("传入权限的ID为空");
        }
        if (!thirdLevelAuthorityService.removeById(authorityId)) {
            throw new BadRequestException("删除权限失败");
        }
    }
}
