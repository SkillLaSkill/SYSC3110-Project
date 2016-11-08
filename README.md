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

To create a new node, enter the node's name in the textfield next to "New node name:", then press the Create Node button.
It will not allow you to create duplicate nodes (nodes with the exact same name). The node will then be displayed on the graphics pane to the right. They nodes are evenly spaced in a grid pattern.

To setup the connections of a mode, enter the name of the node next to the textfield next to "Node to set connections",
then enter a the name of a node in the textfield next to "List of connections:", then click the Establish Connections button.
Note: when connections are established, they are created in a way to keep the graph simple and undirected.
That is, if node A is connected to B then B will be connected to A. Also, node A cannot connect to itself, a node have multiple connections to the same node. A line is drawn between nodes where connections exist.
If the connection list does not adhere to these criteria, the duplicate and invalid connections will be ignored.
ex.
For nodes A, B, C
If node A already references B 
And the input is "A B C C"
The resulting connection (for node A) will be "B C"

To delete a node, enter the name of the node to be deleted in the textfield next to "Node to delete" and press the button labeled "Delete Node". If a node is deleted, all of its connections to other nodes will also be removed.

To delete a connection between two nodes, enter the name of the two nodes in the textfield next to "Delete Connection" and press the button labeled "Delete Connection".

To select a routing algorithm for the simulation to implement, open the drop down menu and select from the available algorithm (only random at the moment).

To begin the simulation, enter the desired number steps to be performed into the textfield labeled "Steps" and enter the rate at which new packets should be generated into the text field labeled "Send Rate". Pressing the button labeled "Simulate" run a simulation using the network created by the user, using the specified send rate and number of steps. If there are no more packets in the simulation, and there are still steps that need to be performed, a new packet will be generated, regardless of send rate.

Pressing the button labeled "Simulate Step" will performed one step (i.e. each existing packet will be transfered once) into the simulation. The send rate used is

A textArea in the bottom right of the interface is used to display the metrics from the simulation. The textArea is updated after each step is completed, displaying the total number of packets transfered between nodes (i.e. a packet moving between one node and a connection).

A Model-View-Controller design was implemented in order to keep the classes seperate and uncoupled.

<B>Changes made since last milestone</B>

The simulation now accomodates transmitting multiple packets simultaneously. The send rate and number of steps to be performed can now be set by the user. The simulation can now be stepped into. The source of a packet can no longer also be its destination. Total number of packets (hops between nodes) is now used as a metric to compare algorhythms.
The Master class was removed, and a Model-View-Controller design was implemented. 
The Node class was decoupled into graph, which is used to store connections and nodes.
Unit tests were created for classes Graph, Transfer, Node and Simulation.
Added drop down menu to select the routing algorithm

<B>Known Issues </B>

<B>Distribution of Work</B>
Ben - Updated the UML diagram and worked on the Controller and Graph
Dan - Created the UML diagram and worked on the UnitTests
Josh - Worked on the View and decoupling Node into Graph
Thomas - Worked on the Model, Controller, Transfer and Master(changes were incorperated into Controller)

