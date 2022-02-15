package dev.codescreen.marketplace;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.common.base.Predicate;

@SpringBootApplication
public class MarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApplication.class, args);
        teste();
    }

    private static void teste() {
    	
    	var ENTRADA = List.of(new log(1,"abc", new Date()),
			    			new log(1,"asa", new Date()),
			    			new log(1,"asas", new Date()),
			    			new log(1,"gdsf", new Date()),
			    			new log(1,"asas", new Date()),
			    			new log(1,"abc", new Date()),
			    			new log(1,"abc", new Date()));
    	
    	var RESULT = List.of(new log(3,"abc", new Date()),
			    			new log(1,"asa", new Date()),
			    			new log(2,"asas", new Date()),
			    			new log(1,"gdsf", new Date()));

    	
    	Map<String, List<log>> collect = ENTRADA.stream().collect(Collectors.groupingBy(log::getTexto)).entrySet().stream().;
    	
    	//list.stream().reduce(new log(),(a, b) -> a.texto == b.texto, null)
    	
    	UnaryOperator<String> inverter = s -> new StringBuilder(s).reverse().toString();
    	Function<String, Integer> convertInteger = s -> Integer.parseInt(s,2);
    	Predicate<Integer> filtro = a -> a > 2;
    	BinaryOperator<Integer> soma = (a, b) -> a + b;
    	
    	var list = List.of(1,2,3,4,5,6,7,8);
    	var a = list.stream().reduce(0, soma);
    					//.forEach(System.out::println);
    	System.out.println(a);
    	 
    }
    
    static class log{
    	private Integer quantidade;
    	private String texto;
    	private Date data;
    	
    	public log(Integer id, String texto, Date data) {
    		this.quantidade = quantidade;
    		this.setTexto(texto);
    		this.data = data;
    	}
    	
    	

    	public log() {
    		
    	}



		public String getTexto() {
			return texto;
		}



		public void setTexto(String texto) {
			this.texto = texto;
		}
    }
    
}
