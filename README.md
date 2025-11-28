# Compilador-ISHA
implementar un programa para reconocer código fuente escrito en un lenguaje de programación básico
que incluye estructuras y sentencias de control para representar operaciones aritméticas usando variables y
números en base hexadecimal. El proceso de reconocimiento constará de dos fases:

1. Análisis Léxico. Consiste en identificar las unidades léxicas (tokens) del código para determinar si son correctas. Genera una secuencia de tokens y una tabla de símbolos para los identificadores, las literales numéricas y literales de texto. El análisis léxico reportará las unidades léxicas incorrectas y la línea del código en donde se encuentran.

2. Análisis Sintáctico. Consiste en identificar si la secuencia de tokens obtenida del analizador léxico corresponde a estructuras gramaticales correctas del lenguaje de programación. El análisis sintáctico reportará si la compilación del programa es correcta o incorrecta y los errores encontrados.

## Analizador léxico.

Este programa leerá el texto del código fuente, escrito en el lenguaje de programación MIO (“programa.mio”), eidentificará las unidades léxicas del lenguaje; en caso de que una unidad léxica no pertenezca al lenguaje, seimprimirá un mensaje de error que incluya el número de línea del archivo del código de entrada en donde seencontró el error. La secuencia de tokens identificados se escribirá en un archivo de texto (“programa.lex”), untoken por línea. Los elementos del lenguaje de programación MIO son:
> Adicional: '#' al inicio de linea y longitud de linea marca comentario.

|Palabras reservadas|Operadores Relacionales|Operadores aritméticos|
|----|----|----|
|PROGRAMA|Mayor que (>)|Suma (+)|
|FINPROG|Menor que (<)|Resta (-)|
|SI|Igual a (==)|Multiplicación (*)|
|ENTONCES|Mayor o igual (>=)|División (/)|
|SINO|Menor o igual (<=)|----|
|FINSI|Diferente (<>)|----|
|MIENTRAS|Asignación (=)|----|
|HACER|----|----|
|FINM|----|----|
|IMPRIME|----|----|
|LEE|----|----|

## Identificadores.

Son cadenas de caracteres alfanuméricos que se usan para identificar variables y programas,tienen una longitud de hasta 16 caracteres. Inician siempre con un carácter alfabético y son sensibles a mayúsculasy minúsculas. Por ejemplo: DiaDeLaSemana1, variable03, X2

## Literales de texto.

Son cadenas de caracteres alfanuméricos encerrados entre comillas. Por ejemplo: “caracteresalf4num3ric05”. No tienen restricción en su longitud.

## Literales numéricas.

Son cadenas de caracteres numéricos y la letras que representan valores en basehexadecimal. Se utiliza la numeración posicional en base 16 y con los símbolos del 0 al 9 y las letras de la A a la Fcomo los símbolos del sistema numérico. Las constante numéricas iniciarán con la combinación de símbolos “0x”(número cero y letra x minúscula).

La tabla de símbolos constará de tres secciones: la primera es una matriz que almacena los identificadores (IDS[]),la segunda almacena las literales de texto (TXT[]), y la tercera almacena las literales numéricas (VAL[]) Cada fila deuna matriz contiene un token detectado dependiendo de su tipo. Las tres secciones de la tabla de símbolos seescribirán en un archivo (“programa.sim”), una después de otra, en el orden en que se definieron los tokens en elcódigo de entrada.

Las matrices IDS y TXT tendrán dos columnas: la primera contiene el valor del token detectado, y la segundacontiene una clave alfanumérica consecutiva (inicia en ID01 para IDS y en TX01 para TXT). La matriz de literalesnuméricas contiene también dos columnas: la primera almacena el token en su representación hexadecimal, y lasegunda, el valor decimal que corresponda.

Puedes utilizar programas generadores de analizadores léxicos (YACC, LEX, FLEX, etc.) pero deberás ajustarlos paraproducir los archivos de salida “.lex” y “.sim” de acuerdo a la especificación.

## Analizador sintáctico.

Este programa leerá la secuencia del archivo de tokens (“programa.lex”) para identificar las estructurasgramaticales del lenguaje de programación MIO. Como resultado, el programa imprimirá “Compilación exitosa”en caso de que no encuentre errores sintácticos en el código, o “Error: (tipo de sentencia que provocó el error)”.[Puntos extras si imprime el número de línea del código fuente correspondiente]

# Revisión de errores

