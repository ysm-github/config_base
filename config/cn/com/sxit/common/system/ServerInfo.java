package cn.com.sxit.common.system;

import java.util.Properties;
import java.util.regex.Matcher;

public class ServerInfo
{
  public static final String SYSTEM_APPNAME = "system.appname";
  public static final String FS = System.getProperty("file.separator");

  public static final String LS = System.getProperty("line.separator");

  public static final String USER_DIR = System.getProperty("user.dir");

  private static Properties initProperty = new Properties();

  private static String appName = "";

  private static String appRootPath = null;

  private static String appLogConfigFileName = "conf/log4j.xml";

  private static String appSystemConfigFileName = "conf/system-config.xml";
  private static final String appInitConfigFileName = "conf/init.properties";
//  private static String appLogConfigPath = null;
//  private static String appSystemConfigPath = null;
//  private static String appInitConfigPath = null;

  private static boolean isWebApp = true;

  public static boolean isWebApp() {
    return isWebApp;
  }

  public static void setWebApp(boolean isWebApp) {
    ServerInfo.isWebApp = isWebApp;
  }

  public static String getAppConfigPath()
  {
    return USER_DIR + FS + (isWebApp ? appName + FS : "");
  }

  public static String getAppInitConfigPath() {
    return getAppConfigPath() + formatPath(appInitConfigFileName);
  }

  public static String getAppLogConfigPath() {
    return getAppConfigPath() + formatPath(appLogConfigFileName);
  }

  public static String getAppSystemConfigPath() {
    return getAppConfigPath() + formatPath(appSystemConfigFileName);
  }
  public static String getAppConfigPath(String fileName) {
    return getAppConfigPath() + formatPath(fileName);
  }
  public static String getAppSystemConfigFileName() {
    return appSystemConfigFileName;
  }

  public static void setAppSystemConfigFileName(String appSystemConfigFileName) {
    ServerInfo.appSystemConfigFileName = appSystemConfigFileName;
  }

  public static String getAppLogConfigFileName() {
    return appLogConfigFileName;
  }

  public static void setAppLogConfigFileName(String appLogConfigFileName) {
    ServerInfo.appLogConfigFileName = appLogConfigFileName;
  }

  public static String getAppName()
  {
    return appName;
  }

  public static void setAppName(String appName) {
    ServerInfo.appName = appName;
  }

  public static String getAppRootPath() {
    return appRootPath;
  }

  public static void setAppRootPath(String appRootPath) {
    ServerInfo.appRootPath = appRootPath;
  }

  public static String formatPath(String path) {
    if ((path.endsWith("\\")) || (path.endsWith("/"))) {
      path = path.substring(0, path.length() - 1);
    }
    return path.replaceAll("\\\\|/", Matcher.quoteReplacement(FS));
  }

  public static Properties getInitProperty() {
    return initProperty;
  }

  public static void setInitProperty(Properties initProperty) {
    ServerInfo.initProperty = initProperty;
  }
  public static String getInitParam(String key) {
    return initProperty.getProperty(key);
  }
  public static String getInitParam(String key, String defaultV) {
    return initProperty.getProperty(key, defaultV);
  }
}