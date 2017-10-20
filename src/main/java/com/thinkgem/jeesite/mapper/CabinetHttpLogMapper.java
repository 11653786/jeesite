package com.thinkgem.jeesite.mapper;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.vo.CabinetHttpLog;
import com.thinkgem.jeesite.vo.CabinetHttpLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface CabinetHttpLogMapper {
    int countByExample(CabinetHttpLogExample example);

    int deleteByExample(CabinetHttpLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CabinetHttpLog record);

    int insertSelective(CabinetHttpLog record);

    List<CabinetHttpLog> selectByExample(CabinetHttpLogExample example);

    CabinetHttpLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CabinetHttpLog record, @Param("example") CabinetHttpLogExample example);

    int updateByExample(@Param("record") CabinetHttpLog record, @Param("example") CabinetHttpLogExample example);

    int updateByPrimaryKeySelective(CabinetHttpLog record);

    int updateByPrimaryKey(CabinetHttpLog record);
}