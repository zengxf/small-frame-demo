package cn.zxf.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTest {

    public static void main(String[] args) {
	Connection conn = ConnectionUtil.getLocalConnection();
	try {
	    String sql = "INSERT INTO crawler_image_log"//
		    + " (website, web_lv2_sign, web_img_url, file_path, file_md5, file_img_width, file_img_height, is_test)"//
		    + " VALUE (?, ?, ?, ?, ?, ?, ?, ?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    int i = 1;
	    stmt.setString(i++, "mm131");
	    stmt.setInt(i++, 323);
	    stmt.setString(i++, "http://ld.com/dd.jpg");
	    stmt.setString(i++, "root/aa.jpg");
	    stmt.setString(i++, "dssdf9fs8sfd98fds9fds798sdf");
	    stmt.setInt(i++, 960);
	    stmt.setInt(i++, 400);
	    stmt.setInt(i++, 1);
	    boolean res = stmt.execute();
	    System.out.println("res: " + res);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

}
