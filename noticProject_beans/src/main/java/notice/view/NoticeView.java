package notice.view;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import notice.controller.ControllerAction;
import notice.model.Factory;
import notice.model.Notice;

public class NoticeView {
	ControllerAction controlAction;
	private static NoticeView noticeview = new NoticeView();
	static String id;
	static String pw;
	
	
	public NoticeView(String id, String pw) {
		this();
		this.id = id;
		this.pw = pw;
		System.out.println("============공지사항==========");
		listNotice();
	}
	private NoticeView() {
		ApplicationContext context = MainView.context;
		controlAction = context.getBean("contorlAction",ControllerAction.class);
	}
	
	public static NoticeView getInstance() {
		return noticeview;
	}
	public void listNotice() {
		 ArrayList<Notice> notice = controlAction.listAction();
		 System.out.println("┌");
		 for (int i = 0; i < notice.size(); i++) {
			 System.out.println(notice.get(i).toString());
		}
		System.out.println("                        ┘");
		noticeMenuBar();
	}

	public void noticeMenuBar() {
		System.out.println("1.글쓰기 2.글보기 3.글삭제 4.종료하기");
		int choice = 0;
		System.out.print("메뉴를 선택해 주세요: ");
		Scanner scan = new Scanner(System.in);
		choice = scan.nextInt();
		switch (choice) {
		case 1:
			System.out.println("=======글쓰기 메뉴=======");
			noticeInsert();
			break;
		case 2:
			System.out.println("=======글보기 메뉴=======");
			noticeSelect();
			break;
		case 3:
			System.out.println("=======글삭제 메뉴=======");
			noticeRemove();
			break;
		case 4:
			System.out.println("=======종료 하기=======");
			System.exit(0);
			break;
		default:
			break;
		}
	}

	private void noticeRemove() {
		int num;
		System.out.println("삭제를 원하는 번호를 선택 해주세요: ");
		Scanner scan = new Scanner(System.in);
		num = scan.nextInt();
		controlAction.deleteAction(num);
		listNotice();
	}

	private static void noticeSelect() {
		int num;
		System.out.println("원하는 번호를 선택 해주세요: ");
		Scanner scan = new Scanner(System.in);
		num = scan.nextInt();
		contentView content = new contentView(num);
	}

	private void noticeInsert() {
		String writer = null;
		String title = null;
		String cont = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("저자를 입력하세요: ");
		writer = scan.next();
		System.out.println("제목를 입력하세요: ");
		title = scan.next();
		System.out.println("내용를 입력하세요: ");
		cont = scan.next();
		controlAction.saveAction(writer, title, cont);
		listNotice();
	}

}
