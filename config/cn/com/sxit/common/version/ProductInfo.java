package cn.com.sxit.common.version;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


/**  
 *     @author     yushiming
 *     @company    sxit	
 *     @version    1.0.0
 *     @since      1.0.0
 *     @date       2013-12-6
 *     @CheckItem
 */
public class ProductInfo {
	private static final Logger log=Logger.getLogger(ProductInfo.class);
    /**
     * 初始化参数由SystemConfigFilter加载，
     * 参数值来自web.xml文件中SystemConfigFilter的初始化参数。
     */
    private static  String versionFile = "version.txt" ;

    private static Properties versionInfo = null ;
    
    public ProductInfo ()
    {
    }

	public static void load()
    {
        versionInfo = new Properties() ;
        try
        {
            versionInfo.load(ProductInfo.class.getClassLoader().getResourceAsStream(versionFile)) ;
        }
        catch (FileNotFoundException e)
        {
           log.warn("没有找到类路径下version文件：" + versionFile,e);
        }
        catch (IOException e)
        {
        	log.warn("读取类路径下version文件错误：" + versionFile,e);
        }catch(Exception e){
        	log.warn("类路径下version文件不存在："+versionFile);
        }
    }
    /*
     * 补丁ID例如：MISC_PORTAL_PATCH1.6.2.001
     */

    public static String getVersionID ()
    {
        return getField(versionInfo, "VersionID") ;
    }

    /**
     * 版本升级时间  例如：2003/10/08 00:30
     * @return String
     */
    public static String getUpdateTime ()
    {
        return getField(versionInfo, "UpdateTime") ;
    }

    /**
     * 版权信息  
     * @return String
     */
    public static String getOwnerInfo ()
    {
        return getField(versionInfo, "OwnerInfo") ;
    }

    /**
     * 版权的声明信息
     * @return String
     */
    public static String getWarnInfo ()
    {

        return getField(versionInfo, "WarnInfo") ;
    }

    /**
     * @param prop Properties
     * @param key String
     * @return String
     */
    public static String getField (Properties prop, String key)
    {
        if (prop == null)
        {
            return "" ;
        }
        String field = prop.getProperty(key) ;
        if (field == null)
        {
//            logger.error("版本/Patch信息有误.具体错误在：" + key) ;
            field = "[none]" ;
        }
        return field ;
    }

}
