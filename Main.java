package arq2_proj1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Main {

	private static String[] mem_inst;
	private static int[] mem_data;
	
	public static void main(String[] args) {
		
		try{
			String dir = JOptionPane.showInputDialog(null, "Digite o diretório do arquivo");
			FileReader file = new FileReader(dir); /**Localizacao do arquivo .ts*/
			BufferedReader arq = new BufferedReader(file);
			
			CPU cpu;
			
			if(args.length == 0){
				mem_data = new int[64];
				mem_inst = new String[64];
				cpu = new CPU(mem_data, mem_inst);
			}else{
				int nreg = Integer.parseInt(args[0]), mem_size = Integer.parseInt(args[1]);
				mem_data = new int[mem_size];
				mem_inst = new String[mem_size];
				cpu = new CPU(nreg, mem_data, mem_inst);
			}
			
			while(arq.ready()){
				memory_fetch(arq, cpu);
				cpu.run();
			}
			
			arq.close();
			file.close();
			
		}catch(FileNotFoundException fnf){
			System.out.println("Arquivo nao encontrado");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static void memory_fetch(BufferedReader br, CPU up) throws IOException{
		int i = 0, size = mem_inst.length;
		String line;
		
		while(i < size && br.ready()){
			line = br.readLine();
			mem_inst[i] = line;
			i++;
		}
	}

}
