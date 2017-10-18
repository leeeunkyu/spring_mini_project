package notice.controller;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;

import notice.model.LoginDao;
import notice.service.LoginService;
import notice.view.MainView;

public  class LoginAction {  // 2. 컴파일시에 org.apache.struts.action.Action 변경되어 컴파일함.
	LoginService loginService;
	
	public HashMap<String, String> loginAction(String user, String pw) {
	  ApplicationContext context = MainView.context;
	  loginService = context.getBean("loginService",LoginService.class);
	  HashMap<String, String> map = new HashMap<String,String>();
	  String msg=null;
		try{
            //boolean check=new LoginDAO().loginCheck(user,pass);
            boolean check = loginService.loginCheck(user, pw);
				if(check) {
				msg= "로그인 되었습니다.";
				map.put("msg", msg);
				map.put("key", ""+check);
                // result.jsp view 이름을 직접적으로 명시하면 view 관리가힘들다. 이름을 변경해서 써라-> struts-config.xml 에 정의
				// ActionForward
			}else {
				msg= "로그인 실패.";
				map.put("msg", msg);
				map.put("key", ""+check);
			}

		}catch(Exception e){
			msg= "error";
			
		}
		return map;
	}
}     	             
