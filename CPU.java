package arq2_proj1;

public class CPU {
	
	private ControlUnity cont_uni = new ControlUnity(); 
	
	/**Registradores e mem�ria de dados e instru��es(Harvard)**/
	private String[] inst_mem;
	private int[] data_mem, regs; // Array de registradores e de memoria de dados
	
	/**Registradores padr�es**/
	private int pc, mar;
	private String mbr, ir;
	private boolean fz, fp, fn; //Flags, fz (zero), fp (positivo), fn (negativo)
	
	/**Construtor padr�o**/
	public CPU(int[] mem_data, String[] mem_inst){ 
		/**Numero de registradores - valor padr�o 3**/
		regs = new int[3]; 
		
		/**Referencia para a mem�ria principal**/
		data_mem = mem_data; 
		inst_mem = mem_inst;
		
		this.pc = 0;
	}
	
	/**Construtor com valores de par�metros**/
	public CPU(int nreg, int[] mem_data, String[] mem_inst){
		regs = new int[nreg];
		data_mem = mem_data;
		inst_mem = mem_inst;
	}
	
	public void run(){
		
		//while(true){
			fetch();
			decode();
			//exec();
			//print
		//}
		
	}
	
	public void fetch(){
		mar = pc;
		mbr = inst_mem[mar];
	}
	
	public String[] decode(){
		ir = mbr;
		String[] inst;
		inst = ir.split(" ");
		for(String s : inst){
			System.out.print(s + " ");
		}
		
		return inst;
	}

}
