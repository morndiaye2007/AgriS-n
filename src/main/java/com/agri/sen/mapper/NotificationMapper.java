package com.agri.sen.mapper;

import com.agri.sen.entity.Notification;
import com.agri.sen.model.NotificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification>{
}
