# Ejemplo de cliente/servidor TCP con Java

## Funcionamiento básico

El cliente se conecta al servidor para solicitar el precio de items (texto libre).

El servidor mantiene la lista de items y sus precios en un diccionario (Map<String, Double>):
- Si el item está registrado, lo registra con un precio aleatorio
- Devuelve el precio del item

## Características principales

- El servidor es MULTIHILO, de forma que puede atender a varios clientes de simultaneamente. Esto se consigue pasando el handler de cliente a un hilo nuevo cada vez que se acepta una nueva conexión de un cliente. De esta forma, el hilo principal del programa se desentiende y puede continuar aceptando nuevos clientes inmediatamente.
- Todos los clientes reciben los mismos precios para los productos. Esto se consigue manteniendo el diccionario de productos y precios en una única variable estática, declarada en la clase Servidor.

## Ampliación

El código de servidor contiene un bug sutil, que es poco probable que se manifieste en las pruebas en clase, pero que seguro que se manifestaría si se atienden varios cientos o miles de clientes de forma simultanea. ¿Sabríais identificarlo y corregirlo?

## Consejo: guías de estilo

Los IDEs son muy buenos indicando fallos sintácticos, que hacen que el programa no compile y no se ejecute. Pero, como hablamos en clase, el siguiente objetivo más allá escribir código limpio y mantenible. Respecto a este aspecto, resultan especialmente útiles las herramientas conocidas como *linters*.

Un buen ejemplo de este tipo de herramientas es [sonarlint](https://www.sonarsource.com/es/products/sonarlint/). Este linter soporta varios IDEs (a través de plugins) y varios lenguajes de programación. Os ayudará a escribir código limpio, evitar estructuras poco mantenibles y, sobre todo, a continuar aprendiendo a escribir buen código.