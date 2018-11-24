import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSetMetaData;


public class JdbcDemo3 
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
		ResultSet resultSet = null;
		try 
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world","root","root");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from city");
			
			//在使用 ResultSet 之前，必须查询它包含多少个列。此信息存储在 ResultSetMetaData 对象中。ResultSet 对象是 JDBC 中最重要的单个对象。从本质上讲，它是对一个一般宽度和未知长度的表的一种抽象
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			for(int i=1; i<=resultSetMetaData.getColumnCount(); i++)
			{
				System.out.print("  |  "+resultSetMetaData.getColumnLabel(i)+"("+resultSetMetaData.getColumnTypeName(i)+")");
			}
			System.out.println("  |");
			System.out.print("\n");
			
//			System.out.print("  |  ");
//			System.out.print("ID");
//			System.out.print("  |  ");
//			System.out.print("Name");
//			System.out.print("  |  ");
//			System.out.print("CountryCode");
//			System.out.print("  |  ");
//			System.out.print("District");
//			System.out.print("  |  ");
//			System.out.print("Population");
//			System.out.print("  |  ");
//			System.out.print("\n");
			
			while(resultSet.next())
			{
				System.out.print("  |  ");
				System.out.print(resultSet.getInt("ID"));
				System.out.print("  |  ");
				System.out.print(resultSet.getString("Name"));
				System.out.print("  |  ");
				System.out.print(resultSet.getString("CountryCode"));
				System.out.print("  |  ");
				System.out.print(resultSet.getString("District"));
				System.out.print("  |  ");
				System.out.print(resultSet.getInt("Population"));
				System.out.print("  |  ");
				System.out.print("\n");
			}
			

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(resultSet!=null)
			{
				try 
				{
					resultSet.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
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



