public class ControlUnity {
	
	/**Registradores e memória de dados e instruções(Harvard)**/
	private String[] inst_mem; // Memória de instruções
	private int[] data_mem, regs; // Array de registradores e de memoria de dados
	private boolean[] used_regs, used_mem; // Array que indica quais registradores e espaço de memória que estão sendo armazenando algum dado
	
	/**Flags usadas por operações de desvios**/
	private boolean fz, fp, fn; //Flags, fz (zero), fp (positivo), fn (negativo)
	
	public ControlUnity(String[] inst_mem, int[] data_mem, int[] regs, boolean[] used_regs, boolean[] used_mem){
		this.inst_mem = inst_mem;
		this.data_mem = data_mem;
		this.regs = regs;
		this.used_regs = used_regs;
		this.used_mem = used_mem;
	}
	
	/**Operação de armazenamento**/
	
	public void store(String[] tokens){

		System.out.println("\t\t\tSTORE" + tokens[1] + " " + tokens[2]);
		int reg = Integer.parseInt(tokens[1].substring(1));
		
		if(reg > regs.length-1 && used_regs[reg] == false){
			System.out.println("\t\t\tErro: Registrador inválido");
			return;
		}
		
		/**Verifica se o parâmetro é um endereço válido (começa com X)**/
		if(tokens[2].charAt(0) != 'M'){
			System.out.println("\t\t\tErro: Endereço inválido");
			return;
		}
		
		/**Execução da operação de armazenamento**/
		
		int dest = Integer.parseInt(tokens[2].substring(1));
		data_mem[dest] = regs[reg];
		used_mem[dest] = true;
		System.out.println("\t\t\tResultado: (MEM)" + tokens[2] + " = " + data_mem[dest]);
		
	}
	
	/**Operação de carga**/
	
	public void load(String[] tokens){
		
		System.out.println("\t\t\tLOAD " + tokens[1] + " " + tokens[2]);
		int val, reg = Integer.parseInt(tokens[1].substring(1));
		
		if(reg > regs.length-1){
			System.out.println("\t\t\tErro: Registrador inválido");
			return;
		}
		
		try{
			val = verify_operand(tokens[2]);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return;
		}
		
		/**Execução da operação de carga**/
		
		regs[reg] = val;
		used_regs[reg] = true;
		System.out.println("\t\t\tResultado: " + tokens[1] + " = " + regs[reg]);
	}
	
	/**Operação de adição**/
	
	public void add(String[] tokens){
		
		if(tokens.length == 3){	//Operação de adição que envolve 1 registradores e um endereço, ou um valor inteiro, ou outro registrador
			
			System.out.println("\t\t\tADD " + tokens[1] + " " + tokens[2]);
			int reg, op2;
			
			try{
				reg = verify_destiny(tokens[1]);
				op2 = verify_operand(tokens[2]);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return;
			}
			
			/**Execução da operação aritmética**/
						
			regs[reg] = regs[reg] + op2;
			set_flags(regs[reg]);
			
			System.out.println("\t\t\tResultado: " + regs[reg]);
			
		}else if(tokens.length == 4){ //Operação de adição que envolve 3 registradores, 2 para efetuar a soma e um para armazenar o resultado
			
			System.out.println("\t\t\tADD " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
			int dest = Integer.parseInt(tokens[1].substring(1)), op1, op2;
			
			if(dest > regs.length-1){
				System.out.println("\t\t\tErro: Registrador inválido");
				return;
			}
			
			try{
				op1 = verify_operand(tokens[2]);
				op2 = verify_operand(tokens[3]);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return;
			}
			
			/**Execução da operação aritmética**/
			
			regs[dest] = op1 + op2;
			used_regs[dest] = true;
			set_flags(regs[dest]);
			System.out.println("\t\t\tResultado: " + regs[dest]);
		}else{
			System.out.println("\t\t\tErro: Número de argumentos inválido");
		}
	}
	
	/**Operação de subtração**/
	
	public void sub(String[] tokens){
	
		if(tokens.length == 3){	//Operação de subtração que envolve 1 registradores e um endereço, ou um valor inteiro, ou outro registrador
			
			System.out.println("\t\t\tSUB " + tokens[1] + " " + tokens[2]);
			int reg, op2;
			
			try{
				reg = verify_destiny(tokens[1]);
				op2 = verify_operand(tokens[2]);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return;
			}	
			
			/**Execução da operação aritmética**/
			
			regs[reg] = regs[reg] - op2;
			set_flags(regs[reg]);
			System.out.println("\t\t\tResultado: " + regs[reg]);
			
		}else if(tokens.length == 4){
			
			System.out.println("\t\t\tSUB " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
			int dest = Integer.parseInt(tokens[1].substring(1)), op1, op2;
			
			if(dest > regs.length-1){
				System.out.println("\t\t\tErro: Registrador inválido");
				return;
			}
			
			try{
				op1 = verify_operand(tokens[2]);
				op2 = verify_operand(tokens[3]);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return;
			}
			
			/**Execução da operação aritmética**/
			
			regs[dest] = op1 - op2;
			used_regs[dest] = true;
			set_flags(regs[dest]);
			System.out.println("\t\t\tResultado: " + regs[dest]);
			
		}else{
			System.out.println("\t\t\tErro: Número de argumentos inválido");
		}
	}
	
	/**Operação de multiplicação**/
	
	public void mpy(String[] tokens){

		if(tokens.length == 3){		
			
			System.out.println("\t\t\tMPY " + tokens[1] + " " + tokens[2]);
			int reg, op2;
			
			try{
				reg = verify_destiny(tokens[1]);
				op2 = verify_operand(tokens[2]);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return;
			}
			
			/**Execução da operação aritmética**/
			
			regs[reg] = regs[reg] * op2;
			set_flags(regs[reg]);
			System.out.println("\t\t\tResultado: " + regs[reg]);
			
		}else if(tokens.length == 4){
			
			System.out.println("\t\t\tMPY " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
			int dest = Integer.parseInt(tokens[1].substring(1)), op1, op2;
			
			if(dest > regs.length-1){
				System.out.println("\t\t\tErro: Registrador inválido");
				return;
			}
			
			try{
				op1 = verify_operand(tokens[2]);
				op2 = verify_operand(tokens[3]);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return;
			}
			
			/**Execução da operação aritmética**/
			
			regs[dest] = op1 * op2;
			used_regs[dest] = true;
			set_flags(regs[dest]);
			System.out.println("\t\t\tResultado: " + regs[dest]);
			
		}else{
			System.out.println("\t\t\tErro: Número de argumentos inválido");
		}
	}
	
	/**Operação de divisão**/
	
	public void div(String[] tokens){
	
		if(tokens.length == 3){			
			
			System.out.println("\t\t\tDIV " + tokens[1] + " " + tokens[2]);
			int reg, op2;
			
			try{
				reg = verify_destiny(tokens[1]);
				op2 = verify_operand(tokens[2]);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return;
			}

			if(op2 == 0){
				System.out.println("\t\t\tErro: Denomidor igual a 0");
				return;
			}				
			
			/**Execução da operação aritmética**/
			
			regs[reg] = regs[reg]/op2;
			set_flags(regs[reg]);
			System.out.println("\t\t\tResultado: " + regs[reg]);
			
		}else if(tokens.length == 4){
			
			System.out.println("\t\t\tDIV " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
			int dest = Integer.parseInt(tokens[1].substring(1)), op1, op2;
			
			if(dest > regs.length-1){
				System.out.println("\t\t\tErro: Registrador inválido");
				return;
			}
			
			try{
				op1 = verify_operand(tokens[2]);
				op2 = verify_operand(tokens[3]);
			}catch(Exception e){
				System.out.println(e.getMessage());
				return;
			}
			
			if(op2 == 0){
				System.out.println("\t\t\tErro: denomidor igual a 0");
				return;
			}
			
			/**Execução da operação aritmética**/
			
			regs[dest] = op1/op2;
			used_regs[dest] = true;
			set_flags(regs[dest]);
			System.out.println("\t\t\tResultado: " + regs[dest]);
		}else{
			System.out.println("\t\t\tErro: Número de argumentos inválido");
		}
	}
	
	/**Verifica o registrador que armazena o resultado**/
	public int verify_destiny(String reg_dest) throws Exception{
		
		int reg = Integer.parseInt(reg_dest.substring(1));
		
		/**Verifica se o primeiro argumento(reg) possui algum dado armazenado**/
		if(reg > regs.length-1 && used_regs[reg] == false){
			throw new Exception("\t\t\tErro: Registrador inválido");
		}
		
		return reg;
	}
	
	/**Verifica os operandos**/
	public int verify_operand(String operand) throws Exception{
		
		int op;
		/**Verifica se o segundo argumento é um endereço, um registrador ou uma valor fixo**/
		if(operand.charAt(0) == 'M'){ //Caso seja um endereço na memoria de dados	
			op = Integer.parseInt(operand.substring(1)); //Pega o valor do endereço
			
			if(used_mem[op]) //Verifica se o endereço possui algum dado
				op = data_mem[op];
			else
				throw new Exception("\t\t\tErro: Endereço da memória não utilizado");
			
			
		}else if(operand.charAt(0) == 'R'){ //Caso seja um registrador
			op = Integer.parseInt(operand.substring(1));
			
			if(op < regs.length && used_regs[op]) //Verifica se o registrador possui algum valor armazenado
				op = regs[op];
			else
				throw new Exception("\t\t\tErro: Registrador inválido");
			
			
		}else{
			op = Integer.parseInt(operand); //Caso o segundo parametro seja um valor inteiro
		}
		
		return op;
		
	}
	
	/**Seta as flags para operações de desvios**/
	
	public void set_flags(int result){
	
		if(result == 0){
			fz = true;
			fn = false;
			fp = false;
		}else if(result > 0){
			fz = false;
			fn = false;
			fp = true;
		}else{
			fz = false;
			fn = true;
			fp = false;
		}
	}
	
	/**Desvio (JUMP) quando ultima operação aritmética for igual a 0**/
	public int jumpz(String[] tokens){
		
		if(fz){ //Verifica a flag
			if(tokens[1].charAt(0) == 'I')//Verifica se o endereço da instruçao é válido
				if(Integer.parseInt(tokens[1].substring(1)) < inst_mem.length) //Verifica se o endereço da instruçao esta dentro do programa
					return Integer.parseInt(tokens[1].substring(1)); //Retorna próxima instrução
		}
		
		System.out.println("\t\t\tNão ocorre desvio");
		
		return -1; //Não ocorre desvio
	}
	
	/**Desvio (JUMP) quando ultima operação aritmética for igual a 0**/
	public int jumpn(String[] tokens){
		
		if(fn){ //Verifica a flag
			if(tokens[1].charAt(0) == 'I')//Verifica se o endereço da instruçao é válido
				if(Integer.parseInt(tokens[1].substring(1)) < inst_mem.length) //Verifica se o endereço da instruçao esta dentro do programa
					return Integer.parseInt(tokens[1].substring(1)); //Retorna próxima instrução
				
		}
		
		System.out.println("\t\t\tNão ocorre desvio");
		
		return -1; //Não ocorre desvio
	}
	
	/**Desvio (JUMP) quando ultima operação aritmética for igual a 0**/
	public int jumpp(String[] tokens){
		
		if(fp){ //Verifica a flag
			if(tokens[1].charAt(0) == 'I')//Verifica se o endereço da instruçao é válido
				if(Integer.parseInt(tokens[1].substring(1)) < inst_mem.length) //Verifica se o endereço da instruçao esta dentro do programa
					return Integer.parseInt(tokens[1].substring(1)); //Retorna próxima instrução
				
		}
		
		System.out.println("\t\t\tNão ocorre desvio");
		
		return -1; //Não ocorre desvio
	}

}
