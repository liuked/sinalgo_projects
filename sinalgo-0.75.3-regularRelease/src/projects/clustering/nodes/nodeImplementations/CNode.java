package projects.clustering.nodes.nodeImplementations;
        import java.awt.Color;
        import java.awt.Graphics;
        import java.util.*;
        import sinalgo.configuration.WrongConfigurationException;
        import sinalgo.gui.transformation.PositionTransformation;
        import sinalgo.nodes.Node;
        import sinalgo.nodes.edges.Edge;
        import sinalgo.nodes.messages.Inbox;
        import projects.clustering.nodes.timers.*;
        import projects.clustering.nodes.messages.*;
        import sinalgo.nodes.messages.Message;
class state
{
    int color;
    state(int color){
        this.color=color;
    }
}

public class CNode extends Node {
    private int color;
    private final int nb = 4;      // number of colors
    private final Color tab[] =
            {Color.BLUE,Color.CYAN,Color.GREEN,Color.DARK_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.RED,Color.WHITE,Color.YELLOW};
    private Hashtable<Integer,state> neighborStates;
    public int getColorInt(){
        return color;
    }
    public Color RGBColor(){
        return tab[getColorInt()];
    }
    public void setColor(int c) {
        this.color=c;
    }
    public void initColor(int range){
        setColor((int) (Math.random() * range) % range);
    }
    public void compute(){
        boolean same=false;
        Iterator<Edge> it=this.outgoingConnections.iterator();
        boolean SC[]=new boolean[nb];
        for (int i=0;i<SC.length;i++)
            SC[i]=false;
        while(it.hasNext()){
            Edge e=it.next();
            state tmp=neighborStates.get(e.endNode.ID); // get state of the pointed neighbor from  neighbor hash  table
            if(tmp!=null){
                if(tmp.color==this.getColorInt()){
                    same=true;
                }
                SC[tmp.color]=true;
            }

        }
        if (same){
            int dispo=0;
            for (int i=0;i<SC.length;i++)
                if(SC[i]==false) dispo++;
            if (dispo == 0) return;
            int choix= ((int) (Math.random() * 10000)) % dispo + 1; // chose a random number between 1 and number of available colors
            int i=0;
            // practically i chose the first available color after skipping choix unavailable colors
            while(choix > 0){
                if(SC[i]==false)
                    choix--;
                if(choix>0) i++;
            }
            this.setColor(i);
        }
    }

    public void handleMessages(Inbox inbox) {
        if(inbox.hasNext()==false) return;
        while(inbox.hasNext()){
            Message msg=inbox.next();
            if(msg instanceof CMessage){
                state tmp=new state(((CMessage) msg).color);
                neighborStates.put(((CMessage) msg).id,tmp);
                compute();
            }
        }
    }
    public void preStep() {}
    public void init() {
        initColor(nb);
        (new CTimer(this,50)).startRelative(50,this);
        this.neighborStates = new Hashtable< Integer, state >
                ( this.outgoingConnections.size() );
    }
    public void neighborhoodChange() {}
    public void postStep() {}
    public String toString() {
        String s = "Node(" + this.ID + ") [";
        Iterator<Edge> edgeIter = this.outgoingConnections.iterator();
        while(edgeIter.hasNext()){
            Edge e = edgeIter.next();
            Node n = e.endNode;
            s+=n.ID+" ";
        }
        return s + "]";
    }
    public void checkRequirements() throws WrongConfigurationException {}
    public void draw(Graphics g,PositionTransformation pt,boolean highlight) {
        Color c;
        this.setColor(this.RGBColor());
        String text = ""+this.ID;
        c=Color.BLACK;
        super.drawNodeAsDiskWithText(g, pt, highlight, text, 20, c);
    }
}