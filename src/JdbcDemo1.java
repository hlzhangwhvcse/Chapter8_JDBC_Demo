public class JdbcDemo1 
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
	}

}