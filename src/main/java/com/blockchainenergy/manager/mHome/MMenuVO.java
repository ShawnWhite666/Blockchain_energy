package com.blockchainenergy.manager.mHome;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/23 14:13
 */
public class MMenuVO {
    private Integer id;
    private String name;
    private String url;

    private List<MMenuVO> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MMenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MMenuVO> children) {
        this.children = children;
    }
}
