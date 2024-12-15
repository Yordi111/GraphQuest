package muia.tesis.map.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import muia.tesis.HighLevelGrammarBaseListener;
import muia.tesis.HighLevelGrammarParser;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import muia.tesis.map.Util;


public class HighLevelMapLoader extends HighLevelGrammarBaseListener {
	private static final Logger log = LoggerFactory
			.getLogger(HighLevelMapLoader.class);

	private Graph graph;
	private TreeMap<String, String[]> content;
	private int count = 0;
	private int [] rooms;
	private int[][] contentCounts;
	private List<List<String>> mainContents;
	private String style = "graph { padding: 50px; fill-color: rgb(240, 240, 240); }\n"
			+ "edge { size: 3px; }\n"
			+ "node { stroke-mode: plain; stroke-width: 3px;"
			+ "fill-color: rgb(240, 240, 240); shape: rounded-box; size-mode: fit;"
			+ "padding: 10px; text-padding: 2px; text-style: bold; text-size: 16px;"
			+ "shadow-mode: gradient-radial; }";
	static String[] colors = { "red", "green", "blue", "cyan", "magenta",
			"yellow", "aquamarine" };

	private Map<String,String> MainContentConfig;
	private Map<String,String> fixContent;
	private Map<String,String> secureContent;
	

	private List<String> keysContent;

	
	public HighLevelMapLoader(TreeMap<String, String[]> content,int[] rooms) {
		log.info("Processing high level map");
		
		this.content = content;
		this.rooms = rooms;

		this.MainContentConfig = Util.loadConfig("main_content.properties",this.getClass());
		this.fixContent = Util.loadConfig("fix_content.properties",this.getClass());
		this.secureContent= Util.loadConfig("secure_content.properties", this.getClass());
		this.keysContent = new ArrayList<>(Util.loadConfig("content.properties", this.getClass()).keySet());
		

		this.graph = new SingleGraph("");
		this.graph.setAutoCreate(true);
		this.graph.setStrict(false);

		if (rooms.length>1){
			this.graph.addEdge("1-2", "1", "2");
			this.graph.getNode("2").setAttribute("cons", "1");
			this.graph.getNode("1").setAttribute("cons", "*,2");
		}else{
			this.graph.addNode("1");
		}

		
		
		this.graph.addAttribute("ui.stylesheet", this.style);
	}

	public Graph graph() {
		return this.graph;
	}
	
	public int[][] contentCounts() {
		return this.contentCounts;
	}
	
	public List<List<String>> mainContents() {
		return this.mainContents;
	}

	@Override
	public void enterConnect(HighLevelGrammarParser.ConnectContext ctx) {	
		this.graph.addEdge(ctx.getText() + "-" + (this.count + 1),
				ctx.getText(), "" + (this.count + 1));

		Node thisNode = this.graph.getNode("" + (this.count + 1));
		Node otherNode = this.graph.getNode(ctx.getText());

		thisNode.setAttribute("cons", otherNode.getId());
		otherNode.setAttribute("cons", otherNode.getAttribute("cons") + ","
				+ thisNode.getId());
	}

	@Override
	public void enterContents(HighLevelGrammarParser.ContentsContext ctx) {
		int[] cont = new int[ctx.cont().size()];
		for (int i = 0; i < ctx.cont().size(); i++){
			cont[i] = Integer.parseInt(ctx.cont().get(i).getText());
			
			if (i<this.keysContent.size() &&this.secureContent.containsKey(this.keysContent.get(i)) 
			&& this.secureContent.get(this.keysContent.get(i)).equals("Yes") && cont[i]==0){
				cont[i]=1;
			}
			
			if (i<this.keysContent.size() && this.fixContent.containsKey(this.keysContent.get(i)) && this.fixContent.get(this.keysContent.get(i)).equals("Yes")){
				cont[i]=this.rooms[this.count];
			}
		}
			
			

		this.graph.getNode("" + (this.count + 1)).setAttribute("cont", cont);
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		if (node.getText().equals(";"))
			this.count++;
	}

	private boolean checkItemCode(int idNode,int nNodes,String code){
		switch (code) {
			case "NONE":
				return false;
			case "LAST":
				return idNode==nNodes-1;
			case "NOT_LAST":
				return idNode!=nNodes-1;
			case "FIRST":
				return idNode==0;
			case "NOT_FIRST":
				return idNode!=0;
			case "FIRST_NOT_LAST":
				return idNode!=nNodes-1 || nNodes==1;
			case "LAST_NOT_FIRST":
				return idNode!=0 || nNodes==1;
			default:
				return false;
		}
	}

	private void addPossList(int idNode,String poses,List<String> contentList,String key){
		List<Integer> numbers = parseNumbers(poses);
		if (numbers==null){
			throw new IllegalArgumentException("Bad Format main_content.properties in propertie:"+key);
		}
		if (numbers.size()>0){
			if (numbers.contains(idNode))
				addKeyContent(contentList,key);
		}
	}

	private void addKeyContent(List<String> contentList,String key){
		if (!contentList.contains(key))
			contentList.add(key);
	}


	public static List<Integer> parseNumbers(String input) {
        String[] items = input.split(",");
        List<Integer> numbers = new ArrayList<>();
        
        for (String item : items) {
            try {
                // Intenta convertir a entero
                int number = Integer.parseInt(item.trim());
                numbers.add(number);
            } catch (NumberFormatException e) {
                // Si hay un error de formato, retorna null o maneja el error
                return null;
            }
        }
        return numbers;
    }

	private void addContent(List<String> contentList,int idNode){

		for (Map.Entry<String, String> entry : this.MainContentConfig.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String[] valuesEntries = value.split(";");
			int numNodes = this.graph.getNodeCount();
			if (valuesEntries[0]!=null && checkItemCode(idNode, numNodes, valuesEntries[0])){
				addKeyContent(contentList,key);
			}
			if (valuesEntries.length==3 && valuesEntries[1]!=null && valuesEntries[2]!=null &&valuesEntries[1].equals("YES")  ){
				addPossList(idNode,valuesEntries[2],contentList,key);
			}

			
			
		}
	}

	@Override
	public void exitMap(HighLevelGrammarParser.MapContext ctx) {
		log.info("Completed high level map graph with {} nodes", this.graph
				.getNodeSet().size());

		this.contentCounts = new int[this.graph.getNodeCount()][this.content.size()];
		this.mainContents = new ArrayList<>();
		for (int i = 0; i < this.graph.getNodeCount(); i++) {
			Node node = this.graph.getNode(i);
			node.addAttribute("ui.label", node.getId());
			node.setAttribute("ui.style", "stroke-color:"
					+ colors[i % colors.length] + ";");

			List<String> mCont = new ArrayList<String>();
			
			if (node.getAttribute("cons")!=null)
				mCont.addAll(Arrays.asList(((String) node.getAttribute("cons")).split(",")));

			addContent(mCont,i);

			int[] cont = node.getAttribute("cont");
			this.contentCounts[i] = cont;
			
			this.mainContents.add(mCont);
		}
	}
}
