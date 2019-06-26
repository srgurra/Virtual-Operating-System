import java.io.IOException;

public class ERROR_HANDLER {

//Catching errors in the program
	public static void errorHandler(int err) throws IOException {
		switch(err){
			case 1:
				System.out.println("Error Code:1 Illegal Instruction: Trap Value");
				
				CPU.writeOutput("ABNORMAL"+"Illegal Instruction: Trap Value.\n CLOCK VALUE: "+Integer.toHexString(CPU.clock), System.currentTimeMillis());
				
				System.exit(0);
			case 2:
				System.out.println("Error Code:2 Memory range fault");
				
				CPU.writeOutput("ABNORMAL"+"Illegal Instruction: Trap Value.\n CLOCK VALUE: "+Integer.toHexString(CPU.clock), System.currentTimeMillis());
				
				System.exit(0);
			case 3:
				System.out.println("Error Code:3 Program Size too large");
				
				CPU.writeOutput("ABNORMAL"+"Illegal Instruction: Trap Value.\n CLOCK VALUE: "+Integer.toHexString(CPU.clock), System.currentTimeMillis());
				SYSTEM.stopTimeExec = System.currentTimeMillis();
				System.exit(0);
			case 4:
				System.out.println("Error Code:4 Alloc large data error");
				
				CPU.writeOutput("ABNORMAL"+"Illegal Instruction: Trap Value.\n CLOCK VALUE: "+Integer.toHexString(CPU.clock), System.currentTimeMillis());
				
				System.exit(0);
			case 5:
				System.out.println("Error Code:5 Infinite loop in the program");
				
				CPU.writeOutput("ABNORMAL"+"Illegal Instruction: Trap Value.\n CLOCK VALUE: "+Integer.toHexString(CPU.clock), System.currentTimeMillis());
				
				System.exit(0);
			case 6:
				System.out.println("Error Code:6 Program size large");
				
				CPU.writeOutput("ABNORMAL"+"Illegal Instruction: Trap Value.\n CLOCK VALUE: "+Integer.toHexString(CPU.clock), System.currentTimeMillis());
				
				System.exit(0);
			case 7:
				System.out.println("Error Code:7 invalid Instruction Format");
				CPU.writeOutput("ABNORMAL"+"invalid Instruction Format.\n CLOCK VALUE: "+Integer.toHexString(CPU.clock), System.currentTimeMillis());
				
				System.exit(0);
			case 8:
				System.out.println("Error Code:8 Memory is locked");
				
				CPU.writeOutput("ABNORMAL.\n Memory is locked. CLOCK VALUE: "+Integer.toHexString(CPU.clock), System.currentTimeMillis());
				
				System.exit(0);
		}
	}

}
