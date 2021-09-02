package io.metersphere.plugin.core.ui;

import lombok.Data;

@Data
public class UiScript {
    /**
     * 脚本唯一标识
     */
    private String id;
    /**
     * 显示名称
     */
    private String name;
    /**
     * 这个参数非常重要，对应继承 MsTestElement 的类全名
     * 如：io.metersphere.plugin.example.sampler.MsThriftSample
     */
    private String clazzName;
    /**
     * 表单基本参数
     * 这个可选/不填走默认值
     */
    private String formOption;

    /**
     * 表单脚本内容
     */
    private String formScript;

    public UiScript(String id, String name, String clazzName, String script) {
        this.id = id;
        this.name = name;
        this.clazzName = clazzName;
        this.formScript = script;
    }
}
