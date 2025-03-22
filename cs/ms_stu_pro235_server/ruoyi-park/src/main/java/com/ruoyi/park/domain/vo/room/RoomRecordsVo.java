package com.ruoyi.park.domain.vo.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hezining
 * @date 2025/3/16 10:55
 * @description: 房间记录列表Vo
 */

@Data
@ApiModel(description = "房间记录视图对象")
public class RoomRecordsVo {

    @ApiModelProperty(value = "租户ID", example = "1234567890", required = true)
    private Long tenantId;

    @ApiModelProperty(value = "租户姓名", example = "张三", required = true)
    private String nickName;

    @ApiModelProperty(value = "入住时间", example = "2023-03-15 14:25:00", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty(value = "退房时间(若没退租则为空)", example = "2023-03-20 12:00:00", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    @ApiModelProperty(value = "身份证号码", example = "110101199001011234", required = true)
    private String idCardNumber;

    @ApiModelProperty(
            value = "电子邮箱",
            example = "zhangsan@example.com",
            notes = "租户的电子邮件地址，用于接收通知。",
            required = true
    )
    private String email;

    @ApiModelProperty(
            value = "手机号码",
            example = "13812345678",
            notes = "租户的联系电话，用于沟通联系。",
            required = true
    )
    private String phonenumber;

    @ApiModelProperty(
            value = "家庭区域",
            example = "北京市海淀区",
            notes = "租户的家庭所在区域。",
            required = true
    )
    private String homeArea;

    @ApiModelProperty(
            value = "详细地址",
            example = "北京市海淀区某街道某小区某号楼某单元某号",
            notes = "租户的具体居住地址。",
            required = true
    )
    private String addressDetails;

    @ApiModelProperty(value = "房屋状态（0已取消(不返回) 1在租 2退租）", example = "0", required = true)
    private String houseType;
}
