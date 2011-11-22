package minestation;
//import java.io.IOException;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.Date;
import java.awt.event.*;
import javax.swing.*;
 

 public class timer implements ActionListener {
	Timer timer = new Timer(500,this); // El timer que se encarga de administrar los tiempo de repeticion
	public int segundos=0; // manejar el valor del contador
	public boolean frozen=false; // manejar el estado del contador TIMER AUTOMATICO -- True Detenido | False Corriendo
 
	public void start(){
		timer.start();
	}
	
	public void stop(){
		timer.stop();
	}
/*	// clase interna que representa una tarea, se puede crear varias tareas y asignarle al timer luego
	class MiTarea extends TimerTask {
		public void run() {
			segundos++;
			System.out.println(segundos);
			// aqui se puede escribir el codigo de la tarea que necesitamos ejecutar
		
		}// end run()
	}// end SincronizacionAutomatica
 
	public void Start(int pSeg){// throws Exception {
		
		// le asignamos una tarea al timer
		if (frozen==false) timer.schedule(new MiTarea(), 0, pSeg );
		else timer.restart();
	}// end Start
 
	public void Stop() {
		System.out.println("Stop");
		timer.cancel();
		timer.purge();

		frozen = true;
	}// end Stop
 
    public void Reset() {
		System.out.println("Reset");
		frozen = true;
		segundos = 0;
	}// end Reset*/
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == timer) { 
			Minestation.sendData();
		//	segundos++;
    	//	System.out.println(segundos);
    	}
      }
}// end Reloj



