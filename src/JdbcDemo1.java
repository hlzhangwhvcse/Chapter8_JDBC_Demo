public class JdbcDemo1 
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
	}

}