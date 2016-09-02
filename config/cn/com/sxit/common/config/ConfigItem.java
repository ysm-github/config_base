package cn.com.sxit.common.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 	   配置子项
 *     @author     yushiming
 *     @company    sxit	
 *     @version    1.0.0
 *     @since      1.0.0
 *     @date       2013-12-6
 *     @CheckItem
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigItem", propOrder = {"description","name","value" })
public class ConfigItem {
	@XmlElement(name = "Name")
	private String name;

	@XmlElement(name = "Description")
	private String description;

	@XmlElement(name = "Value")
	private String value;

	public ConfigItem(String name,String description, String value) {
		this.name = name;
		this.description = description;
		this.value = value;
	}

	public ConfigItem() {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
