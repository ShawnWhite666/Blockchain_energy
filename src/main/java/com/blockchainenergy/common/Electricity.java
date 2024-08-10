package com.blockchainenergy.common;

/**
 * 电相关
 */
public class Electricity {
    public static final double GRID_PRICE = 1.0; // 上网电价，电网买电的价格
    public static final double TARIFF = 1.0;// 目录电价，电网卖给企业电的价格

    // 电网账户
    public static final String ELECTRIC_GRID_ID = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGiKNN3ZefkSvCfdl0J8GEpF0+PFHTDAaiO51/+RVPis5tFWBBdDVtFjQODy0uCfOYhOxRKKwFGVWCgDIEHinWTWUwR8+K4bc23ThltTCQupFb9RGLAlgS/w4ufBqsjJ42ClO9Gp7ewujlFlLhai3wLKRgC5xXNj9RSrllsXuf7wIDAQAB";
    public static final double[] SELL_TO_GRID_PRICE = {
            0.245, 0.286, 0.277, 0.274, 0.301, 0.306,
            0.314, 0.319, 0.342, 0.376, 0.443, 0.496,
            0.384, 0.356, 0.425, 0.453, 0.496, 0.513,
            0.567, 0.542, 0.501, 0.394, 0.351, 0.342
    };
    public static final double[] BUY_FROM_GRID_PRICE = {
            0.324, 0.356, 0.342, 0.338, 0.388, 0.374,
            0.402, 0.434, 0.475, 0.493, 0.526, 0.568,
            0.465, 0.447, 0.516, 0.534, 0.576, 0.594,
            0.648, 0.625, 0.576, 0.486, 0.423, 0.403
    };
}
