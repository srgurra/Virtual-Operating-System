import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;



public class CPU {
	static long startTimeExec = System.currentTimeMillis();
	static int ClockMax=100000;
	
	static int clock;
	static File output= new File("output.txt");
	static String NatureTermination;
	
	public static void writeOutput(String s,long endtime) throws IOException {
		Writer output2 = new BufferedWriter(new FileWriter(output));
		output2.write("JOB IDENTIFICATION NUMBER:1\n");
		output2.write("NATURE OF TERMINATION:"+s+"\n");
		output2.write(" Execution time(ms in decimal): "+(endtime-CPU.startTimeExec+"\n"));
		output2.write(" I/O time(ms in decimal): "+(SYSTEM.stopTimeIO-SYSTEM.startTimeIO)+"\n");
		output2.close();
		
	}
	//performs cpu operations with arguements as PC and trace
	public void cpu(int PC,int trace) throws IOException {
		//Initializing the required variables
		clock=0;
		NatureTermination="";
	
		//writing job details to output file
		SYSTEM sys= new SYSTEM();
		//creating registers array
		String[] registers= new String[] {"0000000000000000",
				"0000000000000000",
				"0000000000000000",
				"0000000000000000",
				"0000000000000000",
				"0000000000000000",
				"0000000000000000",
				"0000000000000000"};
		
		//output trace file  
		File outputfile = new File("trace-file.txt");
        Writer output1 = new BufferedWriter(new FileWriter(outputfile));
        if(trace==1) {
           output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "PC", "instruction", "R", "EA","(R)", "(EA)","(R)","(EA)"));
        }
        
        
        
        //invoking memory class
        MEMORY memory = new MEMORY();
        //looping through instructions
        while(true) {
        	//segmenting the instructions
        	String Z= MEMORY.Memory("READ",PC, "");
			int source= Integer.parseInt(Z.substring(7)+MEMORY.Memory("READ",PC+1, "").substring(0,2), 2);
			int dest=Integer.parseInt(Z.substring(4,7), 2);
			int target=Integer.parseInt(MEMORY.Memory("READ",PC+1, "").substring(2,5), 2);
			int immed6,immed12;	
			if(MEMORY.Memory("READ",PC+1, "").substring(2).charAt(0)=='1') {
				immed6=(int)Long.parseLong(("11111111111111111111111111"+MEMORY.Memory("READ",PC+1, "").substring(2)),2);
			}else {
				immed6=Integer.parseInt(MEMORY.Memory("READ",PC+1, "").substring(2),2);
			}
			if((Z.substring(4)+MEMORY.Memory("READ",PC+1, "")).charAt(0)=='1') {
				immed12=(int)Long.parseLong(("11111111111111111111"+Z.substring(4)+MEMORY.Memory("READ",PC+1, "")),2);
			}else {
				immed12=Integer.parseInt(Z.substring(4)+MEMORY.Memory("READ",PC+1, ""),2);
			}
			String instruction;
			String R1,R2,R3;
			String EA1,EA2,EA3;
			String before1,before2,before3,after1,after2,after3;
			//checking for the infinite loop
			if(clock>ClockMax) {
				output1.close();
				ERROR_HANDLER.errorHandler(5);
			}
			//reading the opcode and perrforming actions accordingly
			switch(MEMORY.Read(PC).substring(0,4)) {
			case "0000":
				//addition operation
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					before3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					before3=""+Integer.parseInt((registers[target]),2);
				}
				String s3=Integer.toBinaryString(Integer.parseInt((registers[source]),2)+Integer.parseInt((registers[target]),2));
				int l3=s3.length();
				if(l3>16) {
					s3=s3.substring(l3-16,l3);
					l3=16;
				}
				registers[dest]=l3==16?s3:"0000000000000000".substring(0,16-l3) + s3;
				instruction = "add "+"r"+dest+","+"r"+source+","+"r"+target;
				R1="r"+dest;
				R2="r"+source;
				R3="r"+target;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					after3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					after3=""+Integer.parseInt((registers[target]),2);
				}
				//writing to trace file
				if(trace == 1 ) {
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","",R3,"",before3,"",after3,""));
			    
				}
				clock++;
				PC = PC + 2;
				break;
				//Addition immediate operation
			case "0001":
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				before3=""+immed6;
				String s8=Integer.toBinaryString(Integer.parseInt((registers[source]),2)+immed6);
				int l8 = s8.length();
				
				registers[dest]= l8==16?s8:"0000000000000000".substring(0,16-l8) + s8;
				instruction = "addi "+"r"+dest+","+"r"+source+","+immed6;
				R1="r"+dest;
				R2="r"+source;
				R3="r"+immed6;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				after3=""+immed6;
				//writing to trace file
				if(trace == 1 ) {
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","",R2,"",before2,"",after2,""));}
				clock++;
				PC = PC + 2;
				break;
				//subtraction operation
			case "0010":
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					before3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					before3=""+Integer.parseInt((registers[target]),2);
				}
				String s4=Integer.toBinaryString(Integer.parseInt((registers[source]),2)-Integer.parseInt((registers[target]),2));
	
				int l4=s4.length();
				if(l4>16) {
					s4=s4.substring(l4-16,l4);
					l4=16;
				}
				registers[dest]=l4==16?s4:"0000000000000000".substring(0,16-l4) + s4;
				instruction = "sub "+"r"+dest+","+"r"+source+","+"r"+target;
				R1="r"+dest;
				R2="r"+source;
				R3="r"+target;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					after3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					after3=""+Integer.parseInt((registers[target]),2);
				}
				//writing to trace file
				if(trace == 1 ) {
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","",R3,"",before3,"",after3,""));}
				clock++;
				PC = PC + 2;
				break;
				//subtraction immediate operation
			case "0011":
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				
				
				before3=""+immed6;
				String s7 = Integer.toBinaryString(Integer.parseInt((registers[source]),2)-immed6);
				int l7 = s7.length();
				
				if(l7>16) {
					s4=s7.substring(l7-16,l7);
					l7=16;
				}
				registers[dest]= l7==16?s7:"0000000000000000".substring(0,16-l7) + s7;
				
				instruction = "subi "+"r"+dest+","+"r"+source+","+immed6;
				R1="r"+dest;
				R2="r"+source;
				R3="r"+immed6;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				
				after3=""+immed6;
				//writing to trace file
				if(trace == 1 ) {
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","","",R3,"",before3,"",after3));
				}
				clock++;
				
				PC = PC + 2;
				break;
				//trap operation
			case "0100":
				if(registers[1].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[1]),2);
				}else {
					before1=""+Integer.parseInt((registers[1]),2);
				}
				instruction = "trap "+ immed12;
				R1="r1";
				clock++;
				//HALT condition with trap =0
				if(immed12==0) {
					output1.close();
					
					NatureTermination=NatureTermination+"NORMAL\n AND THE CLOCK VALUE is"+Integer.toHexString(CPU.clock);
					
					writeOutput(NatureTermination, System.currentTimeMillis());
					System.exit(0);
				}else if(immed12==1){
					//output on console when trap equals 1
					clock = clock +10;
					System.out.println(Integer.toHexString(Integer.parseInt(registers[1], 2)));
					if(registers[1].charAt(0)=='1') {
						after1=""+(int)Long.parseLong(("1111111111111111"+registers[1]),2);
					}else {
						after1=""+Integer.parseInt((registers[1]),2);
					}
					if(trace == 1 ) {
					output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", PC,instruction,R1,"",before1,"",after1,""));}
					
					output1.close();
					NatureTermination=NatureTermination+"NORMAL\n AND THE CLOCK VALUE is"+Integer.toHexString(CPU.clock);
					writeOutput(NatureTermination, System.currentTimeMillis());
					System.exit(0);
					
				}else if(immed12==2) {
					//input when trap equals 2
					clock = clock +10;
					System.out.println("Enter value");
					Scanner sc1 = new Scanner(System.in);
					String s2=(Integer.toBinaryString(Integer.parseInt(sc1.next())));
					int l2=s2.length();
					if(l2>16) {
						s2=s2.substring(l2-16,l2);
						l2=16;
					}
					registers[1]=l2==16?s2:"0000000000000000".substring(0,16-l2) + s2;
					if(registers[1].charAt(0)=='1') {
						after1=""+(int)Long.parseLong(("1111111111111111"+registers[1]),2);
					}else {
						after1=""+Integer.parseInt((registers[1]),2);
					}
					if(trace == 1 ) {
						//writing to trace file
					output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", PC,instruction,R1,"",before1,"",after1,""));
					}
	
				}
				else {
					// checking for invalid trace bit
					output1.close();
					ERROR_HANDLER.errorHandler(1);
				}
			
				after1=""+immed12;
				PC = PC + 2;
				break;
				//locking memory through lockArray 
			case "0101":
				clock++;
				before1=""+immed12;
				MEMORY.lock[immed12]=true;
				MEMORY.lock[immed12+1]=true;
				instruction = "lock "+ immed12;
				R1=""+immed12;
				after1=""+immed12;
				if(trace == 1 ) {
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", PC,instruction,"","","","","",""));
				}PC= PC+2;
				break;
				
				//unlocking memory bytes through lockArray
			case "0110":
				clock++;
				before1=""+immed12;
				MEMORY.lock[immed12]= false;
				MEMORY.lock[immed12+1]= false;
				instruction = "unlock "+ immed12;
				R1=""+immed12;
				after1=""+immed12;
				if(trace == 1 ) {
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", PC,instruction,"","","","","",""));
				
				}PC= PC+2;
				break;
				
				//Branching if equal to zero condition
			case "0111":
				clock++;
				if(registers[source].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before1=""+Integer.parseInt((registers[source]),2);
				}
				before2=""+immed6;
				//incrementing PC by immed6 if equal to 0
				if(registers[source].equals("0000000000000000")) {
					instruction = "beqz "+"("+"r"+source+")"+","+immed6;
					R1="("+"r"+source+")";
					R2=""+immed6;
					if(registers[source].charAt(0)=='1') {
						after1=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
					}else {
						after1=""+Integer.parseInt((registers[source]),2);
					}
					//after1=""+Integer.parseInt(findTwoscomplement(registers[source]),2);
					after2=""+immed6;
					if(trace == 1 ) {
					output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", PC,instruction,R1,"",before1,"",after1,""));
					}
					PC = PC + immed6;
				}
				//incrementing PC by 2 if not equal to 0
				else if(!registers[source].equals("0000000000000000")) {
					instruction = "beqz "+"("+"r"+source+")"+","+immed6;
					R1="("+"r"+source+")";
					R2=""+immed6;
					if(registers[source].charAt(0)=='1') {
						after1=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
					}else {
						after1=""+Integer.parseInt((registers[source]),2);
					}
					//after1=""+Integer.parseInt(findTwoscomplement(registers[source]),2);
					after2=""+immed6;
					if(trace == 1 ) {
					output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", PC,instruction,R1,"",before1,"",after1,""));
					}
					PC = PC + 2;
				}
				break;
				//loading from Memory
			case "1000":
				clock++;
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				String s24;
				s24=MEMORY.Memory("READ", Integer.parseInt(registers[source],2) + immed6,"")+
						MEMORY.Memory("READ", Integer.parseInt(registers[source],2) + immed6+1,"");
				if(s24.charAt(0)=='1') {
					EA2=""+(int)Long.parseLong(("111111111111111111111111"+s24),2);
				}else {
					EA2=""+Integer.parseInt((s24),2);
				}
				//EA2=""+Integer.parseInt(Memory[Integer.parseInt(registers[source],2) + immed6]+Memory[Integer.parseInt(registers[source],2) + immed6+1],2);
				
				if(MEMORY.lock[Integer.parseInt(registers[source],2) + immed6]==false) {
						String st=s24;
						//System.out.println(st);
						int l1=st.length();
						registers[dest]=l1==16?st:"0000000000000000".substring(0,16-l1) + st;
				}
				else {
					ERROR_HANDLER.errorHandler(8);
				}
				instruction = "load "+"r"+dest+","+immed6+"("+"r"+source+")";
				R1 = "r"+dest;
				R2="r"+source;
				EA1=""+immed6;
				String s23;
				if((Integer.parseInt(registers[source],2) + immed6)>=LOADER.memStart&(Integer.parseInt(registers[source],2) + immed6)<2*LOADER.instr) {
					output1.close();
					ERROR_HANDLER.errorHandler(2);
				}else {
				s23=MEMORY.Memory("READ", Integer.parseInt(registers[source],2) + immed6,"")+
						MEMORY.Memory("READ", Integer.parseInt(registers[source],2) + immed6+1,"");
				if(s23.charAt(0)=='1') {
					EA3=""+(int)Long.parseLong(("111111111111111111111111"+s23),2);
				}else {
					EA3=""+Integer.parseInt((s23),2);
				}				
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				if(trace == 1 ) {
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","","",EA1,"",EA2,"",EA3));
				}
				PC = PC + 2;}
				break;
				
				//storing into memory
			case "1001":
				clock++;
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				EA1= ""+immed6;
				String s22;
				s22=MEMORY.Read(Integer.parseInt(registers[dest],2) + immed6)+MEMORY.Read(Integer.parseInt(registers[dest],2) + immed6+1);
				if(s22.charAt(0)=='1') {
					EA2=""+(int)Long.parseLong(("1111111111111111"+s22),2);
				}else {
					EA2=""+Integer.parseInt((s22),2);
				}
				
				if(MEMORY.lock[Integer.parseInt(registers[dest],2) + immed6]==false) {
					if((Integer.parseInt(registers[dest],2) + immed6)<2*LOADER.instr&(Integer.parseInt(registers[dest],2) + immed6)>=2*LOADER.memStart) {
						output1.close();
						ERROR_HANDLER.errorHandler(2);
					}else {
						if(registers[source].length()>16) {
							output1.close();
							ERROR_HANDLER.errorHandler(4);
						}
						
						
						MEMORY.write(Integer.parseInt(registers[dest],2) + immed6, registers[source].substring(0,8));
						//[Integer.parseInt(registers[dest],2) + immed6]) = registers[source].substring(0,8);
						MEMORY.write(Integer.parseInt(registers[dest],2) + immed6+1, registers[source].substring(8,16)); 
						}
					
				}
				else {
					//throwing error if memory is locked	
					ERROR_HANDLER.errorHandler(8);
				}
				instruction = "store "+immed6+"("+"r"+dest+")"+","+"r"+source;
				R1="r"+dest;
				R2="r"+source;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				String s21;
				s21=MEMORY.Read(Integer.parseInt(registers[dest],2) + immed6)+MEMORY.Read(Integer.parseInt(registers[dest],2) + immed6+1);
				if(s21.charAt(0)=='1') {
					EA3=""+(int)Long.parseLong(("111111111111111111111111"+s21),2);
				}else {
					EA3=""+Integer.parseInt((s21),2);
				}
				if(trace == 1 ) {
					//writing to trace file
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","","",EA1,"",EA2,"",EA3));
				}PC = PC + 2;
				break;
				
				//Move values from one register to anotherr
			case "1010":
				clock++;
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				String s6=registers[source];
				int l6=s6.length();
				registers[dest]= l6==16?s6:"0000000000000000".substring(0,16-l6) + s6;
				instruction = "move "+"r"+dest+","+"r"+source;
				R1="r"+dest;
				R2="r"+source;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				if(trace == 1 ) {
					//writing to trace file
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","",R2,"",before2,"",after2,""));
				}
				
				PC = PC + 2;
				break;
				//Move immediate value to register
			case "1011":
				clock++;
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				int l=Integer.toBinaryString(immed6).length();
				registers[dest] = l==16?Integer.toBinaryString(immed6):"0000000000000000".substring(0,16-l) + Integer.toBinaryString(immed6);
				
				registers[source] = "0000000000000000";
				instruction = "movei "+"r"+dest+","+immed6;
				R1="r"+dest;
				R2="r"+source;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				if(trace == 1 ) {
					//writing to trace file
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","","",immed6,"",immed6,"",immed6));
				
				}
				PC = PC + 2;
				break;
				
				//Branching if not equal to zero
			case "1100":
				clock++;
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				//incrementing PC by immed6 if not equal to zero
				if(!registers[source].equals("0000000000000000") ) {
					instruction = "bnez "+"r"+source+","+immed6;
					R1="r"+source;
					if(registers[dest].charAt(0)=='1') {
						after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
					}else {
						after1=""+Integer.parseInt((registers[dest]),2);
					}
					if(trace == 1 ) {
					output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", PC,instruction,R1,"",before1,"",after1,""));
					}
					PC = PC + immed6;
				}
				//incrementing PC by 2 if equal to zero
				else if(registers[source].equals("0000000000000000")) {
					
					instruction = "bnez "+"r"+source+","+immed6;
					R1="r"+source;
					
					if(registers[dest].charAt(0)=='1') {
						after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
					}else {
						after1=""+Integer.parseInt((registers[dest]),2);
					}
					
					if(trace == 1 ) {
					output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", PC,instruction,R1,"",before1,"",after1,""));
					}
					PC=PC+2;
				}
				break;
				
				//Shifting if equal to zero
			case "1101":
				clock++;
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					before3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					before3=""+Integer.parseInt((registers[target]),2);
				}
				registers[dest]= Integer.parseInt(registers[source])==Integer.parseInt(registers[target])? "0000000000000001":"0000000000000000";
				instruction = "seq "+"r"+dest+","+"r"+source+","+"r"+target;
				R1="r"+dest;
				R2="r"+source;
				R3="r"+target;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					after3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					after3=""+Integer.parseInt((registers[target]),2);
				}
				if(trace == 1 ) {
					//writing to trace file
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","",R3,"",before3,"",after3,""));
				}
				PC = PC + 2;
				break;
				//
				//shifting if greater than zero
			case "1110":
				clock++;
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					before3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					before3=""+Integer.parseInt((registers[target]),2);
				}
				registers[dest]= Integer.parseInt(registers[source])>Integer.parseInt(registers[target])? "0000000000000001":"0000000000000000";
				instruction = "sgt "+"r"+dest+","+"r"+source+","+"r"+target;
				R1="r"+dest;
				R2="r"+source;
				R3="r"+target;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					after3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					after3=""+Integer.parseInt((registers[target]),2);
				}
				if(trace == 1 ) {
					//writing to trace file
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","",R3,"",before3,"",after3,""));
				}
				PC = PC + 2;
				break;
				//
				//shift if not equal to zero
			case "1111":
				clock++;
				if(registers[source].charAt(0)=='1') {
					before2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					before2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					before1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					before1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					before3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					before3=""+Integer.parseInt((registers[target]),2);
				}
				registers[dest]= Integer.parseInt(registers[source])!=Integer.parseInt(registers[target])? "0000000000000001":"0000000000000000";
				instruction = "sne "+"r"+dest+","+"r"+source+","+"r"+target;
				R1="r"+dest;
				R2="r"+source;
				R3="r"+target;
				if(registers[source].charAt(0)=='1') {
					after2=""+(int)Long.parseLong(("1111111111111111"+registers[source]),2);
				}else {
					after2=""+Integer.parseInt((registers[source]),2);
				}
				if(registers[dest].charAt(0)=='1') {
					after1=""+(int)Long.parseLong(("1111111111111111"+registers[dest]),2);
				}else {
					after1=""+Integer.parseInt((registers[dest]),2);
				}
				if(registers[target].charAt(0)=='1') {
					after3=""+(int)Long.parseLong(("1111111111111111"+registers[target]),2);
				}else {
					after3=""+Integer.parseInt((registers[target]),2);
				}
				if(trace == 1 ) {
					//writing to trace file
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", PC,instruction,R1,"",before1,"",after1,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n", "","",R2,"",before2,"",after2,""));
				output1.write(String.format("%20s %20s %20s %20s %20s %20s %20s %20s \r\n\n\n", "","",R3,"",before3,"",after3,""));
				}
				PC = PC + 2;
				break;
			}
        }
	}	
	
}
