package com.blockchainenergy.security.test;

import java.util.List;

/**
 * @descriptions:
 * @data: 2021/10/23 14:13
 */
public class TestVO {
    private Integer id;
    private String name;
    private String url;

    private List<TestVO> children;

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

    public List<TestVO> getChildren() {
        return children;
    }

    public void setChildren(List<TestVO> children) {
        this.children = children;
    }
}
