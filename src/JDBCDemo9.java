import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class JDBCDemo9 
{

	public static void main(String[] args) 
	{

		Scanner scanner = new Scanner(System.in);
		CityDAO cityDAO = new CityDAO();
		
		while(true) 
		{
			System.out.println("====================================================================================");
			System.out.println("��ѯȫ����¼������'1'���ٰ��س���");
			System.out.println("��ѯָ����¼�밴��ʽ����'2#ID'���ٰ��س���");
			System.out.println("ɾ����¼�밴��ʽ����\"3#ID'���ٰ��س���\"");
			System.out.println("��Ӽ�¼�밴��ʽ����'4#Name#CountryCode#District#Population'���ٰ��س���");
			System.out.println("�޸ļ�¼�밴��ʽ����'5#ID#Name#CountryCode#District#Population'���ٰ��س���\"");
			System.out.println("�˳�������'6'���ٰ��س���");
			System.out.println("====================================================================================");
			
			String str = scanner.next();
			String cmd = str.substring(0, 1);
			String query = str.substring(1);
			if (cmd.equals("1")) 
			{
				List <City> cities = cityDAO.getAllCities();
				System.out.println("|  ID  |  Name  |  CountryCode  |  District  |  Population  |");
				for(City city : cities)
				{
					System.out.print("| ");
					System.out.print(city.getId());
					System.out.print(" | ");
					System.out.print(city.getName());
					System.out.print(" | ");
					System.out.print(city.getCountryCode());
					System.out.print(" | ");
					System.out.print(city.getDistrict());
					System.out.print(" | ");
					System.out.print(city.getPopulation());
					System.out.println(" | ");
				}
			} 
			else if(cmd.equals("2")) 
			{
				StringTokenizer strTokenizer = new StringTokenizer(query, "#");
				int countTokens = strTokenizer.countTokens();
				System.out.println( "��#�����ŵ�������: " + countTokens);
				if(countTokens != 1)
				{
					continue;
				}
				
				int id = Integer.parseInt(strTokenizer.nextToken());
				City city = cityDAO.getCityById(id);
				if(city != null) 
				{
					System.out.println("|  ID  |  Name  |  CountryCode  |  District  |  Population  |");
					System.out.print("| ");
					System.out.print(city.getId());
					System.out.print(" | ");
					System.out.print(city.getName());
					System.out.print(" | ");
					System.out.print(city.getCountryCode());
					System.out.print(" | ");
					System.out.print(city.getDistrict());
					System.out.print(" | ");
					System.out.print(city.getPopulation());
					System.out.println(" | ");
				}
				else 
				{
					System.out.println("|  ID  |  Name  |  CountryCode  |  District  |  Population  |");
					System.out.println("Ҫɾ���ļ�¼������");
				}
				
			} 
			else if (cmd.equals("3")) 
			{
				StringTokenizer strTokenizer = new StringTokenizer(query, "#");
				int countTokens = strTokenizer.countTokens();
				System.out.println( "��#�����ŵ�������: " + countTokens);
				if(countTokens != 1)
				{
					continue;
				}
				int id = Integer.parseInt(strTokenizer.nextToken());
				int rowCount = cityDAO.deleteCityById(id);
				System.out.println("�ɹ�ɾ����" + rowCount + "����¼");
			} 
			else if(cmd.equals("4")) 
			{
				StringTokenizer strTokenizer = new StringTokenizer(query, "#");
				int countTokens = strTokenizer.countTokens();
				System.out.println( "��#�����ŵ�������: " + countTokens);
				if(countTokens != 4)
				{
					continue;
				}
				City city = new City();
				city.setName(strTokenizer.nextToken());
				city.setCountryCode(strTokenizer.nextToken());
				city.setDistrict(strTokenizer.nextToken());
				city.setPopulation(Integer.parseInt(strTokenizer.nextToken()));
				int rowCount = cityDAO.addCity(city);
				System.out.println("�ɹ�������" + rowCount + "����¼");
			} 
			else if(cmd.equals("5")) 
			{
				StringTokenizer strTokenizer = new StringTokenizer(query, "#");
				int countTokens = strTokenizer.countTokens();
				System.out.println( "��#�����ŵ�������: " + countTokens);
				if(countTokens != 5)
				{
					continue;
				}
				City city = new City();
				city.setId(Integer.parseInt(strTokenizer.nextToken()));
				city.setName(strTokenizer.nextToken());
				city.setCountryCode(strTokenizer.nextToken());
				city.setDistrict(strTokenizer.nextToken());
				city.setPopulation(Integer.parseInt(strTokenizer.nextToken()));
				int rowCount = cityDAO.updateCity(city);
				System.out.println("�ɹ�������" + rowCount + "����¼");
			} 
			else if (cmd.equals("6")) 
			{
				System.out.println("�˳�������");
				System.exit(0);
			}
			else 
			{
				System.out.println("ָ���������������������룡");
			}
		}
	}



}
