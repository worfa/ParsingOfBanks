import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class TestClass {
	
		  
		  @Test
		  public void testingWhenFileDontCreate() throws Exception {
			  ExcelPars excelPars = new ExcelPars();
			  boolean okCreate = excelPars.CreateFileEx("ExelParsCheck");
			  assertFalse(okCreate);
		  }
		  
		  @Test
		  public void testingWhenFileCreate() throws Exception {
			  ExcelPars excelPars = new ExcelPars();
			  boolean okCreate = excelPars.CreateFileEx("ExelParsCheck1");
			  assertTrue(okCreate);
		  }
		  
		  
}
