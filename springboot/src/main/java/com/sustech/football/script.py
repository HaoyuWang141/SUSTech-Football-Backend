import os

def get_filenames_in_directory(directory):
    """ 获取指定文件夹中的所有文件名（不包括子文件夹中的文件） """
    filenames = []
    for item in os.listdir(directory):
        if os.path.isfile(os.path.join(directory, item)):
            filenames.append(item)
    return filenames

# 指定文件夹路径
directory_path = './entity'

# 获取文件名列表
filenames = get_filenames_in_directory(directory_path)
print(filenames)

for filename in filenames:
    entityName = filename.split('.')[0]
    str = f"""
package com.sustech.football.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.football.entity.{entityName};

public interface {entityName}Mapper extends BaseMapper<{entityName}> 
"""
    str = str + '{\n}'
    with open(f'./mapper/{entityName}Mapper.java', 'w') as f:
        f.write(str)
        
    str = f"""
package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.{entityName};

public interface {entityName}Service extends IService<{entityName}>
"""
    str = str + '{\n}'
    with open(f'./service/{entityName}Service.java', 'w') as f:
        f.write(str)
        
    str = f"""
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.{entityName};
import com.sustech.football.mapper.{entityName}Mapper;
import com.sustech.football.service.{entityName}Service;
import org.springframework.stereotype.Service;

@Service
public class {entityName}ServiceImpl extends ServiceImpl<{entityName}Mapper, {entityName}> implements {entityName}Service 
"""
    str = str + '{\n}'
    with open(f'./service/impl/{entityName}ServiceImpl.java', 'w') as f:
        f.write(str)
