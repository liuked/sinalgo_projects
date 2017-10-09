package projects.clustering.nodes.timers;

import projects.clustering.nodes.nodeImplementations.CNode;
import projects.clustering.nodes.messages.*;
import sinalgo.nodes.timers.Timer;


public class CTimer extends Timer {
    CNode sender;
    int interval;
    public CTimer(CNode sender, int interval) {
        this.sender = sender;
        this.interval = interval;
    }
    // called upon timer expiration
    public void fire() {
// create message with color
        CMessage msg= new CMessage(sender.ID,sender.getColorInt());
        sender.broadcast(msg); // send to all neighbors
        this.startRelative(interval, node);
// recursive restart of the timer
    }
}