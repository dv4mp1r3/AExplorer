package adb;
import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger 
{
	private static List logList;
	private File logFile;
	private static BufferedWriter writer;
	private static final SimpleDateFormat sdf = 
			new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

	@SuppressWarnings("static-access")
	public Logger(List logList)
	{
		this.logList = logList;
		try
		{
			
			Calendar now = Calendar.getInstance();
			SimpleDateFormat sdfTmp = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss");
			logFile = new File("log_" + sdfTmp.format(now.getTime()) + "_.txt");
			logFile.createNewFile();

			writer = new BufferedWriter(new FileWriter(logFile));

		}
		catch(IOException e)
		{
			writeLogfile("loggerFailed");
		}
	}

	public static boolean writeToLog(String msg)
	{
		if (msg == null)
			return false;
		try
		{           
			logList.add(msg);
			logList.select(logList.getItemCount()-1);
			writeLogfile(msg);
		}
		catch(Exception ex) 
		{ 
			ex.printStackTrace(); 
			return false; 
		}
		finally { return true; }

	}

	private static void writeLogfile(String msg)
	{
		Calendar now = Calendar.getInstance();

		try 
		{
			writer.write(sdf.format(now.getTime()) + ": " + msg + System.lineSeparator());
			writer.flush();
		}
		catch (IOException e) { e.printStackTrace(); }
	}

	public void close()
	{
		try { writer.close(); } 
		catch (IOException e) { e.printStackTrace(); }
	}

}
