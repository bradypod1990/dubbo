package com.feng.jetty;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;

import org.apache.log4j.Logger;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.util.FileCopyUtils;


public class JettyMain {
	private static final Logger log = Logger.getLogger(JettyMain.class);
	private static String resouceBase = "src/main/webapp";
	private static String webXml  = "";
	private static String contextPath = "/dubbo";
	private static Server server = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerConnector connector = null;
		try{
			File webINF =new File(resouceBase+File.separator+"WEB-INF");
			File webxmlFile =new File(webINF,"web.xml");
			webXml=webxmlFile.getAbsolutePath();
			QueuedThreadPool threadPool = new QueuedThreadPool();
			threadPool.setMaxThreads(100);
			threadPool.setMinThreads(10);
			threadPool.setIdleTimeout(1000);

			// 创建上下文
			server = new Server(threadPool);

			// JMX
			// Setup JMX
			MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
			server.addEventListener(mbContainer);
			server.addBean(mbContainer);
			// Add loggers MBean to server (will be picked up by MBeanContainer
			// above)
			server.addBean(Log.getLog());

			WebAppContext context = new WebAppContext();

			// 开放端口
			connector = new ServerConnector(server);
			connector.setPort(8081);
			server.setConnectors(new Connector[] { connector });
			// 服务
			context.setContextPath(contextPath);
			context.setTempDirectory(new File("temp"));
			context.setDescriptor(webXml);
			context.setResourceBase(resouceBase);
			context.setDefaultsDescriptor("./webdefault.xml");
			//设置form表单的最大值，form表单过大时，可以提交
			//最大提交
			context.setMaxFormContentSize(5*1024*1024);

			Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
			classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");
			context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");
			server.setHandler(context);
			server.start();
			server.join();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
