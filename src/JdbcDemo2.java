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
			System.out.println("����JDBC�����ɹ�");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println("����JDBC����ʧ�ܣ�" + e);
		}
		
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ph","root","root");
			//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
			System.out.println("����mysql���ݿ�ɹ�");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("����mysql���ݿ�ʧ�ܣ�" + e);
		}
	}
}