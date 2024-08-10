package com.blockchainenergy.common.page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pageDto
 */
@Data
@NoArgsConstructor
public class PageParam {
    private Integer pageNum;//页码
    private Integer pageSize;//每页记录数
}
