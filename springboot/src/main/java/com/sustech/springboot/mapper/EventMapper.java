
package com.sustech.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.springboot.entity.Event;
import org.apache.ibatis.annotations.Mapper;


public interface EventMapper extends MppBaseMapper<Event>
{
}