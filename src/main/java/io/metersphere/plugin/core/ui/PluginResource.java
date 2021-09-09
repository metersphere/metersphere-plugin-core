package io.metersphere.plugin.core.ui;

import lombok.Data;

import java.util.List;

@Data
public class PluginResource {
    /**
     * jar唯一标识且不变，每次上传jar通过它更新
     */
    private String pluginId;
    /**
     * UI脚本，一个UI步骤一条
     */
    private List<UiScript> uiScripts;

    public PluginResource() {
    }

    public PluginResource(String pluginId, List<UiScript> uiScripts) {
        this.pluginId = pluginId;
        this.uiScripts = uiScripts;
    }
}
