import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo5 
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
			connection.setAutoCommit(false);//设置事务为手动提交模式
			
			statement = connection.createStatement();
			rowCount = statement.executeUpdate("insert into city(ID, Name, CountryCode, District, Population) values('4080', 'Wu Han','CHN', 'Hu Bei', '1000')");
			System.out.println("插入" + rowCount + "条数据");
			
			rowCount = statement.executeUpdate("update city set Population=10000000 where id=4080");
			System.out.println("更新" + rowCount + "条数据");
			
			rowCount = statement.executeUpdate("delete from city where ID = 4080");	
			System.out.println("删除" + rowCount + "条数据");
			
			rowCount = 1/0;//此处人为增加一个异常用于测试回滚，如果取消此行数据库修改可以正常保存
			connection.commit();//手动提交事务
		} 
		catch (Exception exception) //此处修改为捕获Exception用于测试回滚操作
		{
			exception.printStackTrace();
			try 
			{
				connection.rollback();//如果相关操作出现异常，会调用rollback()回滚操作
			} 
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
		finally
		{
			if(statement!=null)
			{
				try 
				{
					statement.close();
				} 
				catch (SQLException sqlException) 
				{
					sqlException.printStackTrace();
				}
			}
			
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException sqlException) 
				{
					sqlException.printStackTrace();
				}
			}
		}
	}

}
