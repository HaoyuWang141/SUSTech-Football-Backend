
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Player;
import com.sustech.football.mapper.PlayerMapper;
import com.sustech.football.service.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player> implements PlayerService 
{
}