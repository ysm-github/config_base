package cn.com.sxit.common.system;

import cn.com.sxit.common.config.ConfigException;
import cn.com.sxit.common.config.SystemConfig;
import cn.com.sxit.common.version.VersionStart;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class StartUp
{
  private static final Log log = LogFactory.getLog(StartUp.class);

  public static void init()
    throws ConfigException
  {
    ServerInfo.setWebApp(false);
    try
    {
      String initFilePath = ServerInfo.getAppInitConfigPath();
      System.out.println("initFilePath:" + initFilePath);
      ServerInfo.getInitProperty().load(new FileInputStream(initFilePath));

      ServerInfo.setAppRootPath(ServerInfo.USER_DIR);
      System.out.println("serverInfo.appRootPath = " + ServerInfo.getAppRootPath());

      String appName = ServerInfo.getInitParam("system.appname", ServerInfo.USER_DIR.substring(ServerInfo.USER_DIR.lastIndexOf(File.separatorChar) + 1));
      ServerInfo.setAppName(appName);
      System.out.println("serverInfo.appName = " + ServerInfo.getAppName());

      String logFileName = ServerInfo.getInitParam("log.config.path");
      if ((logFileName != null) && (!"".equals(logFileName))) {
        ServerInfo.setAppLogConfigFileName(logFileName);
      }
      logConfigure(ServerInfo.getAppLogConfigPath());

      new SystemConfig().init();
      log.debug("Init SystemConfig success finished!");

      VersionStart.show();
      log.debug("System startup success!");
    } catch (Exception e) {
      throw new ConfigException("System startup fail!", e);
    }
  }

  private static void logConfigure(String log4jConfigPath) {
    System.out.println("log4jConfigPath = " + log4jConfigPath);
    try {
      boolean logFresh = Boolean.valueOf(ServerInfo.getInitParam("log.refresh", "false")).booleanValue();
      System.out.println("logRefresh = " + logFresh);
      long logRefreshTime = Long.valueOf(ServerInfo.getInitParam("log.interval", "0")).longValue();
      if (logFresh) {
    	  System.out.println("logRefreshTime = " + logRefreshTime);
        System.out.println("logRefreshTime = " + logRefreshTime);

        if (log4jConfigPath.endsWith("xml"))
          DOMConfigurator.configureAndWatch(log4jConfigPath, 
            logRefreshTime);
        else
          PropertyConfigurator.configureAndWatch(log4jConfigPath, 
            logRefreshTime);
      }
      else if (log4jConfigPath.endsWith("xml")) {
        DOMConfigurator.configure(log4jConfigPath);
      } else {
        PropertyConfigurator.configure(log4jConfigPath);
      }
      log.debug("Init log config finished!(logRefresh=" + logFresh + 
        ",logRefreshTime=" + logRefreshTime + ")");
    } catch (Exception e) {
      System.out.println("Init log config failed!");
      log.error(e);
    }
  }

  public static void main(String[] args) throws Exception {
    try {
      init();
    }
    catch (ConfigException e) {
      e.printStackTrace();
    }
  }
}