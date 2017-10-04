package com.thinkgem.jeesite.mapper;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.vo.AccessToken;
import com.thinkgem.jeesite.vo.AccessTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface AccessTokenMapper {
    int countByExample(AccessTokenExample example);

    int deleteByExample(AccessTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccessToken record);

    int insertSelective(AccessToken record);

    List<AccessToken> selectByExample(AccessTokenExample example);

    AccessToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccessToken record, @Param("example") AccessTokenExample example);

    int updateByExample(@Param("record") AccessToken record, @Param("example") AccessTokenExample example);

    int updateByPrimaryKeySelective(AccessToken record);

    int updateByPrimaryKey(AccessToken record);
}