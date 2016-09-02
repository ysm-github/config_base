package cn.com.sxit.common.config;


/**    系统配置异常类
 *     @author     yushiming
 *     @company    sxit	
 *     @version    1.0.0
 *     @since      1.0.0
 *     @date       2013-12-6
 *     @CheckItem
 */
public class ConfigException extends Exception {

	private static final long serialVersionUID = -7601134512212580323L;

	public ConfigException(String name) {
		super(name);
	}

	public ConfigException() {
		super();
	}

	public ConfigException(String name, Throwable e) {
		super(name, e);
	}

	public ConfigException(Throwable e) {
		super(e);
	}
}
