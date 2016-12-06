# project-teamgetterdone
project-teamgetterdone created by GitHub Classroom

SYSC 3110 Fall 2016
Professor Babak Esfandiari

<B>Group Members </B>

Ben Palko 100964652

Dan Mihailescu	100972565

Josh Seguin	100964276

Thomas Carriere 100947281

<B>Usage</B>

Running the JAR will launch the user interface which will allow the user to create the topology of the network and run simulations on it.

To create new nodes, right click on the gui and select "Create Nodes" from the list of options to launch the create node prompt. Enter the desired node names into the textfield, seperated by commas, and press "OK".
It will not allow you to create duplicate nodes (nodes with the exact same name). The node will then be displayed on the graphics pane of the gui. They nodes are evenly spaced in a skewed grid pattern.

To setup the connections of a node, right click on the gui and select "Add connection" from the list of options to launch the set connection prompt. Enter the name of the first node to add a connection to in the textfield, then press "OK", which will launch a second prompt. Enter the name of the second node to add a connection to in the textfield, then press "OK" to establish the connection.
Note: when connections are established, they are created in a way to keep the graph simple and undirected.
That is, if node A is connected to B then B will be connected to A. Also, node A cannot connect to itself, a node have multiple connections to the same node. A line is drawn between nodes where connections exist.
If the connection list does not adhere to these criteria, the duplicate and invalid connections will be ignored.
ex.
For nodes A, B, C
If node A already references B 
And the input is "A B C C"
The resulting connection (for node A) will be "B C"

To delete a node, right click the on the gui and select "Delete Node" from the list of options to launch the delete node prompt. Enter the name of the node to delete in the textfield and press "OK" to delete the node.

To delete a connection between two nodes, right click the gui and select "Delete Connection" from the list of options to launch the delete connection prompt. Enter the name of a node with connections to delete in the textfield and press "OK" to launch the second prompt. Enter the name of a node connected to the first node in the textfield and press "OK" to delete the connection between the two given nodes.

To select a routing algorithm for the simulation to implement, click on File from the menubar and press "Select Algorithm" to launch the select algorithm prompt. From the prompt, select the desrired Algorithm from the drop down list and press "OK" to set the algorithm to simulate.

To begin the simulation, press the button labeled "Start Simulation". If an algorithm wasn't selected before hand from the File Menu, a prompt will ask the user to select the desired algorithm from a drop down list. Once selected, press "OK" to launch the Number of Steps prompt. Enter the number of steps for the simulation to run, and press "OK". If it is the first time running the simulation, the user will be prompted to enter a Send Rate (the rate at which never packets are introduced into the simulation); otherwise, the simulation will begin. If prompted, enter the desired Send Rate and press "OK" to launch the simulation.
Note: The number of steps and the send rate must both be positive integers. 
      The Send Rate dicates how many steps must be performed before a new packet is created.

Pressing the button labeled "Simulate Step" will perform one step (i.e. each existing packet will be transfered once) into the simulation. The user will be prompted for a sendRate and algorhithm if none are already set.

Pressing the button labeled "Step Back" will perform one step backwards through the simulation (reversing the steps already performing).

A textArea on the right of the interface is used to display the metrics from the simulation. The textArea is updated after each step is completed, displaying the total number of packets transfered between nodes (i.e. a packet moving between one node and a connection).

A Model-View-Controller design was implemented in order to keep the classes seperate and uncoupled.

There are four algorhithms to select.
Random: packets are transmitted randomly throughout the simulation, until they reach their destination.
Breadth-First-Search: uses a BFS technique to determine the shortest path for a packet to take to its destination.
Depth-First-Search: uses a DFS technique to determine a path to the destination.
Flooding: floods the network with the packet by sending it to all connections of a node, and all their connections.

<B>Changes made since last milestone</B>

Added functionality to step back through simulation.
Fixed the Flooding algorithm.
Fixed the test that was failing.
Fixed the Depth-First-Search algorhithm.
Both metrics are now displayed.
All tests pass.

<B>Known Issues </B>
Simulation requires a connected graph to function properly.
Deleting a node or connection after simulating can cause errors.
Metrics display invalid value while Stepping Back through simulation. Once the simulalation returns to the point where it was initial Stepped Backwards, the metrics become valid again, as they aren't updated while stepping back and forth through the history of the simulation. The output displays the validity of the metrics.
If the XML file "ImportExport.xml" is invalid or doesn't exist, exceptions will thrown.

<B>Distribution of Work</B>

Ben - Thoroughly updated MVC, View and Controller

Dan - Created the UML diagram,worked on UniTTests, JAVADOC, ExportingXML

Josh - Worked on the Depth algorhithm, Model, ImportingXML, StepBack

Thomas - Worked on the Model, StepBack, Documentation, Flooding algorithm and Metrics.

