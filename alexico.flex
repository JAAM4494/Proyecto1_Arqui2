

/* --------------------------Codigo de Usuario----------------------- */
package arm.compiler;

import java_cup.runtime.*;
import java.io.Reader;
import java.util.*;
import arm.compiler.BancoInstrucciones;
import arm.compiler.ManejadorErrores;
       
%% //inicio de opciones
   
/* ------ Seccion de opciones y declaraciones de JFlex -------------- */  
   
/* 
    Cambiamos el nombre de la clase del analizador a Lexer
*/
%class AnalizadorLexico

/*
    Activar el contador de lineas, variable yyline
    Activar el contador de columna, variable yycolumn
*/
%line
%column

%caseless
%ignorecase
    
/* 
   Activamos la compatibilidad con Java CUP para analizadores
   sintacticos(parser)
*/
%cup
   
/*
    Declaraciones

    El codigo entre %{  y %} sera copiado integramente en el 
    analizador generado.
*/


%{
    /*  Generamos un java_cup.Symbol para guardar el tipo de token 
        encontrado */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Generamos un Symbol para el tipo de token encontrado 
       junto con su valor */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
	
	
	ArrayList<String> instruccion = new ArrayList<String>();
	BancoInstrucciones administrador = new BancoInstrucciones();
	ManejadorErrores moduloErrores = new ManejadorErrores();
	
	private void añadirTermino (String nuevoTermino){
		instruccion.add(nuevoTermino);
	}

	private void añadirInstruccion (){
		administrador.añadirInstruccion(new ArrayList<String>(instruccion));
		instruccion.clear();
	}

%}
Salto = \r|\n|\r\n
Comment = ";" ~{Salto}
Espacio = [ \t\f]
Decimal = "-"[0-9][0-9]* | [0-9][0-9]*
Etiqueta = [0-9]*[a-zA-Z][a-zA-Z]*[0-9]*
Registro = "R"[0-9] | "R""1"[0-5]
Vector = "V"[0-9] | "V""1"[0-5]

And = and
Eor = eor
Eorv = eorv
Sub = sub
Subv = subv
Rsb = rsb
Add = add
Addv = addv
Adc = adc
Sbc = sbc
Rsc = rsc
Cmp = cmp
Cmn = cmn
Orr = orr
Mov = mov
Movv = movv
Lsl = lsl
Lslv = lslv
Lsrv = lsrv
Asr = asr
Rrx = rrx
Ror = ror
Rorv = rorv
Rol = rol
Rolv = rolv
Bic = bic
Mvn = mvn
Mul = mul
Mla = mla
Str = str
Strv = strv
Ldr = ldr
Ldrv = ldrv
Strb = strb
Ldrb = ldrb
B = b
Beq = beq
Bne = bne
Blt = blt
Bgt = bgt



%% //fin de opciones
/* -------------------- Seccion de reglas lexicas ------------------ */
   
/*
   Esta seccion contiene expresiones regulares y acciones. 
   Las acciones son código en Java que se ejecutara cuando se
   encuentre una entrada valida para la expresion regular correspondiente */
   
   /* YYINITIAL es el estado inicial del analizador lexico al escanear.
      Las expresiones regulares solo serán comparadas si se encuentra
      en ese estado inicial. Es decir, cada vez que se encuentra una 
      coincidencia el scanner vuelve al estado inicial. Por lo cual se ignoran
      estados intermedios.*/
   
<YYINITIAL> {
							
    ","                {   
							return symbol(sym.COMA); }
							
	"#"                {   
								añadirTermino ("#");	
							return symbol(sym.NUMERAL); }
							
	"["                {  
						  //añadirTermino ("[");
                          return symbol(sym.LLAVEIZQ); }
	"]"                {  
						  //añadirTermino ("]");
                          return symbol(sym.LLAVEDER); }
						  
	{And}                {añadirTermino ("and");
                          return symbol(sym.AND); }
						  
	{Eor}                {añadirTermino ("eor");
                          return symbol(sym.EOR); }

	{Eorv}                {añadirTermino ("eorv");
                          return symbol(sym.EORV); }
						  
	{Sub}                {añadirTermino ("sub");
                          return symbol(sym.SUB); }

	{Subv}                {añadirTermino ("subv");
                          return symbol(sym.SUBV); }
						  
	{Rsb}                {añadirTermino ("rsb");
                          return symbol(sym.RSB); }
						  
	{Add}                {añadirTermino ("add");
		          System.out.println("Prueba");
                          return symbol(sym.ADD); }
	
	{Addv}                {añadirTermino ("addv");

                          return symbol(sym.ADDV); }
	
						  
	{Adc}                {añadirTermino ("adc");
                          return symbol(sym.ADC); }
						  
	{Sbc}                {añadirTermino ("sbc");
                          return symbol(sym.SBC); }
						  
	{Rsc}                {añadirTermino ("rsc");
                          return symbol(sym.RSC); }
						  
	{Orr}                {añadirTermino ("orr");
                          return symbol(sym.ORR); }
						  
	{Mvn}                {añadirTermino ("mvn");
                          return symbol(sym.MVN); }
						  
	{Bic}                {añadirTermino ("bic");
                          return symbol(sym.BIC); }
						
	{Mul}                {añadirTermino ("mul");
                          return symbol(sym.MUL); }
						  
	{Cmp}                {añadirTermino ("cmp");
                          return symbol(sym.CMP); }
						  
	{Cmn}                {añadirTermino ("cmn");
                          return symbol(sym.CMN); }
						  
	{Mov}                {añadirTermino ("mov");

                          return symbol(sym.MOV); }
	
	{Movv}                {añadirTermino ("movv");

                          return symbol(sym.MOVV); }
						  
	{Lsl}                {añadirTermino ("lsl");
                          return symbol(sym.LSL); }
	
	{Lslv}                {añadirTermino ("lslv");
                          return symbol(sym.LSLV); }
	
	{Lsrv}                {añadirTermino ("lsrv");
                          return symbol(sym.LSRV); }
	
						  
	{Asr}                {añadirTermino ("asr");
                          return symbol(sym.ASR); }
						  
	{Rrx}                {añadirTermino ("rrx");
                          return symbol(sym.RRX); }
						  
	{Ror}                {añadirTermino ("ror");
                          return symbol(sym.ROR); }

	{Rol}                {añadirTermino ("rol");
                          return symbol(sym.ROL); }

	{Rorv}                {añadirTermino ("rorv");
                          return symbol(sym.RORV); }

	{Rolv}                {añadirTermino ("rolv");
                          return symbol(sym.ROLV); }
						  
	{Mla}                {añadirTermino ("mla");
                          return symbol(sym.MLA); }
						  
	{Str}                {añadirTermino ("str");
                          return symbol(sym.STR); }

	{Strv}                {añadirTermino ("strv");
                          return symbol(sym.STRV); }
						  
	{Ldr}                {añadirTermino ("ldr");
                          return symbol(sym.LDR); }
	{Ldrv}                {añadirTermino ("ldrv");
                          return symbol(sym.LDRV); }
						  
	{Strb}                {añadirTermino ("strb");
                          return symbol(sym.STRB); }
						 
	{Ldrb}                {añadirTermino ("ldrb");
                          return symbol(sym.LDRB); }
						  
	{B}                {añadirTermino ("b");
                          return symbol(sym.B); }
					
	{Beq}                {añadirTermino ("beq");
                          return symbol(sym.BEQ); }
						  
	{Bne}                {añadirTermino ("bne");
                          return symbol(sym.BNE); }
						  
	{Bgt}                {añadirTermino ("bgt");
                          return symbol(sym.BGT); }
						  
	{Blt}                {añadirTermino ("blt");
                          return symbol(sym.BLT); }
		
    {Decimal}      {   
					  añadirTermino (yytext());
                      return symbol(sym.DECIMAL, new Integer(yytext())); }


    {Espacio}       { /* ignora el espacio */ } 
	
	{Comment}    { //System.out.println("Comentario ingresado");
					añadirInstruccion();} 
	
	{Salto}			{añadirInstruccion();}
					  
	{Registro}	{  
					  añadirTermino (yytext().toLowerCase());
                      return symbol(sym.REGISTRO, yytext()); }
	{Vector}	{  
					  añadirTermino (yytext().toLowerCase());
                      return symbol(sym.VECTOR, yytext()); }

					  
	{Etiqueta}	{  	  //System.out.println("Etiqueta ingresada");
					  añadirTermino (yytext());
                      return symbol(sym.ETIQUETA); }
}


/* Si el token contenido en la entrada no coincide con ninguna regla
    entonces se marca un token ilegal */
[^]                    { 
							System.out.println("Se ha añadido un error lexico"+yytext());
							moduloErrores.setErrorLexico(yycolumn, yyline, yytext());
						}
