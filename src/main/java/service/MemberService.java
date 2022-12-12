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
	
	
	public int deleteMember(String memberId) {
		int result = 0;
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mariadb://localhost:3306/db58","root","java1234");
			conn.setAutoCommit(false); // 자동커밋(execute()) 해지
			
			this.outIdDao = new OutIdDao();
			if(this.outIdDao.insertMemberId(conn, memberId) == 1) {
				this.memberDao = new MemberDao();
				result = this.memberDao.deleteMember(conn, memberId);
			}
			
			conn.commit(); // 쿼리 커밋!
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
		
		return result; // 탈퇴성공 1, 실패 0
	}

	
	public void test() {
		this.outIdDao = new OutIdDao();
	}
	
	
	
	
}
