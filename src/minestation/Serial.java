package minestation;


import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration ;

import javax.swing.JOptionPane;




public class Serial implements SerialPortEventListener {
	SerialPort serialPort;

	/** Buffered input stream from the port */
	private InputStream input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	private boolean portEnable=false;

	public String[] list(){
		int cnt=0;
		String PortNames[]=new String[30];
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		
		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			PortNames[cnt++]=currPortId.getName();
		}
		return PortNames;
		
	}
	
	public boolean enabled(){
		return portEnable;
	}
	
	public void outData(String datap){
		  try {
		         // write string to serial port
		         output.write(datap.getBytes());
		      } catch (Exception e) {
		    	  System.err.println(e.toString());
		      }
	}
	
	
	public void initialize(String portName) {

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
		}
		if (portId == null) {
			JOptionPane.showMessageDialog(null, "Could not find COM port.", "Error COM port!", JOptionPane.ERROR_MESSAGE);
			//System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			portEnable=true;
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
			portEnable=false;
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();
				byte chunk[] = new byte[available];
				input.read(chunk, 0, available);

				// Displayed results are codepage dependent
				System.out.print(new String(chunk));
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

/*	public static void main(String[] args) throws Exception {
		serialtest main = new serialtest();
		main.initialize();
		System.out.println("Started");
	}*/
}
