package cn.com.sxit.common.system;

import cn.com.sxit.common.config.ConfigException;
import cn.com.sxit.common.config.SystemConfig;
import cn.com.sxit.common.version.VersionStart;
import java.io.FileInputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class StartupServlet extends HttpServlet
{
private static final long serialVersionUID = -2782533675875196337L;
private Log log = LogFactory.getLog(StartupServlet.class);

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ServerInfo.setWebApp(true);
    try
    {
      String appName = config.getServletContext().getContextPath().substring(1);
      ServerInfo.setAppName(appName);
      System.out.println("appName = " + ServerInfo.getAppName());
      String initFilePath = ServerInfo.getAppInitConfigPath();
      System.out.println("initFilePath:" + initFilePath);
      ServerInfo.getInitProperty().load(new FileInputStream(initFilePath));
      String newAppName = ServerInfo.getInitParam("system.appname");
      if ((newAppName != null) && (!"".equals(newAppName))) {
        ServerInfo.setAppName(newAppName);
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }

    ServerInfo.setAppRootPath(config.getServletContext().getRealPath("/"));
    System.out.println("serverInfo.appRootPath = " + 
      ServerInfo.getAppRootPath());

    String logFileName = ServerInfo.getInitParam("log.config.path");
    if ((logFileName != null) && (!"".equals(logFileName))) {
      ServerInfo.setAppLogConfigFileName(logFileName);
    }
    logConfigure(ServerInfo.getAppLogConfigPath());
    try
    {
      new SystemConfig().init();

      VersionStart.show();
      this.log.info("SystemConfig success finish!");
    } catch (ConfigException e) {
      this.log.error(e.getMessage());
      throw new ServletException(e);
    }
    this.log.debug("System startup success!");
  }

  private void logConfigure(String log4jConfigPath) {
    System.out.println("log4jConfigPath = " + log4jConfigPath);
    try {
      boolean logFresh = Boolean.valueOf(ServerInfo.getInitParam("log.refresh", "false")).booleanValue();
      System.out.println("logRefresh = " + logFresh);
      long logRefreshTime = Long.valueOf(ServerInfo.getInitParam("log.interval", "0")).longValue();
      System.out.println("logRefreshTime = " + logRefreshTime);
      if (logFresh) {
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
      this.log.info("Init log config finished!(logRefresh=" + logFresh + 
        ",logRefreshTime=" + logRefreshTime + ")");
    } catch (Exception e) {
      System.out.println("Init log config failed!");
      this.log.error(e);
    }
  }
}