package com.blockchainenergy.info.user.dto;

import com.blockchainenergy.common.page.PageParam;
import lombok.Data;

@Data
public class GetUserTimeSlotDto extends PageParam {
    private Integer pageNum;//页码
    private Integer pageSize;//每页记录数
    private String release_id;
}
