package notice.controller;

import notice.model.Notice;
import notice.model.NoticeDao;

public  class ViewAction {

  public Notice viewAction (int num) {
		Notice n = null;
	  try{
			NoticeDao dao=NoticeDao.getInstance();
  			n = dao.noticeView(num);
		}catch(Exception e){
		}
	  return n;
	}
}     	
 
     
              
