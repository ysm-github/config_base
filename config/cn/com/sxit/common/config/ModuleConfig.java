package cn.com.sxit.common.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.collections.CollectionUtils;

/**  
 *     模块配置项
 *     @author     yushiming
 *     @company    sxit	
 *     @version    1.0.0
 *     @since      1.0.0
 *     @date       2013-12-6
 *     @CheckItem
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ModuleConfig {
	@XmlElement(name = "ConfigItem")
	private List<ConfigItem> configItem;

	@XmlAttribute(name = "Name")
	private String name;

	@XmlAttribute(name = "Description")
	private String description;

	@XmlTransient
	private Map<String, ConfigItem> configItemMap = new HashMap<String, ConfigItem>();

	public List<ConfigItem> getConfigItem() {
		return configItem;
	}

	public void setConfigItem(List<ConfigItem> configItem) {
		this.configItem = configItem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConfigItem getConfigItem(int itemId) {
		ConfigItem configItem = null;
		if (CollectionUtils.isNotEmpty(this.getConfigItem())) {
			configItem = this.getConfigItem().get(itemId);
		}
		return configItem;
	}

	public ConfigItem getConfigItem(String configItemName) {
		ConfigItem configItem = null;
		configItem = configItemMap.get(configItemName);
		if (configItem == null) {
			if (CollectionUtils.isNotEmpty(this.getConfigItem())) {
				for (ConfigItem item : this.getConfigItem()) {
					if (configItemName.equals(item.getName())) {
						configItem = item;
						configItemMap.put(configItemName, item);
						break;
					}
				}
			}
		}
		return configItem;
	}
}
