
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class SYSTEM {
	static String file_name;
	static long startTimeIO,startTimeExec;
	static long stopTimeIO,stopTimeExec;
	
	
	
    //Invoking objects for Loader and CPU classes
	public static void main(String[] args) throws IOException {
		
		file_name=args[0];//reading file name from arguements
		startTimeIO = System.currentTimeMillis();
		LOADER load_file = new LOADER();
		load_file.loader(0);
		stopTimeIO=System.currentTimeMillis();
		stopTimeExec= System.currentTimeMillis();
		startTimeExec = System.currentTimeMillis();
		CPU c= new CPU();
		c.cpu(LOADER.PC,LOADER.trace);
		
		
		
	}

}
