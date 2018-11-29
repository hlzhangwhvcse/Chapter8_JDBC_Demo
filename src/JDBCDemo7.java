import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ParameterMetaData;


public class JDBCDemo7 
{
	private Connection connection = null;
	private PreparedStatement  preparedStatement = null;
	static 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void prepareConnection() 
	{
		try 
		{
			if(null == connection || connection.isClosed()) 
			{
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
			}
		} 
		catch (SQLException sqlException) 
		{
			throw new RuntimeException("连接异常 ：" + sqlException.getMessage());
		}
	}
	private void close()
	{
		try
		{
			if (preparedStatement != null) 
			{
				preparedStatement.close();
			}
			
			if (connection != null) 
			{
				connection.close();
			}
		}
		catch (SQLException sqlException) 
		{
			throw new RuntimeException("关闭连接异常:" + sqlException.getMessage());
		}
	}
	
	private void rollback()
	{
		try 
		{
			connection.rollback();
		} 
		catch(SQLException sqlException) 
		{
			throw new RuntimeException("回滚失败:" + sqlException.getMessage());
		}
	}
	
	public void addCity(int id, String name, String countryCode, String district, int population) 
	{
		prepareConnection();
		try 
		{
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement("insert into city(ID, Name, CountryCode, District, Population) values(?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, id);//将整型赋给第1个占位符
			preparedStatement.setString(2, name);//将字符串赋给第2个占位符
			preparedStatement.setString(3, countryCode);
			preparedStatement.setString(4, district);
			preparedStatement.setInt(5, population);
			
			int rowCount = preparedStatement.executeUpdate();//executeUpdate()在PreparedStatement对象中执行会对数据库表产生影响的sql语句，如insert, update, delete， ，返回受影响的行数
			connection.commit();
			
			if (rowCount > 0) 
			{
				System.out.println("成功插入了：" + rowCount + "行记录");
			}
		} 
		catch (SQLException sqlException) 
		{
			rollback();
			throw new RuntimeException("添加记录异常：" + sqlException.getMessage());
		}
		finally
		{
			close();
		}
	}
	
	public static void main(String[] args) 
	{
		new JDBCDemo7().addCity(4080, "Wu Han", "CHN", "Hubei", 8000000);
	}
}