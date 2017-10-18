package notice.controller;

import java.util.ArrayList;

import notice.model.Notice;
import notice.model.NoticeDao;

/***
 * crud
 * @author kosta
 *
 */
public class ControllerAction {
	static ControllerAction controlAction = new ControllerAction();
	public static ControllerAction getInstance() {
		return controlAction;
	}
	public String saveAction(String writer, String title, String cont) {
			try{
				NoticeDao dao=NoticeDao.getInstance();
	  			dao.noticeInsert(writer,title,cont);
				return "정상적으로 저장 되었습니다.";
	        }catch(Exception e){
				return "error";
			}
	}
	 public Notice viewAction (int num) {
			Notice n = null;
		  try{
				NoticeDao dao=NoticeDao.getInstance();
	  			n = dao.noticeView(num);
			}catch(Exception e){
			}
		  return n;
	 }
	 public ArrayList<Notice> listAction() {
		  ArrayList<Notice> notice = null;
		  try{
				NoticeDao dao=NoticeDao.getInstance();
				notice = dao.noticeList();
			}catch(Exception e){
			}
		  return notice;
		}
	 public Notice modifyAction(int num) {
			Notice n = null;
			try {
				NoticeDao dao = NoticeDao.getInstance();
				n = dao.noticeView(num);
			} catch (Exception e) {
			}
			return n;
		}
	 public Notice updateAction(int num, String writer,String inDate,String title, String cont) {
		  Notice nb = null;
			try{
				NoticeDao dao=NoticeDao.getInstance();
	  			nb = new Notice(num, writer, inDate, title, cont);
	  			if(1==dao.noticeUpdate(num, title, cont)) {
	  				return nb;	  				
	  			}
	        }catch(Exception e){
			}
			return nb;
	}     	
	public String deleteAction(int num) {	
		String msg = null;
		try {
			NoticeDao dao=NoticeDao.getInstance();
			dao.noticeDelete(num);
	        msg = "삭제처리 되었습니다.";
	    }catch(Exception e){
	    	msg="삭제시 오류 발생했습니다.";
		}
			return msg;
		}
}
