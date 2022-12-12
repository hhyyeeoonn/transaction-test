package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OutIdDao {
	
	// 탈퇴아이디 입력
	public int insertMemberId(Connection conn, String memberId) throws Exception { // conn에 대한 예외 발생시 이 메소드를 호출한 MemberService에 예외사실을 전달하고 예외처리
		int row = 0;
		PreparedStatement stmt = null;
		
		String sql = "INSERT INTO outid(member_id, createdate)"
				+ " VALUES(?, NOW())";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, memberId);
		row = stmt.executeUpdate();
		stmt.close();
		return row;
	}
}
