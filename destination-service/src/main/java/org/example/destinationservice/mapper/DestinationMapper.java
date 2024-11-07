package org.example.destinationservice.mapper;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.destinationservice.pojo.entity.Destination;

import java.util.List;

@Mapper
public interface DestinationMapper {

    @Select("select * from `tp-destinations`.`tp-destinations`where id = #{id}")
    Destination getDestinationsById(long id);
}
