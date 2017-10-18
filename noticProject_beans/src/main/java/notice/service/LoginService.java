package notice.service;

import org.springframework.context.ApplicationContext;

import notice.model.LoginDao;
import notice.view.MainView;

public class LoginService {
	  
	LoginDao loginDao;
	
	public boolean loginCheck(String user, String pw) {
		ApplicationContext context = MainView.context;
		loginDao = context.getBean("loginDao",LoginDao.class);
		return loginDao.loginCheck(user, pw);
	}
}
