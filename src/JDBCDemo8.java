import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDemo8 
{
	private Connection connection = null;
	private PreparedStatement  preparedStatement = null;
	static 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	private void prepareConnection() 
	{
		try 
		{
			if (null == connection || connection.isClosed()) 
			{
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
			}
		} 
		catch(SQLException e) 
		{
			throw new RuntimeException("连接异常 :" + e.getMessage());
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
		catch(SQLException e) 
		{
			throw new RuntimeException("关闭连接异常:"+e.getMessage());
		}
	}	
	private void rollback()
	{
		try 
		{
			connection.rollback();
		} 
		catch(SQLException e) 
		{
			throw new RuntimeException("回滚失败:"+e.getMessage());
		}
	}

	public void testScroll()//测试可滚动结果集
	{
		try
		{
			prepareConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement("select * from city", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("当前行号：" + resultSet.getRow());
			
			resultSet.next();//将游标从当前位置向后移动一行
			System.out.println("当前行号：" + resultSet.getRow());
			System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2));
			
			resultSet.last();
			int rowCount = resultSet.getRow();
			System.out.println("ResultSet获得的总行数是 " + rowCount + " 行记录");
			System.out.println("当前行号：" + resultSet.getRow());
			System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2));
			
			resultSet.absolute(1);
			System.out.println("当前行号：" + resultSet.getRow());
			System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2));

			resultSet.last();
			System.out.println("当前行号：" + resultSet.getRow());
			System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2));
			
			
			
			String updatedValue = resultSet.getString(2) + "_updated";//更新后的值
			resultSet.updateString(2, updatedValue);//更新结果集resultSet中的某个列字段的值
			resultSet.updateInt(5, 30000000);
			resultSet.updateRow();//将当前行的更新发送到数据库中执行
			System.out.println("更新后的记录行: " + resultSet.getInt(1) + "|" + resultSet.getString(2));
			connection.commit();
		}
		catch(SQLException e) 
		{
			rollback();
			e.printStackTrace();
		}
		finally
		{
			close();
		}
		
	}
	public static void main(String[] args) 
	{
		new JDBCDemo8().testScroll();
	}
}
