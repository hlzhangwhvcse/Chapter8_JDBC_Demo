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
			throw new RuntimeException("�����쳣 ��" + sqlException.getMessage());
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
			throw new RuntimeException("�ر������쳣:" + sqlException.getMessage());
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
			throw new RuntimeException("�ع�ʧ��:" + sqlException.getMessage());
		}
	}
	
	public void addCity(int id, String name, String countryCode, String district, int population) 
	{
		prepareConnection();
		try 
		{
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement("insert into city(ID, Name, CountryCode, District, Population) values(?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, id);//�����͸�����1��ռλ��
			preparedStatement.setString(2, name);//���ַ���������2��ռλ��
			preparedStatement.setString(3, countryCode);
			preparedStatement.setString(4, district);
			preparedStatement.setInt(5, population);
			
			int rowCount = preparedStatement.executeUpdate();//executeUpdate()��PreparedStatement������ִ�л�����ݿ�����Ӱ���sql��䣬��insert, update, delete�� ��������Ӱ�������
			connection.commit();
			
			if (rowCount > 0) 
			{
				System.out.println("�ɹ������ˣ�" + rowCount + "�м�¼");
			}
		} 
		catch (SQLException sqlException) 
		{
			rollback();
			throw new RuntimeException("��Ӽ�¼�쳣��" + sqlException.getMessage());
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