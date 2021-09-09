package io.metersphere.plugin.core;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.metersphere.plugin.core.utils.LogUtil;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.jmeter.save.SaveService;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "clazzName")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MsTestElement {
    private String type;
    private String clazzName = "io.metersphere.plugin.core.MsTestElement";
    @JSONField(ordinal = 1)
    private String id;
    @JSONField(ordinal = 2)
    private String name;
    @JSONField(ordinal = 3)
    private String label;
    @JSONField(ordinal = 4)
    private String resourceId;
    @JSONField(ordinal = 5)
    private String referenced;
    @JSONField(ordinal = 6)
    private boolean active;
    @JSONField(ordinal = 7)
    private String index;
    @JSONField(ordinal = 8)
    private boolean enable = true;
    @JSONField(ordinal = 9)
    private String refType;
    @JSONField(ordinal = 10)
    private LinkedList<MsTestElement> hashTree;
    @JSONField(ordinal = 12)
    private String projectId;
    @JSONField(ordinal = 13)
    private boolean isMockEnvironment;
    @JSONField(ordinal = 14)
    private String environmentId;
    @JSONField(ordinal = 15)
    private String pluginId;
    @JSONField(ordinal = 16)
    private String stepName;

    private MsTestElement parent;

    /**
     * todo 公共环境逐层传递，如果自身有环境 以自身引用环境为准否则以公共环境作为请求环境
     */
    public void toHashTree(HashTree tree, List<MsTestElement> hashTree, MsParameter config) {
        if (CollectionUtils.isNotEmpty(hashTree)) {
            for (MsTestElement el : hashTree) {
                el.toHashTree(tree, el.hashTree, config);
            }
        }
    }

    /**
     * 转换JMX
     *
     * @param hashTree
     * @return
     */
    public String getJmx(HashTree hashTree) {
        try (ByteArrayOutputStream bas = new ByteArrayOutputStream()) {
            SaveService.saveTree(hashTree, bas);
            return bas.toString();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.warn("HashTree error, can't log jmx scenarioDefinition");
        }
        return null;
    }

    public HashTree generateHashTree(MsParameter config) {
        HashTree jmeterTestPlanHashTree = new ListedHashTree();
        this.toHashTree(jmeterTestPlanHashTree, this.hashTree, config);
        return jmeterTestPlanHashTree;
    }
}





