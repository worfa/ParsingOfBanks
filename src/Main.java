import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;


public class Main {
		
	   public static void main( String[] args )throws IOException, Exception{
		 
		   int valueSend = 0;
		  
		   //create doc file with name "ExelParsCheck". Check create to file. 
		ExcelPars excelPars = new ExcelPars();
		String nameFile = "ExelParsCheck";
		excelPars.CreateFileEx(nameFile);
		excelPars.WriteNameColumnExcel(nameFile);
		String DollarValue = splitingCurrenceDol(); 
		String EvrValue = splitingCurrenceEvr();
		excelPars.WriteCurrencies(nameFile, DollarValue, EvrValue);
		if(excelPars.checkRefreshCurrensy(nameFile) == true) {
			Sender sendEmail = new Sender("email@yandex.ru","password");
			boolean send = true;
			
			while(send) {
				valueSend = valueSend+1;
			send = sendEmail.send(valueSend + "На данный момент курс валют относительно рубля составляет:"
					+ "Доллар: " + DollarValue + "\b\b" + "Евро: " + EvrValue, 
					"email@mail.ru", "email@yandex.ru", "Курс валют изменился!!!");
			}
		}else {
			System.out.println("Письмо не может быть отправлено, так как" + 
				" курс валют не изменился совершенно. Поэтому просто расслабься" + 
				" и посмотри старые курсы в файле :D");}
	   }
		
	   
	   // parsing CentroBank
	   public static String currencyBay()throws IOException {
		   Document page = getPage();
		   Element tableWth = page.select("div[class=key-indicator_table_wrapper]").first();
		   
		   String DollarVchera = tableWth.select("td[class=value td-w-4 _bold _end mono-num]").text();
		   
		   return DollarVchera; 
	   }
   
	   public static Document getPage() throws IOException{
		   
		   String url = "https://cbr.ru/key-indicators/";
		   try {
		   Document page = Jsoup.parse(new URL(url), 10000);
		   return page;
		   }
		   catch(Exception e) {System.out.println("Подключиться не получилось, повторная попытка подключения.");
		   Document page = getPage();
		   return page;
		   }
	   }
	   
	   public static String splitingCurrenceDol() throws IOException {
		   
		   String dol = currencyBay();
		   String [] currencyAr = dol.split(" ");
		   dol = currencyAr[0]; 
		   return dol;
		   
	   }
	   
	   public static String splitingCurrenceEvr() throws IOException {
		   
		   String evr = currencyBay();
		   String [] currencyAr = evr.split(" ");
		   evr = currencyAr[1]; 
		   return evr;
	   }
}

