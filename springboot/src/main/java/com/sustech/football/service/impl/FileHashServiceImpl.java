package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.FileHash;
import com.sustech.football.mapper.FileHashMapper;
import com.sustech.football.service.FileHashService;
import org.springframework.stereotype.Service;

@Service
public class FileHashServiceImpl extends ServiceImpl<FileHashMapper, FileHash> implements FileHashService{
}
