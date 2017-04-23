public class CPU {
	
	private ControlUnity cont_unity; 
	
	/**Registradores e memória de dados e instruções(Harvard)**/
	private String[] inst_mem; // Memória de instruções
	private int[] data_mem, regs; // Array de registradores e de memoria de dados
	private boolean[] used_regs, used_mem; // Array que indica quais registradores e espaço de memória que estão sendo armazenando algum dado
	
	/**Registradores padrões**/
	private int pc, mar;
	private String mbr, ir;
	
	/**Construtor padrão**/
	public CPU(int nreg, int[] mem_data, String[] mem_inst){
		/**Numero de registradores**/
		regs = new int[nreg];
		used_regs = new boolean[nreg];
		
		/**Referencia para a memória principal**/
		data_mem = mem_data;
		inst_mem = mem_inst;
		used_mem = new boolean[mem_data.length];
		
		this.pc = 0;
		
		/**Inicializa os arrays de consulta de uso**/
		for(int i = 0; i < regs.length; i++)
			used_regs[i] = false;
		
		for(int i = 0; i < data_mem.length; i++)
			used_mem[i] = false;
		
		/**Inicializa a unidade de controle (responsável por executar as operações)**/
		cont_unity = new ControlUnity(inst_mem, data_mem, regs, used_regs, used_mem);
	}
	
	public void run(){
		String[] tokens;
		while(pc < inst_mem.length && inst_mem[pc] != null){
			fetch();
			tokens = decode();
			exec(tokens);
		}
		System.out.println("\n\t\t================ END ================\n");
	}
	
	public void fetch(){
		System.out.println("\t\t=============== FETCH ===============\n");

		System.out.println("\t\t\t[MAR] <- [PC]: ");
		mar = pc;
		System.out.println("\t\t\t[MAR] = " + "I" + mar + "\n");
		
		System.out.println("\t\t\t[MBR] <- [MEM(MAR)]: ");
		mbr = inst_mem[mar];
		System.out.println("\t\t\t[MBR] = " + mbr + "\n");

		System.out.println("\t\t\t[PC] <- PC + 1: ");
		pc++;
		System.out.println("\t\t\t[PC] = " + "I" + pc + "\n");
		
		System.out.println("\n\t\t=====================================\n");
	}
	
	public String[] decode(){
		System.out.println("\n\t\t=============== DECODE ==============\n");
		
		int i = 1;
		ir = mbr;
		String[] inst;
		inst = ir.split(" ");
		
		System.out.println("\t\t\tOperation: " + inst[0]);
		
		System.out.print("\t\t\tRegisters: ");
		while(i < inst.length){
			if(inst[i].charAt(0) == 'R')
				System.out.print(inst[i] + " ");
			else
				break;
			i++;
		}
		
		System.out.print("\n\t\t\tAdress: ");
		while(i < inst.length){
			if(inst[i].charAt(0) == 'M' || inst[i].charAt(0) == 'I')
				System.out.print(inst[i] + " ");
			else
				break;
			i++;
		}
		
		System.out.print("\n\t\t\tNumbers: ");
		while(i < inst.length){
			System.out.print(inst[i] + " ");
			i++;
		}
		
		System.out.println("\n\n\t\t=====================================\n");
		
		return inst;
	}
	
	public void exec(String[] instruct){
		System.out.println("\n\t\t============== EXECUTE ==============\n");
		switch(instruct[0]){
		
		case "STORE":
			cont_unity.store(instruct);
			break;
		case "LOAD":
			cont_unity.load(instruct);
			break;
			
		case "ADD":
			cont_unity.add(instruct);
			break;
		
		case "SUB":
			cont_unity.sub(instruct);
			break;
		
		case "MPY":
			cont_unity.mpy(instruct);			
			break;
			
		case "DIV":
			cont_unity.div(instruct);
			break;

		case "JUMPZ":
			if(cont_unity.jumpz(instruct) >= 0){
				pc = cont_unity.jumpz(instruct);
				System.out.println("\t\t\tSalto: Instrução " + pc);
			}
			break;
			
		case "JUMPN":
			if(cont_unity.jumpn(instruct) >= 0){
				pc = cont_unity.jumpn(instruct);
				System.out.println("\t\t\tSalto: Instrução " + pc);
			}
			break;
			
		case "JUMPP":
			if(cont_unity.jumpp(instruct) >= 0){
				pc = cont_unity.jumpp(instruct);
				System.out.println("\t\t\tSalto: Instrução " + pc);
			}
			break;
			
		default:
			System.out.println("\t\t\tErro: Operação inválida");
		}
		
		System.out.println("\n\t\t=====================================\n");
	}


}
