package mainController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import passes.PassOne;
import passes.PassTwo;

public class MainController {
	
	public static void main(String[] args) {
		PassOne passOne;
		String progName = "test";

		try {
			passOne = new PassOne(progName);
			PassTwo passTwo = new PassTwo(passOne.getOperations(), passOne.getSymbolTable(), progName, passOne.isProceed());
			
		} catch (Exception e) {
//			e.printStackTrace();
		}

	}

}
