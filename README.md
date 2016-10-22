# project-teamgetterdone
project-teamgetterdone created by GitHub Classroom

SYSC 3110 Fall 2016
Professor Babak Esfandiari

<B>Group Members </B>

Ben Palko 100964652
Dan Mihailescu	100972565
Josh Seguin	100964276
Thomas Carriere 

<B>Usage</B>

Running the JAR will launch our Network Simulator window.
In the menubar - under File, there are three options:
Display Nodes and Connections - Provides a list of all created nodes and their connections in the display area.
Setup Test Nodes - Creates the nodes and connections based on the image in the project.pdf file.
Reset - Clears all nodes, simulation must be stopped before this can be done.

To create a new node, enter the node's name in the textfield next to "New node name:", then press the Create Node button.
It will not allow you to create duplicate nodes (nodes with the exact same name).

To setup the connections of a mode, enter the name of the node next to the textfield next to "Node to set connections",
then enter a space separated list of the nodes in the textfield next to "List of connections:", then click the Establish Connections button.
Note: when connections are established, they are created in a way to keep the graph simple and undirected.
That is, if node A is connected to B then B will be connected to A. Also, node A cannot connect to itself, a node have multiple connections to the same node.
If the connection list does not adhere to these criteria, the duplicate and invalid connections will be ignored.
ex.
For nodes A, B, C, D
If node A already references B and C
And the input is "A C D D"
The resulting connection (for node A) will be "B C D"

Pressing the Start Simulation button will start the simulation, which takes a random node from the created nodes as the source and
another random node as the destination, and applies the random routing algorithm, outputting each step to the display area with 1s intervals.

Pressing the Stop Simulation button will cause the simulation to stop once it's finish it's current routing. After the simulation is stopped it will
display the average hops to the display area.

<B>Changes made since last milestone</B>

N/A

<B>Known Issues </B>

Cannot specify sendrate.
Cannot have multiple transfers going through simulation simultaneously.
GUI code is "smelly" in certain places.
No way to force stop a simulation if it gets in an infinite loop.
