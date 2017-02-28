

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.feng.dubbo.service.FileUploadService;
import com.feng.dubbo.service.TestService;

public class Customer {

	@Test
	public void main() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:customer.xml"});
		TestService ts = (TestService) context.getBean("testService");
		System.out.println(ts.sayHello());
		
//		fileUpload();
	}
	@Test
	public void fileUpload() throws FileNotFoundException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:customer.xml"});
//		context.start();
		FileUploadService ts = (FileUploadService) context.getBean("fileUploadService");
		FileInputStream in = new FileInputStream(new File("C:\\Users\\Administrator\\Pictures\\Camera Roll\\927_p83.jpg"));
		String a = ts.upload("E:\\a.jpg", in);
		System.out.println(a);
		//关于服务端出现“java.io.IOException: 远程主机强迫关闭了一个现有的连接。”，由于客户端与服务端连接被强制管理导致，springtest方式除非手动closeTServiceClient。由于ThriftServiceClientProxyFactory重写了close方法，在tomcat容器里面spring会自动调用close方法，应该不会出现问题。
	}
}
