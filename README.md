#Ejercicio 5.12
Ejercicio 5.12 del libro Simulacion un enfoque practico en java

##Consideraciones

1. El ejercicio nos dice que la razón de llegada de los clientes es de 20 clientes por hora, como los clientes no llegan todos al mismo tiempo, tenemos una razon de llegadas de 1 cliente cada 3 minutos.
2. Todos los tiempos obtenidos en minutos son redondeados a dos decimales.
3. Para la realizacion del ejercicio se generan 50 mil numeros aleatorios.
4. __Consideraciones para los parametros al instanciar la clase Simulacion:__

	'Simulacion simulacion = new Simulacion(10, 30, 5, 127, 11, 100003, 20, 2, 1, 2);'

	Datos a ingresar 
	+ __tiempoSimulacion__: Expresado en horas y es de tipo entero.
	+ __numeroCorridas__: Entero.

	+ __Numeros Aleatorios__ (Método Congruencial mixto)
		+ __x__: Semilla, entero.
		+ __a__: Multiplicador, entero.
		+ __c__: Incremento, entero.
		+ __m__: Módulo, entero.

	+ Informacion del ejercicio
		+ __mediaPoisson__: double
		+ __mediaExponencial__: double
		+ __rangoAUniforme__: double
		+ __rangoBUniforme__: double

5. Para numeros grandes de corridas u horas de simulación, tener en cuenta la cantidad de números aleatorios disponibles.
