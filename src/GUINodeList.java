import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GUINodeList implements Iterable<String> {
		
		private List<String> nodeNames;
		private List<Ellipse2D> nodes;
		private List<List<String>> nodeMessages = new ArrayList<>();

		private List<Line2D> graphConnections;
		
		
		public GUINodeList() {
			nodeNames = new ArrayList<>();
			nodes = new ArrayList<>();
			graphConnections = new ArrayList<>();
			nodeMessages = new ArrayList<>();
		}
		
		public Ellipse2D getEllipse(String name) {
			return nodes.get(nodeNames.indexOf(name));
		}
		public List<Line2D> getAllConnections() {
			return graphConnections;
		}
		public List<String> getMessages(String name) {
			//return nodeMessages.get(nodeNames.indexOf(name));
			return null;
		}
		
		public void addGUINode(String name, Ellipse2D node) {
			nodeNames.add(name);
			nodes.add(node);
		}
		
		@Override
		public Iterator<String> iterator() {
			return nodeNames.iterator();
		}
		public void clear() {
			nodeNames.clear();
			nodes.clear();
			graphConnections.clear();
			for (List l : nodeMessages) {
				l.clear();
			}
			nodeMessages.clear();
			
		}

		public void addConnection(Line2D conn) {
			graphConnections.add(conn);
			
		}
		
}
