package com.ruoyi.park.domain.vo.tenant;

import com.ruoyi.common.constant.TenantStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hezining
 * @date 2025/3/1 21:20
 * @description: 分页获取租户列表Vo
 * 该类用于封装分页查询租户列表时返回的数据结构。
 */
@Data
@ApiModel("分页获取租户列表Vo")
public class TenantListVo {

    @ApiModelProperty(
            value = "租户ID",
            example = "1",
            notes = "租户的唯一标识符。"
    )
    private Long tenantId;

    @ApiModelProperty(
            value = "租户昵称",
            example = "张三",
            notes = "租户的昵称或姓名，用于展示。"
    )
    private String nickName;

    @ApiModelProperty(
            value = "身份证号",
            example = "110101199001011234",
            notes = "租户的身份证号码，用于身份验证。"
    )
    private String idCardNumber;

    @ApiModelProperty(
            value = "电子邮箱",
            example = "zhangsan@example.com",
            notes = "租户的电子邮件地址，用于接收通知。"
    )
    private String email;

    @ApiModelProperty(
            value = "手机号码",
            example = "13812345678",
            notes = "租户的联系电话，用于沟通联系。"
    )
    private String phonenumber;

    @ApiModelProperty(
            value = "家庭区域",
            example = "北京市海淀区",
            notes = "租户的家庭所在区域。"
    )
    private String homeArea;

    @ApiModelProperty(
            value = "详细地址",
            example = "北京市海淀区某街道某小区某号楼某单元某号",
            notes = "租户的具体居住地址。"
    )
    private String addressDetails;

    /* 默认未租 */
    @ApiModelProperty(
            value = "租户状态（0在租 1未租）",
            example = "正常",
            notes = "在租，未租"
    )
    private String tenantStatus = TenantStatus.NOT_RENTED;

    @ApiModelProperty(
            value = "正在租赁的房间列表",
            example = "[\"房间A\", \"房间B\"]",
            notes = "租户当前正在租赁的房间列表，可能为空。"
    )
    private List<TenantRentingRoomVo> RentingRooms;
}