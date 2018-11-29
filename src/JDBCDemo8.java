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
			throw new RuntimeException("�����쳣 :" + e.getMessage());
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
			throw new RuntimeException("�ر������쳣:"+e.getMessage());
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
			throw new RuntimeException("�ع�ʧ��:"+e.getMessage());
		}
	}

	public void testScroll()//���Կɹ��������
	{
		try
		{
			prepareConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement("select * from city", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("��ǰ�кţ�" + resultSet.getRow());
			
			resultSet.next();//���α�ӵ�ǰλ������ƶ�һ��
			System.out.println("��ǰ�кţ�" + resultSet.getRow());
			System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2));
			
			resultSet.last();
			int rowCount = resultSet.getRow();
			System.out.println("ResultSet��õ��������� " + rowCount + " �м�¼");
			System.out.println("��ǰ�кţ�" + resultSet.getRow());
			System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2));
			
			resultSet.absolute(1);
			System.out.println("��ǰ�кţ�" + resultSet.getRow());
			System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2));

			resultSet.last();
			System.out.println("��ǰ�кţ�" + resultSet.getRow());
			System.out.println(resultSet.getInt(1) + "|" + resultSet.getString(2));
			
			
			
			String updatedValue = resultSet.getString(2) + "_updated";//���º��ֵ
			resultSet.updateString(2, updatedValue);//���½����resultSet�е�ĳ�����ֶε�ֵ
			resultSet.updateInt(5, 30000000);
			resultSet.updateRow();//����ǰ�еĸ��·��͵����ݿ���ִ��
			System.out.println("���º�ļ�¼��: " + resultSet.getInt(1) + "|" + resultSet.getString(2));
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
