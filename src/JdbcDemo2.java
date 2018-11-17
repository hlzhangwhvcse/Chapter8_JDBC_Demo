import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDemo2 
{
	public static void main(String[] args) 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("加载JDBC驱动成功");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println("加载JDBC驱动失败：" + e);
		}
		
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ph","root","root");
			//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
			System.out.println("连接mysql数据库成功");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("连接mysql数据库失败：" + e);
		}
	}
}