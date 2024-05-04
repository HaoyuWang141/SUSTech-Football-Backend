package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.Event;
import com.sustech.football.entity.EventStage;
import com.sustech.football.entity.EventStageTag;
import com.sustech.football.mapper.EventStageMapper;
import com.sustech.football.mapper.EventStageTagMapper;
import com.sustech.football.service.EventStageService;
import com.sustech.football.service.EventStageTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventStageServiceImpl extends MppServiceImpl<EventStageMapper, EventStage> implements EventStageService {

    @Autowired
    private EventStageTagService eventStageTagService;

    @Override
    public boolean updateStageAndTage(Event event) {
        List<Event.Stage> stage = event.getStageList();
        if (stage == null) {
            throw new RuntimeException("赛事阶段为空");
        }
        for (Event.Stage s : stage) {
            if (s.getStageName() == null) {
                throw new RuntimeException("赛事阶段名为空");
            }
            List<Event.Tag> tags = s.getTags();
            if (tags == null) {
                throw new RuntimeException("赛事标签为空");
            }
            this.save(new EventStage(event.getEventId(), s.getStageName()));
            for (Event.Tag t : tags) {
                if (t.getTagName() == null) {
                    throw new RuntimeException("赛事标签名为空");
                }
                eventStageTagService.save(new EventStageTag(event.getEventId(), s.getStageName(), t.getTagName()));
            }
        }
        return true;
    }
}
