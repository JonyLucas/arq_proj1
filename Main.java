import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javax.swing.JOptionPane;

public class Main {

	private static String[] mem_inst;
	private static int[] mem_data;
	
	public static void main(String[] args) {
		
		try{
			String dir = JOptionPane.showInputDialog(null, "Digite o diretório do arquivo");
			int file_size = countLines(dir); //Conta o numero de linhas do arquivo
			FileReader file = new FileReader(dir); //Localizacao do arquivo
			BufferedReader arq = new BufferedReader(file);

			//System.out.println(file_size);
			
			CPU cpu;
			mem_inst = new String[file_size];
			
			if(args.length == 0){
				mem_data = new int[64]; //Tamanho padrão da memória de dados
				cpu = new CPU(3, mem_data, mem_inst); //Por padrão é utilizado o 3 registradores
			}else{
				int nreg = Integer.parseInt(args[0]), mem_size = Integer.parseInt(args[1]);
				mem_data = new int[mem_size];
				cpu = new CPU(nreg, mem_data, mem_inst);
			}
			
			/**Carrega o arquivo na memoria**/
			memory_fetch(arq);
			
			/**CPU executa o programa**/
			cpu.run();	
			
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
	
	public static int countLines(String filename) throws IOException {
	    LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
		int cnt = 0;
		while (reader.readLine() != null);
	
		cnt = reader.getLineNumber(); 
		reader.close();
		return cnt;
	}
	
	public static void memory_fetch(BufferedReader br) throws IOException{
		int i = 0, size = mem_inst.length;
		String line;
		
		while(i < size && br.ready()){
			line = br.readLine();
			if(line.equals("")){
				continue;
			}
			
			mem_inst[i] = line;
			i++;
		}
	}

}
