package dao;
import java.sql.*;
import vo.Member;


public class MemberDao {
	
	// 회원탈퇴
	public int deleteMember(Connection conn, String memberId) throws Exception{ // try..catch는 해지할 자원이 없을 때 
		
		PreparedStatement stmt = null;
		
		int row = 0;
			
		String sql = "DELETE FROM member WHERE member_id = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, memberId);
		row = stmt.executeUpdate(); 
	
		stmt.close(); 
		
		return row; // 2가 되어야지 회원탈퇴 성공
	}
	
	/*
	public int deleteMember2(String memberId) {
		Connection conn = null;
		PreparedStatement outidStmt = null;
		PreparedStatement memberStmt = null;
		int outidRow = 0;
		int memberRow = 0;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58","root","java1234");
			conn.setAutoCommit(false);
			
			// 1)
			String outidSql = "INSERT INTO outid(member_id, createdate)"
					+ " VALUES(?, NOW())"; 
			outidStmt = conn.prepareStatement(outidSql);
			outidStmt.setString(1, memberId);
			outidRow = outidStmt.executeUpdate(); // conn.setAutoCommit(false); 자동커밋 X
			// 2)					
			String memberSql = "DELETE FROM member WHERE member_id = ?";
			memberStmt = conn.prepareStatement(memberSql);
			memberStmt.setString(1, memberId);
			memberRow = memberStmt.executeUpdate(); // conn.setAutoCommit(false); -> 자동커밋X
			conn.commit(); // 1) + 2) 일괄처리 -> 트랜잭션
		} catch(Exception e) {
			try {
				conn.rollback(); // 1) + 2) 일괄처리 -> 트랜잭션 하나라도 실패시 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if(outidStmt != null) { 
						outidStmt.close(); 
					}
					if(memberStmt != null) { 
						memberStmt.close(); 
					}
					if(conn != null) { 
						conn.close(); 
					}

		            outidStmt.close();
		            memberStmt.close();
		            conn.close();
		         } catch(Exception e1) {
		            e1.printStackTrace();
		         }

			}
		} 
		return outidRow + memberRow; // 2가 되어야지 회원탈퇴 성공
	}
	 */
	
	
	
	
	// 회원가입
	public int insertMember(Member member) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58","root","java1234");
			String sql = "INSERT INTO member(member_id, member_pw, member_name)"
					+ " VALUES(?, PASSWORD(?), ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			stmt.setString(3, member.getMemberName());
			row = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) { 
					stmt.close(); 
				}
				if(conn != null) { 
					conn.close(); 
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
		
	// 아이디 중복검사 / param String : 사용할 아이디 / return value : true(입력된 아이디 사용가능) / false(사용불가 아이디)
	public boolean checkMemberId(String memberId) {
		boolean result = true;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58","root","java1234");
			String sql = "SELECT t.id"
					+ " FROM (SELECT member_id id FROM member"
					+ "			UNION"
					+ "			SELECT member_id id FROM outid) t"
					+ " WHERE t.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) { // 이미 사용된 아이디일 경우
				result = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) { 
					rs.close(); 
				}
				if(stmt != null) { 
					stmt.close(); 
				}
				if(conn != null) { 
					conn.close(); 
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 로그인
	public Member login(Connection conn, Member paramMember) throws Exception { // String memberId, String memberPw
		Member resultMember = null;
		PreparedStatement stmt = null;
		
		String sql = "SELECT member_id memberId, member_name memberName"
					+ "	FROM member WHERE member_id=? AND member_pw = PASSWORD(?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, paramMember.getMemberId());
		stmt.setString(2, paramMember.getMemberPw());
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			resultMember = new Member();
			resultMember.setMemberId(rs.getString("memberId"));
			resultMember.setMemberName(rs.getString("memberName"));
		}
		stmt.close();
		rs.close();
		return resultMember; // 로그인 실패시 null, 성공시 로그인 Member객체 반환
	}
	
	
	/*
		public Member login(Member paramMember) { // String memberId, String memberPw
		Member resultMember = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58","root","java1234");
			String sql = "SELECT member_id memberId,"
					+ " member_name memberName"
					+ " FROM member"
					+ " WHERE member_id = ?"
					+ " AND member_pw = PASSWORD(?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
				resultMember = new Member();
				resultMember.setMemberId(rs.getString("memberId"));
				resultMember.setMemberName(rs.getString("memberName"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) { 
					rs.close(); 
				}
				if(stmt != null) { 
					stmt.close(); 
				}
				if(conn != null) { 
					conn.close(); 
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return resultMember; // 로그인 실패시 null, 성공시 로그인 Member객체 반환
	}
	 */
	
	
	
	// 회원탈퇴
	public int deleteMember2(String memberId) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58","root","java1234");
			String sql = "DELETE member_id, member_pw, member_name"
					+ " FROM member"
					+ " WHERE member_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			row = stmt.executeUpdate();
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) { 
					stmt.close(); 
				}
				if(conn != null) { 
					conn.close(); 
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
}
