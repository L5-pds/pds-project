package interest_rate;

import java.util.ArrayList;

public class ClientsData {

	public static void main(String[] args) { 
		// client 
		Rate_calculation c1 = new Rate_calculation(	
				1,
				"BOUZID",
				25, 
				30000, 
				40000,
				48,
				"AUTOMOBILE",
				2,
				2,
				1,
				1,
				1,
				1) ;
	c1.grade();
	new MainFrame().setVisible(true) ; 
//	new GainLoss().setVisible(true); 
	//new CarLoan().setVisible(true);
	}

}
