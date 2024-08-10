package com.blockchainenergy.trade.release;

import com.blockchainenergy.common.page.PageParam;
import lombok.Data;

@Data
public class GetReleaseDto extends PageParam {
    private Integer pageNum;//页码
    private Integer pageSize;//每页记录数
    private Integer release_state_id;
    private Integer trade_type;
}
