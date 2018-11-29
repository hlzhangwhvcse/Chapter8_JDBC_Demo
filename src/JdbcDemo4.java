import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo4 
{	
	public static void main(String[] args) 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		Connection connection = null;
		Statement statement = null;
		try 
		{
			int rowCount = 0;//执行executeUpdate后所影响的行数
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world","root","root");
			statement = connection.createStatement();
			
			rowCount = statement.executeUpdate("insert into city(ID, Name, CountryCode, District, Population) values(4080, 'Wu Han','CHN', 'Hu Bei', 1000)");
			System.out.println("====插入了"+ rowCount + "条数据====");
			
//			rowCount = statement.executeUpdate("update city set Population=10000000 where id=4080");
//			System.out.println("====更新了" + rowCount +"条数据====");
			
			//rowCount = statement.executeUpdate("delete from city where ID = 4080");
			//System.out.println("====删除了" + rowCount + "条数据====");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(statement!=null)
			{
				try 
				{
					statement.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
