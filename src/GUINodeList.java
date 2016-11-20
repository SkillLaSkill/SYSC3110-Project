import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Creates a list of all the visual representations of the nodes that have been inputed
 * 
 * @author Team GetterDone
 */
public class GUINodeList implements Iterable<String> {
		private List<String> nodeNames;
		private List<Ellipse2D> nodes;
		private List<List<String>> nodeMessages = new ArrayList<>();

		private List<Line2D> graphConnections;
		
		/**
		 * Creates an instance of GUINodeList and initialized the values
		 */
		public GUINodeList() {
			nodeNames = new ArrayList<>();
			nodes = new ArrayList<>();
			graphConnections = new ArrayList<>();
			nodeMessages = new ArrayList<>();
		}
		
		/**
		 * Gets the Ellipse2D object given its name
		 * 
		 * @param name (String) - Name of the Ellipse2D
		 * @return Ellipse2D - Object with the given name
		 */
		public Ellipse2D getEllipse(String name) {
			return nodes.get(nodeNames.indexOf(name));
		}
		
		/**
		 * Gets all of the connections in terms of Line2D objects
		 * 
		 * @return List<Line2D> - List of connection objects
		 */
		public List<Line2D> getAllConnections() {
			return graphConnections;
		}
		
		/**
		 * Gets all of the messages object (not implemented atm)
		 * 
		 * @param name (String) - Name of object you want messages from
		 * @return List<String> - List of all messaged
		 */
		public List<String> getMessages(String name) {
			return null;
		}
		
		/**
		 * Adds a new node to the GUI given its name and the Ellipse2D object
		 * 
		 * @param name (String) - Name of the object
		 * @param node (Ellipse2D) - Node visual object
		 */
		public void addGUINode(String name, Ellipse2D node) {
			nodeNames.add(name);
			nodes.add(node);
		}
		
		/**
		 * Iterates through all of the node names
		 * 
		 * @return Iterator<String> - Iterator of all the node names
		 */
		@Override
		public Iterator<String> iterator() {
			return nodeNames.iterator();
		}
		
		/**
		 * Resets the GUI to its initial look
		 */
		public void clear() {
			nodeNames.clear();
			nodes.clear();
			graphConnections.clear();
			for (List l : nodeMessages) {
				l.clear();
			}
			nodeMessages.clear();
		}

		/**
		 * Adds a connection visual object to the GUI
		 * 
		 * @param conn (Line2D) - Connection visual object
		 */
		public void addConnection(Line2D conn) {
			graphConnections.add(conn);
		}
}
