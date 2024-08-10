package com.blockchainenergy.trade.tradeInfo;

import com.blockchainenergy.common.page.PageParam;
import lombok.Data;

@Data
public class GetTradeDto extends PageParam {
    private Integer pageNum;//页码
    private Integer pageSize;//每页记录数
    private Integer default_id;
}
