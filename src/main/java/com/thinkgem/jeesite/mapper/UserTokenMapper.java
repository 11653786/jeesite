package com.thinkgem.jeesite.mapper;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.vo.UserToken;
import com.thinkgem.jeesite.vo.UserTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface UserTokenMapper {
    int countByExample(UserTokenExample example);

    int deleteByExample(UserTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserToken record);

    int insertSelective(UserToken record);

    List<UserToken> selectByExample(UserTokenExample example);

    UserToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserToken record, @Param("example") UserTokenExample example);

    int updateByExample(@Param("record") UserToken record, @Param("example") UserTokenExample example);

    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);
}