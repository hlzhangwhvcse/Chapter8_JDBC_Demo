import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo6 
{
	private Connection connection = null;
	private Statement  statement = null;
	
	//静态代码块:在java中使用static关键字声明的代码块。静态块用于初始化类，为类的属性初始化。每个静态代码块只会执行一次，JVM在加载类时执行静态代码块，静态代码块先于主方法执行。
	//注意：1 静态代码块不能存在于任何方法体内。2 静态代码块不能直接访问静态实例变量和实例方法，需要通过类的实例对象来访问。
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
		catch (SQLException sqlException) 
		{
			throw new RuntimeException("连接异常    :"+ sqlException.getMessage());
		}
	}
	
	private void close()
	{
		try
		{
			if (statement != null) 
			{
				statement.close();
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
		catch (SQLException sqlException) 
		{
			throw new RuntimeException("回滚失败:" + sqlException.getMessage());
		}
	}
	public void addCity(int id, String name, String countryCode, String district, int population) 
	{
		prepareConnection();
		try 
		{
			connection.setAutoCommit(false);//默认为自动提交， 执行update ,delete或者insert都会自动提交到数据库，无法回滚事务。 
			//设置connection.setautocommit(false),只有程序调用connection.commit()时才会将先前执行语句提交到数据库，实现数据库事务。
			//true：sql命令的提交（commit）由驱动程序负责 
			//false：sql命令的提交由应用程序负责，程序必须调用commit或者rollback方法
			
			statement = connection.createStatement();
			
			//动态传多个数时，需要构造很复杂的sql语句字符串，易出错且难阅读
			String sqlStatement = "insert into city(ID, Name, CountryCode, District, Population)" + "values(" + id +	", '" + name + "' ,'"  + countryCode + "', '" + district + "', " + population + ")";	
			System.out.println("要执行的sql语句是：" + sqlStatement);
			
			int rowCount = statement.executeUpdate(sqlStatement);//executeUpdate()在Statement对象中执行会对数据库表产生影响的sql语句，如insert, update, delete，返回受影响的行数
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
		new JDBCDemo6().addCity(4080, "Wu Han", "CHN", "Hubei", 8000000);
	}
	
}