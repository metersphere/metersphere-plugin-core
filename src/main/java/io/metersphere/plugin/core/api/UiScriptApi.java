package io.metersphere.plugin.core.api;

import io.metersphere.plugin.core.ui.PluginResource;

public abstract class UiScriptApi {

    /**
     * 初始化页面脚本
     * 上传JAR到MeterSphere平台后会自动调用
     */
    public abstract PluginResource init();

    /**
     * 自定义方法，用于在自定义插件中初始化数据
     *
     * @param request 请求参数，任何json类型数据，配合插件脚本定义
     * @return 返回要处理的JSON数据
     */
    public abstract String customMethod(String request);
}
