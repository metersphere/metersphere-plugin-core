package io.metersphere.plugin.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.metersphere.plugin.core.utils.LogUtil;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.jmeter.save.SaveService;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "clazzName")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MsTestElement {
    // 组件类型
    @Required(message = "Type 不能为空")
    private String type;

    // 用于数据反射对象
    @Required(message = "ClazzName 不能为空")
    private String clazzName = MsTestElement.class.getCanonicalName();

    // 自身资源ID（用例ID/接口ID/场景ID)等
    @Required(message = "ID 不能为空")
    private String id;

    // 当前组件唯一标示
    @Required(message = "ResourceId 不能为空")
    private String resourceId;

    // 组件标签名称
    private String name;

    // 组件标签
    private String label;

    // 引用对象标示
    private String referenced;

    // 是否展开收起操作
    private boolean active;
    // 组件索引
    private String index;

    // 是否禁用/启用标示
    private boolean enable = true;

    // 引用对象类型（REF，Created,DELETE）
    private String refType;

    // 子组件
    private LinkedList<MsTestElement> hashTree;

    // 项目ID
    private String projectId;

    // 是否是mock环境
    private boolean isMockEnvironment;

    // 自身环境Id
    private String environmentId;

    // 插件ID
    private String pluginId;

    // 步骤别名
    private String stepName;

    // 父类
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





