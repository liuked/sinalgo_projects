package projects.coloring_d2.nodes.messages;

import projects.coloring_d2.nodes.nodeImplementations.state;
import sinalgo.nodes.Connections;
import sinalgo.nodes.NodeOutgoingConnectionsList;
import sinalgo.nodes.messages.Message;

import java.util.Hashtable;

public class CMessage extends Message {

    public int id;
    public int color;
    public Hashtable<Integer,state> neighborStates;
    public Connections d2OutgoingConnections = new NodeOutgoingConnectionsList(true);

    public CMessage(int id, int color, Hashtable<Integer,state> _neighborStates, Connections _d2OutgoingConnections) {
        this.id=id;
        this.color = color;
        this.neighborStates = _neighborStates;
        d2OutgoingConnections = _d2OutgoingConnections;
    }

    public Message clone() {
        return new CMessage(id,color,neighborStates,d2OutgoingConnections);
    }

}
