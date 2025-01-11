package muia.tesis.map;

import grammar.Derivation;
import grammar.Grammar;
import grammar.GrammarException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import muia.tesis.map.data.LowLevelMap;

public class LowLevelBuilder implements Builder<LowLevelMap> {
	private int nNodes;
	private List<String> mainContents;
	private int[] nContent;
	private TreeMap<String, String[]> content;
	private String grammarRules;
	private Grammar grammar;
	private Map<String,String> config;
	//private Map<String,String> fixContent;
	//private Map<String,String> secureContent;

	public LowLevelBuilder(int nNodes, List<String> mainContents,
			int[] nContent, TreeMap<String, String[]> content) {
		this.nNodes = nNodes;
		this.mainContents = mainContents;
		this.nContent = nContent;
		this.content = content;
		this.config = Util.loadConfig("gui_config.properties",this.getClass());
		//this.fixContent = Util.loadConfig("fix_content.properties",this.getClass());
		//this.secureContent= Util.loadConfig("secure_content.properties", this.getClass());

		this.grammarRules = rules();
		this.grammar = grammar(this.grammarRules);
		
		//this.orderContent= Util.loadConfig("content.properties", this.getClass());
		
	}

	public LowLevelMap build() {
		Derivation derivation = derivation(this.grammar);
		return new LowLevelMap(this.nNodes, this.mainContents, this.nContent,
				this.content, this.grammarRules, this.grammar, derivation);
	}

	public LowLevelMap[] build(int n) {
		LowLevelMap[] maps = new LowLevelMap[n];
		for (int i = 0; i < n; i++)
			maps[i] = build();
		return maps;
	}

	public LowLevelMap[] crossover(LowLevelMap a, LowLevelMap b)
			throws GrammarException {
		// TODO: check compatibility
		LowLevelMap[] crossover = new LowLevelMap[2];
		List<Derivation> derivations = a.getDerivation().crossoverWX(
				b.getDerivation());
		for (int i = 0; i < 2; i++)
			crossover[i] = new LowLevelMap(this.nNodes, this.mainContents,
					this.nContent, this.content, this.grammarRules,
					this.grammar, derivations.get(i));
		return crossover;
	}

	public LowLevelMap mutate(LowLevelMap original) throws GrammarException {
		// TODO: check compatibility
		return new LowLevelMap(this.nNodes, this.mainContents, this.nContent,
				this.content, this.grammarRules, this.grammar, original
						.getDerivation().mutate());
	}

	private String rules() {
		int max_connection = Integer.parseInt(this.config.get("max_rooms"));
		StringBuilder grammar = new StringBuilder();
		




		grammar.append("#A# S" + "\n"); // Axiom

		
		grammar.append("#N# R Z N NZ "); // Non-terminals
		for (int i = 3; i <= nNodes; i++){
			grammar.append("R" + i + " ");
			grammar.append("N" + i + " ");

		}		
		int count = 0;
		for (String key : content.keySet()) {
			if (nContent[count] > 0 ){//|| 
			//(this.fixContent.containsKey(key) && this.fixContent.get(key).equals("Yes"))|| 
			//(this.secureContent.containsKey(key) && this.secureContent.get(key).equals("Yes"))) 
			grammar.append(key + "s " + key + " "+ key+"Comb ");
			
			}
			count++;
		}
		grammar.append("\n");

		grammar.append("#T# ; : - % 0 1"); // Terminals
		for (int i = 2; nNodes > 1 && i <= nNodes; i++)
			grammar.append(" " + i);
		count = 0;
		for (String key : content.keySet()) {
			if (nContent[count] > 0){// || 
			//(this.fixContent.containsKey(key) && this.fixContent.get(key).equals("Yes"))|| 
			//(this.secureContent.containsKey(key) && this.secureContent.get(key).equals("Yes")))
				grammar.append(" " + String.join(" ", content.get(key)));
			}
			count++;
		}
		grammar.append("\n");

		grammar.append("S ::= "); // P - Axiom
		if (nNodes > 2) grammar.append("R ");
		if (this.mainContents.size() > 0) grammar.append("; Z ");
		count = 0;
		for (String key : content.keySet()) {
			if (nContent[count] > 0){ //|| 
			//(this.fixContent.containsKey(key) && this.fixContent.get(key).equals("Yes"))|| 
			//(this.secureContent.containsKey(key) && this.secureContent.get(key).equals("Yes"))) 
			grammar.append("; " + key + "s ");
			
			}
			count++;
		}
		grammar.append("\n");
		//grammar.append("C ::= 0 | 1");


		if (nNodes > 2) { // P - Room connections
			grammar.append("R ::= ");
			for (int i = 3; i <= Math.min(nNodes,max_connection); i++) {
				grammar.append(i+" % "+"R" + i);
				if (i != nNodes) grammar.append(" : ");
			}
			grammar.append("\n");

			
			for (int i = 3; i <= nNodes; i++) {
				grammar.append("R" + i + " ::= ");

				//List<String> connections = roomConnections();
				int maxConn = Integer.parseInt(this.config.get("max_connections"));
				List<String> rooms_conns = roomConnections(i,maxConn);
				for (int j = 0; j < rooms_conns.size(); j++){
					grammar.append(" "+rooms_conns.get(j));
					if (j != rooms_conns.size() - 1) grammar.append(" | ");
				}
				grammar.append("\n");

				
				grammar.append((i-1)+"\n");

				grammar.append("N" + i + " ::= ");
				for (int j = 1; j < i-1; j++) {
					grammar.append(j+" | ");
				}
				grammar.append((i-1)+"\n");

			}
		}

		grammar.append("N" + " ::= ");
				for (int j = 1; j < this.nNodes; j++) {
					grammar.append(j+" | ");
				}
		grammar.append(this.nNodes+"\n");		
		grammar.append("NZ ::= 0 | N\n");

		

		if (this.mainContents.size() > 0) { // P - Main Contents
			grammar.append("Z ::= ");

			List<String> combs = new ArrayList<>();
			List<Integer> conns = new ArrayList<>();
			for (int i = 1; i <= this.nNodes; i++)
				conns.add(i);
			String mainContents_combs="";
			for (int i=0;i<this.mainContents.size()-1;i++){
				mainContents_combs=mainContents_combs+"N : ";
			}
			mainContents_combs=mainContents_combs+"N\n";

			
			grammar.append(mainContents_combs);
		}

		

		

		count = 0;
		int fixCountentSize;
		for (String key : content.keySet()) { // P - Contents
			fixCountentSize = nContent[count];


			if (fixCountentSize > 0) {
				String content_key_setup=key+"Comb ::=";
				for (int i = 1; i <= fixCountentSize; i++) {
					content_key_setup += " " + key;
					if (i != fixCountentSize) content_key_setup += " :";
				}
				content_key_setup+=" \n";
				grammar.append(content_key_setup);
				grammar.append(key + "s ::= ");

				List<String> combs = new ArrayList<>();
				List<Integer> conns = new ArrayList<>();
				List<List<String>> combsContent;

				
				for (int i = 1; i <= nNodes; i++)
					conns.add(i);
				System.out.println("what"+nNodes+"okey"+fixCountentSize);
				if (nNodes <=10 || nNodes-3<fixCountentSize || fixCountentSize<=10){
					combsContent=combinations(conns, fixCountentSize,
					false);
					for (List<String> comb : combsContent) {
						String cont = String.join(" : ", comb);
						cont += " ; ";
						cont+=key+"Comb ";
						combs.add(cont);
					}
					grammar.append(String.join((char) 32 + "|" + (char) 32, combs));
					grammar.append("\n");
				}else {

					String mainContents_combs="";
					for (int i=0;i<fixCountentSize-1;i++){
						mainContents_combs=mainContents_combs+"N : ";
					}
					mainContents_combs=mainContents_combs+"N ; "+key+"Comb \n";

					
					grammar.append(mainContents_combs);

					
				}
				


				

				grammar.append(key + " ::= "); // P - Contents (term)
				grammar.append(String.join((char) 32 + "|" + (char) 32,
						content.get(key)));
				grammar.append("\n");
			}

			count++;
		}


		
		
//		System.out.println(grammar.toString());
		return grammar.toString();
	}

	private Grammar grammar(String rules) {
		Grammar grammar = null;
		System.out.println("Low Rules :\n"+rules);
		try {
			
			File temp = File.createTempFile("grammar_low", ".gr");
			BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
			writer.write(rules);
			writer.close();

			grammar = new Grammar(new BufferedReader(new FileReader(temp)));
		} catch (IOException | GrammarException e) {
			e.printStackTrace();
		}
		return grammar;
	}

	private static Derivation derivation(Grammar grammar) {
		Derivation derivation = null;
		try {
			derivation = new Derivation(grammar, 10); // TODO: why 10?
		} catch (GrammarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return derivation;
	}

	private static List<List<String>> combinations(List<Integer> n, int k,
			boolean repeat) {	
		if (repeat) {
			System.out.println("nice");
			return repeating_combinations(n, k,new HashMap<String,List<List<String>>>());
		} else {
			System.out.println("fuck");
			List<List<String>> comb = combinar(n,k);
			
			return comb;
		}
	}

	public static List<List<String>> combinar(List<Integer> numeros, int k) {
        List<List<String>> resultado = new ArrayList<>();
        
        // Optimización cuando k == n
        if (k == numeros.size()) {
            List<String> combinacion = new ArrayList<>();
            for (Integer num : numeros) {
                combinacion.add(String.valueOf(num));
            }
            resultado.add(combinacion);
            return resultado;
        }
        
        // Optimización cuando k > n / 2
        if (k > numeros.size() / 2) {
            return complementarCombinaciones(numeros, numeros.size() - k);
        }
        
        generarCombinaciones(numeros, k, 0, new ArrayList<>(), resultado);
        return resultado;
    }

    private static List<List<String>> complementarCombinaciones(List<Integer> numeros, int kComplementario) {
        List<List<String>> complementos = new ArrayList<>();
        generarCombinaciones(numeros, kComplementario, 0, new ArrayList<>(), complementos);
        
        List<List<String>> resultado = new ArrayList<>();
        for (List<String> complemento : complementos) {
            List<Integer> combinacionNumeros = new ArrayList<>(numeros);
            for (String strNum : complemento) {
                combinacionNumeros.remove(Integer.valueOf(strNum));
            }
            List<String> combinacion = new ArrayList<>();
            for (Integer num : combinacionNumeros) {
                combinacion.add(String.valueOf(num));
            }
            resultado.add(combinacion);
        }
        return resultado;
    }

    private  static void generarCombinaciones(List<Integer> numeros, int k, int indiceInicio, List<Integer> combinacionActual, List<List<String>> resultado) {
        if (combinacionActual.size() == k) {
            List<String> combinacionString = new ArrayList<>();
            for (Integer num : combinacionActual) {
                combinacionString.add(String.valueOf(num));
            }
            resultado.add(combinacionString);
            return;
        }
        
        for (int i = indiceInicio; i < numeros.size(); i++) {
            combinacionActual.add(numeros.get(i));
            generarCombinaciones(numeros, k, i + 1, combinacionActual, resultado);
            combinacionActual.remove(combinacionActual.size() - 1);
        }
    }

	private static List<List<String>> repeating_combinations(List<Integer> n,
			int k,Map<String,List<List<String>>> cache) {
		List<List<String>> comb = new ArrayList<>();
		if (k == 1) {
			for (int i : n)
				comb.add(Arrays.asList("" + i));
			return comb;
		}
		String key = String.valueOf(k-1);
		for (int i : n) {
			List<List<String>> combs;
			if (cache.containsKey(key)){
				combs = cache.get(key);
			}else{
				combs = repeating_combinations(n, k - 1,cache);
				cache.put(key,combs);
			}
			
			for (List<String> c : combs) {
				c = new ArrayList<>(c);
				c.add("" + i);
				comb.add(c);
			}
		}
		return comb;
	}

	private static String key_ListConns(List<Integer> n,int nNodes){
		String id="";
		Boolean first=false;
		
		for (int i = 1; i <=nNodes; i++){
			if(n.contains(i)){
				id = id.concat("_"+i);
			}
		}
		return id;
					
	}

	private static Set<Set<String>> unique_combinations(List<Integer> n, int k,Map<String,Set<Set<String>>> cache,int nNodes) {
		Set<Set<String>> comb = new HashSet<>();
		if (k == 1) {
			for (int i : n)
				comb.add(new HashSet<>(Arrays.asList("" + i)));
			return comb;
		}
		for (int i : n) {
			
			Set<Set<String>> combs;
			List<Integer> subN = new ArrayList<>(n);
			subN.remove(subN.indexOf(i));
			
			String key = (k-1)+key_ListConns(subN, nNodes);

			if (cache.containsKey(key)){
				combs = cache.get(key);
			}else{
				combs = unique_combinations(subN, k - 1,cache,nNodes);
				cache.put(key,combs);
			}
			for (Set<String> c : combs) {
				c = new HashSet<>(c);
				c.add("" + i);
				comb.add(c);
			}
		}
		return comb;
	}

	
	/**
     * Verifica si la cantidad de '1' en la representación binaria de un número
     * es mayor o igual que un número máximo especificado.
     *
     * @param bin     El número entero que se verificará.
     * @param maxUnos La cantidad mínima de '1' requerida.
     * @return true si la cantidad de '1' es mayor o igual a maxUnos, de lo contrario false.
     */
    public static boolean checkPrune(int bin, int maxUnos) {
        // Convierte el número a binario y cuenta los '1'
        int count = Integer.bitCount(bin);
        // Verifica si la cantidad de '1' es mayor o igual a maxUnos
        return count <= maxUnos;
    }


	private List<String> roomConnections(int n,int maxConn) {
		List<String> connections = new ArrayList<>();
		
		for (int i = 0;i<Math.min(n-1,maxConn) ; i++) {
			
			connections.add(oneConnections(i, Math.min(n-1,maxConn),n));
			
		}
		return connections;
	}

	private static String oneConnections(int pos_set,int n_elem,int idNode) {
		String connection="";
		for (int j = 0; j < n_elem; j++) {
			if (j==pos_set){
				connection=connection.concat(" "+"N"+idNode);
			}else{
				connection=connection.concat(" "+"NZ");
			}

			if (j!=n_elem-1)
				connection=connection.concat(" %");
		}
		
		return connection;
	}
}
