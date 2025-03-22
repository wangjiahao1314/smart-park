package com.ruoyi.park.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.park.domain.entity.ParkContract;
import com.ruoyi.park.domain.vo.contract.LatestContractInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ParkContractMapper extends BaseMapper<ParkContract> {

    /**
     * 通过房间ID查询合同类型
     *
     * @param roomId
     * @return
     */
    default List<String> selectContractTypeByRoomId(Long roomId) {
        LambdaQueryWrapper<ParkContract> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ParkContract::getRoomId, roomId)
                .eq(ParkContract::getDelFlag, UserStatus.OK.getCode());
        return selectList(lqw).stream().map(ParkContract::getContractType).collect(Collectors.toList());
    }

    /**
     * 查询当前房间的最新合同信息
     *
     * @param roomId
     * @return 天数
     */
    @Select("SELECT\n" +
            "\tupdate_time,\n" +
            "\tcontract_type \n" +
            "FROM\n" +
            "\tpark_contract \n" +
            "WHERE\n" +
            "\troom_id = #{roomId} \n" +
            "ORDER BY\n" +
            "\tcreate_time DESC \n" +
            "\tLIMIT 1")
    LatestContractInfo selectLatestContractInfo(Long roomId);
}
