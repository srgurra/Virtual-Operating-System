import java.io.File;
import java.util.Scanner;

public class LOADER {
	
	static int instr;
	static int trace;
	static int PC;
	static int memStart;
	
	//loading data from loader file
	public void loader(int X) {	
		
		Scanner sc;
		int n=0;
		int i=0;
		File file = new File(SYSTEM.file_name); 
		try {
			sc = new Scanner(file);
			instr=Integer.parseInt(sc.next(),16);
			memStart=Integer.parseInt(sc.next(),16);
			PC=memStart;
			String s= "";
			while(s.length()<4*instr) {
				s= s+sc.next();	
			}
			//checking if the instructions size is larger than memory
			MEMORY memory = new MEMORY();
			if(2*instr>memory.Memory.length) {
				ERROR_HANDLER.errorHandler(3);
			}
			//checking for memory range fault error
			if(s.length()<4*instr|s.length()>4*instr) {
				ERROR_HANDLER.errorHandler(2);
			}
			//checking whether the size of bits is 16 and appending extra zeroes
			while(n<2*instr) {
				try {
					String bin=Integer.toBinaryString(Integer.parseInt(s.substring(i,i+2 ), 16));
					int len = bin.length();
					memory.Memory[n]=(len == 8 ? bin : "00000000".substring(0,8-len) + bin);	
					i=i+2;
					n++;
				}
				catch(NumberFormatException ne){
					ERROR_HANDLER.errorHandler(7);
				}
				
				
				
			}
			//reading trace bit
			trace=Integer.parseInt(sc.next());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
