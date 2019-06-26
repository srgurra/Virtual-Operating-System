
public class MEMORY {
	
	//creating memory and a lock array(to check if memory byte is locked or not) of 1024 bytes
	public static String[] Memory= new String[1024];
	public static boolean[] lock = new boolean[1024];
	//invoking MEMORY(X,Y,Z) procedure to perform read, write and dump operations
	public static String Memory(String X,int Y,String Z) {
		long startTime3 = System.currentTimeMillis();
		//MEMORY read
		if(X=="READ") {
			String str = Read(Y);
			return str;
		}
		//Memory write
		else if(X=="WRITE") {
			write(Y, Z);
			return null;
		}
		//Memory Dump
		else {
			DUMP();
			return null;
		}
	}
	//Memory write
	public static void write(int i, String Z) {
		Memory[i]=Z;
	}
	//Memory read
	public static String Read(int n) {
		return Memory[n];
	}
	//memory dump
	public static void DUMP() {
		for(int i=0; i<64; i++) {
			String t;
			switch(i) {
			case 0:
				t="0000";
				System.out.printf("%-20s",t);
				break;
			case 8:
				t="0008";
				System.out.printf("%-20s",t);
				break;
			case 16:
				t="0016";
				System.out.printf("%-20s",t);
				break;
			case 24:
				t="0024";
				System.out.printf("%-20s",t);
				break;
			case 32:
				t="0032";
				System.out.printf("%-20s",t);
				break;
			case 40:
				t="0040";
				System.out.printf("%-20s",t);
				break;
			case 48:
				t="0048";
				System.out.printf("%-20s",t);
				break;
			case 56:
				t="0056";
				System.out.printf("%-20s",t);
				break;
			case 64:
				t="0064";
				System.out.printf("%-20s",t);
				break;
			}
			if(Memory[i]==null) {
				System.out.printf("%-20s","00000000");
			}
			else {
				System.out.printf("%-20s",Memory[i]);
			}
			if(i%8==7) {
				if(Memory[i]==null) {
					System.out.printf("%-20s","00000000");
				}
				else {
					System.out.printf("%-20s",Memory[i]);
				}
				
				System.out.println();
			}
			
		}
		
		System.out.println();
	}
	
}
