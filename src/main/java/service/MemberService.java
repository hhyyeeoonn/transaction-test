package service;

import java.sql.DriverManager;

import vo.*;
import dao.MemberDao;
import dao.OutIdDao;
import java.sql.*;

public class MemberService {
	private OutIdDao outIdDao; // 필드변수생성 메소드 안에서 지역변수로 만들지 않도록...정보은닉 
	private MemberDao memberDao; 

	public Member login(Member paramMember) {
		Member resultMember = null;
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58","root","java1234");
			this.memberDao = new MemberDao();
			resultMember = memberDao.login(conn, paramMember);
		}  catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try{
					conn.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return resultMember;
	}
	
	//트랜잭션(Transaction)이란 데이터베이스의 상태를 변환시키는 하나의 논리적 기능을 수행하기 위한 작업의 단위 (https://sunnyk.tistory.com/15)
	public int deleteMember(String memberId) {
		int result = 0;
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mariadb://localhost:3306/db58","root","java1234");
			conn.setAutoCommit(false); // 자동커밋(execute()) 해지
			
			/*
			setAutoCommit은 사용자가 코드상에서 commit/rollback을 하지 않아도
			프로그램이 정상으로 끝나면, commit 실행중 에러가 발생하면 자동으로 rollback 해줌

			setAutoCommit(true)이면 이 기능을 사용하자는 것이고
			conn.setAutoCommit(false)이면 사용자가 직접 commit/rollback을 하겠다는 것
			
			commit의 경우는 insert/delete/update 후 정상적으로 Exception이 발생하지 않았다면, 호출해야함
			방법은 동일하게

			conn.commit();

			Exception이 발생하였다면, 

			conn.rollback();

			입니다.

			일반적으로 update/insert/delete가 있는 부분의 try 절안에서 SQL이 실행되고,
			Exception을 catch 하는 블럭에서는 rollback을, 정상인 경우는 commit을 해주시면 되겠습니다.
			
			(https://adgw.tistory.com/entry/javajspsetAutoCommitfalse-%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C)
			 */
			
			
			
			this.outIdDao = new OutIdDao();
			if(this.outIdDao.insertMemberId(conn, memberId) == 1) {
				this.memberDao = new MemberDao();
				result = this.memberDao.deleteMember(conn, memberId);
			}
			
			conn.commit(); // 쿼리 커밋!
		} catch(Exception e) {
			try { // 예외 발생 가능성이 있는 문장
				conn.rollback();
			} catch (SQLException e1) { // 예외 발생시 이를 처리하기 위한 문장 : 예외메세지 출력
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally { // 예외 발생 여부와 상관없이 항상 무조건 수행되어야하는 문장
			if(conn != null) {
				try{
					conn.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} // 예외 발생시 try->catch->finally 순으로,  발생 하지 않은 경우 try->finally 순으로 실행
		// finally 블록은 어떤 경우에 사용할까? : 보통은 자원이나 DB에 커넥션 한 경우, 파일 닫기, 연결 닫기(close) 등과 같은 "정리"코드를 넣는 데 사용된다.
		//https://cheershennah.tistory.com/147
	
		
		return result; // 탈퇴성공 1, 실패 0
	}

	
	public void test() {
		this.outIdDao = new OutIdDao();
	}
	
	
	
	
}
