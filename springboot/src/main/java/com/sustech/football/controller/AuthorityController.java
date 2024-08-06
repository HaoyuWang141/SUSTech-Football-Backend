package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.football.entity.SecondLevelAuthority;
import com.sustech.football.entity.ThirdLevelAuthority;
import com.sustech.football.exception.BadRequestException;
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
    private SecondLevelAuthorityService secondLevelAuthorityService;
    @Autowired
    private ThirdLevelAuthorityService thirdLevelAuthorityService;

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
    public List<ThirdLevelAuthority> getThirdAuthorityBySecond(Long secondLevelAuthorityId) {
        if (secondLevelAuthorityId == null) {
            throw new BadRequestException("传入权限的ID为空");
        }
        QueryWrapper<ThirdLevelAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("second_level_authority_id", secondLevelAuthorityId);
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
