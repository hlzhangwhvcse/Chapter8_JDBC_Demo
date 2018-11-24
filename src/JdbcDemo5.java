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
			int rowCount = 0;//ִ��executeUpdate����Ӱ�������
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world","root","root");
			connection.setAutoCommit(false);//��������Ϊ�ֶ��ύģʽ
			
			statement = connection.createStatement();
			rowCount = statement.executeUpdate("insert into city(ID, Name, CountryCode, District, Population) values('4080', 'Wu Han','CHN', 'Hu Bei', '1000')");
			System.out.println("����" + rowCount + "������");
			
			rowCount = statement.executeUpdate("update city set Population=10000000 where id=4080");
			System.out.println("����" + rowCount + "������");
			
			rowCount = statement.executeUpdate("delete from city where ID = 4080");	
			System.out.println("ɾ��" + rowCount + "������");
			
			rowCount = 1/0;//�˴���Ϊ����һ���쳣���ڲ��Իع������ȡ���������ݿ��޸Ŀ�����������
			connection.commit();//�ֶ��ύ����
		} 
		catch (Exception exception) //�˴��޸�Ϊ����Exception���ڲ��Իع�����
		{
			exception.printStackTrace();
			try 
			{
				connection.rollback();//�����ز��������쳣�������rollback()�ع�����
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
