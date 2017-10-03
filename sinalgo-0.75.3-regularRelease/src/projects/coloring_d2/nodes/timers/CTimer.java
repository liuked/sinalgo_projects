package projects.coloring_d2.nodes.timers;

import projects.coloring.nodes.nodeImplementations.CNode;
import projects.coloring_d2.nodes.nodeImplementations.CNodeD2;
import projects.coloring_d2.nodes.messages.*;
import sinalgo.nodes.timers.Timer;


public class CTimer extends Timer {

    public CNodeD2 sender;
    public int interval;

    public CTimer(CNodeD2 sender, int interval) {
        this.sender = sender;
        this.interval = interval;
    }
    // called upon timer expiration
    public void fire() {
// create message with color and neighbour state
        CMessage msg= new CMessage(sender.ID,sender.getColorInt(),sender.getNeighborStates(), sender.outgoingConnections);
        sender.broadcast(msg); // send to all neighbors
        this.startRelative(interval, node);

// recursive restart of the timer
    }
}