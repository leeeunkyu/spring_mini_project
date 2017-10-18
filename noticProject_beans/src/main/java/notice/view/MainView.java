package notice.view;

import java.util.HashMap;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import notice.controller.LoginAction;
import notice.model.Factory;
import notice.model.LoginDao;

public class MainView {
	public static ApplicationContext context;
	LoginAction loginAction;
	LoginDao loginDao;
	Factory factory;
	
	public void init() {
		context = new ClassPathXmlApplicationContext("bean-config.xml");
		factory = context.getBean("dao",Factory.class);
		loginAction = context.getBean("loginAction",LoginAction.class);
		loginDao = context.getBean("loginDao",LoginDao.class);
	}
	public void login() {
		String id,pw,msg;
		HashMap<String, String> map = new HashMap<String,String>();
		Scanner scan = new Scanner(System.in);
		System.out.print("아이디를 입력 하시오: ");
		id = scan.next();
		System.out.println("비밀번호를 입력 하시오: ");
		pw = scan.next();
		map = loginAction.loginAction(id, pw);
		if("true".equals(map.get("key"))) {
			System.out.println("========"+id+"님 환영=======");
			NoticeView notice = new NoticeView(id,pw);
		}else {
			System.out.println("============로그인실패==========");
		}
	}
	
	public static void main(String[] args) {
		MainView mv = new MainView();
		mv.init();
		mv.login();
	}
}
