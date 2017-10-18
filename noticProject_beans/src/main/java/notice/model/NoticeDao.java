/* 
        Singleton Pattern을 적용 :
        1.  Singleton instance에 대한 멤버변수 private static
        2.  getInstance() : Singleton public static 메서드 제공
        3.  생성자 private
*/

package notice.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
/**
 *<pre>
 * Database의 Notice 정보를 접근하기위한 CRUD처리 기능을 담고있는 클래스
 *</pre>
 *
 */

public class NoticeDao{
	private Factory factory;

	/* 
        Singleton Pattern을 적용 :
        1.  Singleton instance에 대한 멤버변수 private static
        2.  getInstance() : Singleton public static 메서드 제공
        3.  생성자 private
    */
    // 1.
    private static NoticeDao dao = NoticeDao.getInstance();

    // 2.
    public static NoticeDao getInstance() {
        if (dao == null) {
            dao = new NoticeDao();
        }
        return dao;
    }

    /**
    * Default constructor
    * DataBase 연동을 위해 DataSource 객체를 검색합니다.
    */
    // 3.
	private NoticeDao() {
	 
	}

    public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	
    /**
    * 공지사항 정보를 DB에 입력합니다.
    * 공지사항 입력을 위해서 기존에 저장된 공지사항의 번호 중 가장 큰값을 구합니다.
    * 가장 큰값에 1을 더해 공지사항의 번호를 구하고, 공지사항 레코드가 존재하지 않을 경우 번호를 1로 설정합니다.
    * 구해진 번호와 매개변수로 입력된 id, title, content, 그리고 오늘날짜를 구하여 Notice 테이블에 한 레코드를 추가합니다.
    * @param writer 작성자
    * @param title  제목
    * @param content  공지사항 내용
    * @return void
    */
	public void noticeInsert(String writer, String title,String content){
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			int seqnum;
			con = factory.getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT max(num) FROM notice");
			if(rs.next())   {
				seqnum = rs.getInt(1) + 1;
			}else{
				seqnum = 1;
		}
		pstmt = con.prepareStatement("INSERT INTO notice VALUES(?,?,?,?,?)");
		pstmt.setInt(1, seqnum);
		pstmt.setString(2, writer);
		Date dt=new Date();
		SimpleDateFormat sd=new SimpleDateFormat();
		String date=sd.getDateInstance().format(dt);
		pstmt.setString(3, date);
		pstmt.setString(4,title);
 		pstmt.setString(5, content);
		pstmt.executeUpdate();
		}catch(Exception e)     {
			e.printStackTrace();
		}finally{
			try { 
				if(stmt != null)stmt.close();
				if(pstmt != null)pstmt.close(); 
				if(con != null)con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 공지사항 내용을 수정합니다
	 * @param num 글번호
	 * @param content 내용
	 */
	public int noticeUpdate(int num, String title, String content){
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement("update notice set title=?, cont=? where num=?");
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { 
				if(pstmt != null)pstmt.close(); 
				if(con != null)con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
/**
* DB를 연동하여 공지사항 정보를 Notice 객체에 저장하고 객체들의 목록을 ArrayList 형태로 리턴합니다.
* @return ArrayList : 공지사항 정보 목록
*/
	public ArrayList<Notice> noticeList(){
		Connection con = null;
		ArrayList<Notice> arr=new ArrayList<>();
		Statement stmt=null;
		try {
    		con = factory.getConnection();
			stmt = con.createStatement();
			String query = "SELECT * FROM Notice ORDER BY num desc";
			ResultSet myResult = stmt.executeQuery(query);

			while (myResult.next()) {
				Notice n=new Notice();
				n.setNum(myResult.getInt("num"));
				n.setWriter(myResult.getString("writer"));
				n.setInDate(myResult.getString("inDate"));
				n.setTitle(myResult.getString("title"));
				n.setContent(myResult.getString("cont"));
				arr.add(n);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally { 
			try { 
				if(stmt != null)stmt.close();
				if(con != null)con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		 return arr; 
	}

    /**
    * 매개변수로 전달된 번호의 공지사항 정보를 검색하여 Notice 형태로 리턴합니다.
    * @param num 검색하고자 하는 공지사항 번호
    * @return Notice : 검색된 공지사항정보(Notice)
    */
	public Notice noticeView(int num){
		Connection con = null;
		Statement stmt=null;
		Notice n=new Notice();
		try {
    		con = factory.getConnection();
			stmt = con.createStatement();
			String query = "SELECT * FROM Notice where num="+num;
			ResultSet myResult = stmt.executeQuery(query);
			if (myResult.next()) {
				n.setNum(myResult.getInt("num"));
				n.setWriter(myResult.getString("writer"));
				n.setInDate(myResult.getString("inDate"));
				n.setTitle(myResult.getString("title"));
				n.setContent(myResult.getString("cont"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally { 
			try { 
				if(stmt != null)stmt.close();
				if(con != null)con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return n; 
	}

    /**
    * 매개변수로 전달된 번호의 공지사항 정보를 삭제합니다.
    * @param num 삭제하고자 하는 공지사항 번호
    * @return void : 
    */
	public void noticeDelete(int num) {
		Connection con = null;
		Statement stmt=null;

        try {
    		con = factory.getConnection();
			stmt = con.createStatement();
			String query = "delete from Notice where num="+num;
			stmt.executeUpdate(query);

        } catch (SQLException e) {
			System.out.println(e);
		} finally { 
			try { 
				if(stmt != null)stmt.close();
				if(con != null)con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

    /**
    * NoticeDAO 클래스의 단독 테스트를 위한 메소드
    */

}
