package notice.view;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import notice.controller.ControllerAction;
import notice.controller.ViewAction;
import notice.model.Notice;

public class contentView {
	NoticeView noticeView = NoticeView.getInstance();
	ControllerAction controlAction;

	public contentView(int num) {
		ApplicationContext context = MainView.context;
		controlAction = context.getBean("contorlAction",ControllerAction.class);
		ViewAction viewAction = new ViewAction();
		show(viewAction.viewAction(num));
		System.out.println("======선택한 글========");
	}

	private void show(Notice viewAction) {
		System.out.println();
		System.out.println(viewAction.toString());
		System.out.println();
		System.out.println("======================");
		System.out.println("1.목록으로가기  2.수정하기");
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		switch (num) {
		case 1:
			noticeView.listNotice();			
			break;
		case 2:
			update(viewAction);
		default:
			break;
		}
	}

	private void update(Notice viewAction) {
		Scanner scan = new Scanner(System.in);
		String writer,title,cont;
		System.out.print("수정할 저자 :");
		writer = scan.next();
		System.out.print("수정할 제목 :");
		title = scan.next();
		System.out.print("수정할 내용 :");
		cont = scan.next();
		System.out.println("=======수정 완료==========");
		show(controlAction.updateAction(viewAction.getNum(), writer,viewAction.getInDate() ,title, cont));
	}

}
