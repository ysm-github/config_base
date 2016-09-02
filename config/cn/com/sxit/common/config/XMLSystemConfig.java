package cn.com.sxit.common.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**  
 *     @author     yushiming
 *     @company    sxit	
 *     @version    1.0.0
 *     @since      1.0.0
 *     @date       2013-12-6
 *     @CheckItem
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="XMLSystemConfig")
public class XMLSystemConfig {
	@XmlElement(name = "ModuleConfig")
	private List<ModuleConfig> moduleConfig;

	public List<ModuleConfig> getModuleConfig() {
		return moduleConfig;
	}

	public void setModuleConfig(List<ModuleConfig> moduleConfig) {
		this.moduleConfig = moduleConfig;
	}
}
