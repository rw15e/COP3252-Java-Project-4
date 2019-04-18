import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.util.Date;
import java.io.*;

public class FileViewer {
    public static void main(String[] args) throws FileNotFoundException, IOException
    {		
		boolean information = false;
		boolean copy = false;
		boolean view = false;
		boolean noParamtersGiven = false; 
		boolean compare = false;
		String viewParameter = " ";
		String copyParameter1 = " ";
		String copyParameter2 = " ";
		String infoParameter = " ";
		String compareParameter1 = " ";
		String compareParameter2 = " ";
		
		if(args.length == 0){ 
			//System.out.println("NO PARAMETER GIVEN \n");
			information = true;	
			noParamtersGiven = true;
			infoParameter = "."; // this fixed my NullPointerException 
		}
		else if(args.length == 1){ 
			//System.out.println("ONE PARAMETER GIVEN \n");
			if(args[0].equals("-i")){
				information = true;
				infoParameter = ".";
				//System.out.println("INFORMATION SELECTED \n");
			}	
		}	
		else if(args.length == 2){ 
			//System.out.println("2 PARAMETERS GIVEN \n");
			if(args[0].equals("-i")){
				information = true;
				infoParameter = args[1];
				//System.out.println("INFORMATION SELECTED \n");
			}	
			else if(args[0].equals("-v")){
				view = true;
				viewParameter = args[1];
				//System.out.println("VIEW SELECTED \n");
			}
			else if(args[0].equals("-c")){
				// do nothing here
			}
			else{
				System.out.print("Usage: java -jar hw4.jar [-i [<file>|<directory>]|-v <file>|-c <sourceFile> <destFile>|-d <file1> <file2>]");
				return;
			}
		}			
		else if(args.length == 3){
			//System.out.println("3 PARAMETERS GIVEN \n");
			if(args[0].equals("-c")){
				copy = true;
				copyParameter1 = args[1];
				copyParameter2 = args[2];
				//System.out.println("COPY SELECTED \n");
			}
			else if(args[0].equals("-d")){
				compare = true;
				compareParameter1 = args[1];
				compareParameter2 = args[2];
				//System.out.println("COMPARE SELECTED \n");
			}
			else{
				System.out.print("Usage: java -jar hw4.jar [-i [<file>|<directory>]|-v <file>|-c <sourceFile> <destFile>|-d <file1> <file2>]");
				return;
			}
		}
		else{
			System.out.print("Usage: java -jar hw4.jar [-i [<file>|<directory>]|-v <file>|-c <sourceFile> <destFile>|-d <file1> <file2>]");
			return;
		}
		
		if(compare = true){
			int x = 0;
			int y = 0;
			try{ 
				File file1 = new File(compareParameter1);
				File file2 = new File(compareParameter2);
			
				if(file1.length() == file2.length()){ // if the files are same size continue on with search
					InputStream in1 = new BufferedInputStream(new FileInputStream(file1));
					InputStream in2 = new BufferedInputStream(new FileInputStream(file2));
				while(x != -1 || y != -1){
					x = in1.read(); // will return next byte of data, or a -1 if its end of stream
					y = in2.read(); // will return next byte of data, or a -1 if its end of stream
					if(x != y)
						System.out.println("THE FILES DO NOT MATCH!");
				}
				System.out.println("THE FILES MATCH!");
				}
				else // if files are different size then they cannot be identical!
				System.out.println("THE FILES DO NOT MATCH!");	
			} // end of try block
			catch(FileNotFoundException ex){
				 //System.out.println(ex);
			}
			System.out.println();
		}
		
		if(information == true){ // execute code selected by user who passed "-i" and possibly a second parameter file/folder		
			File file1 = new File(infoParameter);
			long size = 0;
			if(args.length <= 1){
				infoParameter = ".";  // if no parameter is given, default to "." directory 
				System.out.println("Size		Filename");
				File[] filesList = file1.listFiles();
				for(File f : filesList){
					size = f.length();
					if(f.isDirectory())
						System.out.print("*		");
					else
						System.out.print(size + "		");
					if(f.isDirectory())
						System.out.println(f.getName());
					if(f.isFile())
						System.out.println(f.getName());		
				}
			}
			else if(args.length == 2){										
				if(file1.isFile()){  // infoParameter is a file
					System.out.println("File Path: "+ file1.getAbsolutePath());
					System.out.print("Is executable? ");
					if(file1.canExecute())
						System.out.println("True");
					else
						System.out.println("False");
					System.out.println("Size: " + file1.length());
					System.out.print("Last modified date: ");
					long time = 0;
					time = file1.lastModified();
					Date date = new Date(time);
					System.out.println(date);
				}
				else if(file1.isDirectory()){ // infoParameter is a directory
					infoParameter = ".";
					System.out.println("Size		Filename");
					File[] filesList = file1.listFiles();
					for(File f : filesList){
						size = f.length();
						if(f.isDirectory())
							System.out.print("*		");
						else
							System.out.print(size + "		");
						if(f.isDirectory())
							System.out.println(f.getName());
						if(f.isFile())
							System.out.println(f.getName());		
					}
				}
				else // infoParameter is NOT a valid file OR directory
					System.out.print("Error: Invalid File");
				}
		}

		if(view == true){ // execute code selected by user who passed "-v" and a second parameter which is the file to view
		try{
			File file1 = new File(viewParameter);
			if(file1.isFile()){  // infoParameter is a file
				Scanner fileInput = new Scanner(new File(viewParameter));
				while(fileInput.hasNextLine())
					System.out.println(fileInput.nextLine()); // prints out each line while there are still lines to print
			}			
			else
				System.out.print("Error: Invalid File");
			}
			catch(FileNotFoundException ex){ // catch the file exception
				 System.out.println(ex);
			}
		}
		
		if(copy == true){ // execute code selected by user who passed "-c" and 2 more parameters, the first being sourcefile and second being destination
			File source = new File(copyParameter1);
			File destination = new File(copyParameter2);
			if(source.isFile()){  // source is a file
				if(destination.isFile())  // destination is aleady a file. error, dont overwrite it!
					System.out.println("Error: Destination file already exists!");
				else{
			        try (FileReader reader = new FileReader(copyParameter1); FileWriter writer = new FileWriter(copyParameter2);) 	
					{
						int x = 1;; 
						while(x != -1) { // returns -1 when done
							writer.write(x);
							x = reader.read();
						}
					}
					catch(IOException ex) { // catch the file reader exception
						System.out.println(ex);
					}
					System.out.println(copyParameter1 + " was successfully copied to " + copyParameter2 + "!\n");
				}
			}
			else
				System.out.print("Error: Source file is not valid");
		}		
	}
}