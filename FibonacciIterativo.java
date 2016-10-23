package jasrsir.pspro;

import javax.print.attribute.standard.NumberOfDocuments;

/**
 * Ejercicio de Fibonacci Iterativo a través de 2 hilos. El ejercicio contiene:
 *  - Clase FibonacciIterativo (main) -> llama a HiloPrincipal
 *  - Clase HiloPrincipal -> Contiene en su .run()la creación de los 2 hilos para el calculo
 *  - CLase Hilo -> Clase responsable del cálculo de fibonacci
 *
 * @author Juan Antonio Suárez Rosa
 * @version 1.0
 *
 */
public class FibonacciIterativo {
	
	public static void main(String[] args) {
		// Si el número es 2 o menos no hay cálculo
		if (Integer.parseInt(args[0]) <= 2) {
			System.out.println("Pon un número mayor que 2 si quieres un cálculo en condiciones hombre!");
		} else {
			HiloPrincipal hiloPrincipal = new HiloPrincipal(Integer.parseInt(args[0]));
			hiloPrincipal.run();
		}
	}

}

//Clase con el cuerpo del código
class HiloPrincipal implements Runnable{
	
	//Variables
	public static int fibonacci = 0;
	private Hilo fib1;
	private Hilo fib2;
	private int numero;
	
	//Constructor que inicializa el número a calcular
	public HiloPrincipal(int n){
		numero = n;
	}
	
	//Metodo run que Inicializa los 2 hilos para el cálculo
	@Override
	public void run() {
		
		//Try-catch para los cálculos y posibles errores
		try {
			fib1 = new Hilo(numero-1);  // No llamo a .start() porque en el constructor
			fib2 = new Hilo(numero-2);  // ya hago correr el hilo en él.
			fib1.esperaAlOtro();
			fib2.esperaAlOtro();
			
			fibonacci = fib1.getResult() + fib2.getResult();
		} catch (InterruptedException e) {
			//nohay nada en el catch
		}
		
		//Imprimimos el resultado
		System.out.println("El fibonacci de " + numero + " es : " + fibonacci);
	}
	
}

/**
 * Clase Hilo que implementa la interfaz Runnable para la
 * la implementación de los hilos en la sucesión de Fibonacci.
 * debiendo crear una instancia de thread dentro.
 * @author jasrsir
 */
class Hilo implements Runnable {
	
	//Variables
	private int numfib = 0;
	private int resultado;
	private int fib1 = 1;
	private int fib2 = 1;
	private Thread hilito;
	
	//Constructor
	public Hilo(int numFib) {
		this.numfib = numFib;
		hilito = new Thread(this);  //Creamos el hilo
		hilito.start();				//Corremos el hilo.
	}
	
	//Método run
	@Override
	public void run() {
		
 		for (int i = 2; i <= numfib; i++) {
 			resultado = fib1 + fib2;
 			fib2 = fib1;
 			fib1 = resultado;
 		}
	}
	
	//Método que pone en espera al hilo para el resultado completo
	public void esperaAlOtro() throws InterruptedException {
		hilito.join();
	}
	
	//Método que devuelve el resultado obtenido;
	public int getResult() {
		return resultado;
	}
}
