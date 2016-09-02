package cn.com.sxit.common.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import cn.com.sxit.common.system.ServerInfo;
/**
 * 
 *     @author     yushiming
 *     @company    sxit	
 *     @version    1.0.0
 *     @since      1.0.0
 *     @date       2013-12-6
 *     @CheckItem
 */
public class SystemConfig {
	private static final Log log = LogFactory.getLog(SystemConfig.class);


	private static XMLSystemConfig xmlSystemConfig;

	private static Map<String, ModuleConfig> moduleConfigMap=new HashMap<String, ModuleConfig>();
	
	private static String filePath;

	private ScheduledExecutorService service;
	
	private static long LASTMODIFYTIME = 0;
	/**
	 * 是否支持配置文件刷新
	 */
	private static boolean refresh=false;
	/**
	 * 是否支持配置文件刷新
	 */
	private long refreshTime=0;
	
	/**
	 * @param moduleId
	 *            模块编号
	 * @return 一个模块所有的配置信息
	 */
	//public static ModuleConfig getModule(int moduleId) {
	//	return xmlSystemConfig.getModuleConfig().get(moduleId);
	//}
	/**
	 * 局部更新配置文件
	 */
	public static synchronized void updateModuleConfigMap (ModuleConfig moduleConfig,String moduleName) {
		moduleConfigMap.remove(moduleName);
		moduleConfigMap.put(moduleName, moduleConfig);
	}
	/**
	 * 全局更新配置文件
	 */
	public static synchronized void setXmlSystemConfig(XMLSystemConfig xmlSystemConfig) {
		SystemConfig.xmlSystemConfig = xmlSystemConfig;
		moduleConfigMap.clear();
	}

	/**
	 * @param moduleId
	 *            模块名称
	 * @return 一个模块所有的配置信息
	 */
	private static ModuleConfig getModule(String moduleName) {
		ModuleConfig moduleConfig = null;
		if(refresh){
			//配置文件动态更新，需要同步代码
			moduleConfig= getSynModule(moduleName);
		}else{
		moduleConfig = moduleConfigMap.get(moduleName);

		if (moduleConfig == null) {
			for (ModuleConfig mc : xmlSystemConfig.getModuleConfig()) {
				if (mc.getName().equals(moduleName)) {
					moduleConfig = mc;
					moduleConfigMap.put(moduleName, mc);
					break;
				}
			}
		}
		}
		return moduleConfig;
	}
	/**
	 * @param moduleId
	 *            模块名称
	 * @return 一个模块所有的配置信息
	 */
	public static synchronized ModuleConfig getSynModule(String moduleName) {
		ModuleConfig moduleConfig = null;
		moduleConfig = moduleConfigMap.get(moduleName);

		if (moduleConfig == null) {
			for (ModuleConfig mc : xmlSystemConfig.getModuleConfig()) {
				if (mc.getName().equals(moduleName)) {
					moduleConfig = mc;
					moduleConfigMap.put(moduleName, mc);
					break;
				}
			}
		}
		return moduleConfig;
	}
	/**
	 * 
	 * @param moduleId
	 *            模块编号
	 * @param itemId
	 *            配置项编号
	 * @return
	 * @throws ConfigException
	 */
	/*public static ConfigItem getItem(int moduleId, int itemId)
			throws ConfigException {
		try {
			ModuleConfig moduleConfig = getModule(moduleId);
			return moduleConfig.getConfigItem().get(itemId);
		} catch (Exception e) {
			throw new ConfigException(e);
		}
	}*/

	private static String getValue(String moduleName, String itemName){
		String value=null;
		ModuleConfig moduleConfig=getModule(moduleName.trim());
		if(moduleConfig!=null){
			ConfigItem item=moduleConfig.getConfigItem(itemName.trim());
			if(item!=null){
				value=item.getValue();
				if(value!=null)value=value.trim();
			}
		}
		return value;
	}
	public static String getStringValue(String moduleName, String itemName,String defaultValue) {
		
		String desc = defaultValue;
		if ((moduleName != null && !"".equals(moduleName.trim()))&&(itemName != null && !"".equals(itemName.trim())))
		{
			desc = getValue(moduleName, itemName);
			if (desc == null || "".equals(desc.trim()))
			{
				desc = defaultValue;
			}
		}
		return desc;
	}
	public static String getStringValue(String moduleName, String itemName) throws ConfigException {
		String value= getValue(moduleName, itemName);
		if(value==null){
			throw new ConfigException("Null pointer exception!");
		}
		return value;
}
	public static Integer getIntValue(String moduleName, String itemName)
			throws ConfigException {
		String desc= getValue(moduleName, itemName);
		if(desc==null){
			throw new ConfigException("Null pointer exception!");
		}
		try {
			return Integer.parseInt(desc);
		} catch (NumberFormatException e) {
			throw new ConfigException(e);
		}
	}
	public static Integer getIntValue(String moduleName, String itemName,int defaultValue){
		int value = defaultValue;
		if ((moduleName != null && !"".equals(moduleName.trim()))&&(itemName != null && !"".equals(itemName.trim())))
		{
			 String desc= getValue(moduleName, itemName);
			if (desc != null && !"".equals(desc.trim()))
			{
				try {
					value = Integer.parseInt(desc);
				} catch (NumberFormatException e) {
				}
			}
		}
		return value;
}
	public static Long getLongValue(String moduleName, String itemName) throws ConfigException {
		String desc= getValue(moduleName, itemName);
		if(desc==null){
			throw new ConfigException("Null pointer exception!");
		}
		try {
			return Long.parseLong(desc);
		} catch (NumberFormatException e) {
			throw new ConfigException(e);
		}
	}
	public static Long getLongValue(String moduleName, String itemName,long defaultValue){
		long value = defaultValue;
		if ((moduleName != null && !"".equals(moduleName.trim()))&&(itemName != null && !"".equals(itemName.trim())))
		{
			 String desc= getValue(moduleName, itemName);
			if (desc != null && !"".equals(desc.trim()))
			{
				try {
					value = Long.parseLong(desc);
				} catch (NumberFormatException e) {
				}
			}
		}
		return value;
	}
	public static Double getDoubleValue(String moduleName, String itemName) throws ConfigException {
		String desc= getValue(moduleName, itemName);
		if(desc==null){
			throw new ConfigException("Null pointer exception!");
		}
		try {
			return Double.parseDouble(desc);
		} catch (NumberFormatException e) {
			throw new ConfigException(e);
		}
	}
	public static Double getDoubleValue(String moduleName, String itemName,double defaultValue){
		double value = defaultValue;
		if ((moduleName != null && !"".equals(moduleName.trim()))&&(itemName != null && !"".equals(itemName.trim())))
		{
			 String desc= getValue(moduleName, itemName);
			if (desc != null && !"".equals(desc.trim()))
			{
				try {
					value = Long.parseLong(desc);
				} catch (NumberFormatException e) {
				}
			}
		}
		return value;
	}
	public SystemConfig() {}
	public SystemConfig(ScheduledExecutorService service) {
		this.service=service;
	}
	/**
	 * 动态刷新配置文件
	 *
	 */
	private void task(){
		if(log.isDebugEnabled()){
			log.debug("check configuration file");
		}
		XMLSystemConfig xmlSystemConfig = null;
		try {
			File file = new File(getFilePath());
			if (file.lastModified() > SystemConfig.LASTMODIFYTIME) {
				if(log.isDebugEnabled()){
					log.debug("configuration file is modified[ lastModifiedtime:"+SystemConfig.LASTMODIFYTIME+",now:"+file.lastModified()+" ],update config...");
				}
				xmlSystemConfig = SystemConfig.init_reload();
				SystemConfig.setXmlSystemConfig(xmlSystemConfig);
				if(log.isDebugEnabled()){
					log.debug("Successfully updated in the cache configuration information");
				}
			}
		} catch (Exception e) {
			log.error(
					"update configuration["+getFilePath()+"] error, please check the configuration!",
					e);
		}
	}
	public class ReloadConfigTimerTask extends TimerTask {
		@Override
		public void run() {
			task();
		}
	}
	private class ReloadConfigRunnable implements  Runnable{
		public void run() {
			task();
		} 

	}
	public void init() throws ConfigException {
		String systemConfigFileName=ServerInfo.getInitParam("system.config.path");
		if(systemConfigFileName!=null&&!"".equals(systemConfigFileName)){
			ServerInfo.setAppSystemConfigFileName(systemConfigFileName);
		}
		SystemConfig.refresh=Boolean.valueOf(ServerInfo.getInitParam("system.refresh","false"));
		this.refreshTime=Long.valueOf(ServerInfo.getInitParam("system.interval","0"));
		if(SystemConfig.refresh){
			//设置更新线程
			if(this.service!=null){
				service.scheduleWithFixedDelay(new ReloadConfigRunnable(), 10000, refreshTime, TimeUnit.MILLISECONDS);
			}else{
				
				new Timer().schedule(new ReloadConfigTimerTask(), 10000, refreshTime);
			}
		}
		filePath= ServerInfo.getAppSystemConfigPath();
		log.info("Init system config!(logRefresh=" + SystemConfig.refresh
				+ ",logRefreshTime=" + refreshTime + ")");
		unmarshaller(true);
	}
	public static XMLSystemConfig init_reload() throws ConfigException {
		return unmarshaller(false);
	}

	private static XMLSystemConfig unmarshaller(boolean first) throws ConfigException {
		log.info("Load configfile:" + filePath);
		XMLSystemConfig xmlSystemConfig = null;
		try {
			JAXBContext context = JAXBContext
					.newInstance(XMLSystemConfig.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			File file = new File(filePath);
			SystemConfig.LASTMODIFYTIME = file.lastModified();
			xmlSystemConfig = (XMLSystemConfig) unmarshaller.unmarshal(file);
			if (first) {
				SystemConfig.xmlSystemConfig = xmlSystemConfig;
				return null;
			}
		} catch (JAXBException e) {
			throw new ConfigException("Analytic configuration file["
					+ ServerInfo.getAppSystemConfigPath() + "] failure!!!", e);
		}
		return xmlSystemConfig;
	}

	public static void main(String[] args) {
		String rootPath = new File("").getAbsolutePath();
		System.out.println(rootPath);
	}

	public static String getFilePath() {
		return filePath;
	}
	public static long getLastModifyTime(){
		return SystemConfig.LASTMODIFYTIME;
	}

}
