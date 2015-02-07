package com.hiilimonoksidi.tiralabra.application;

import com.hiilimonoksidi.tiralabra.graph.Node;
import com.hiilimonoksidi.tiralabra.graph.Path;

public interface StepController {

    public void setOpenNodes(Iterable<Node> open);
    
    public void setClosedNodes(Iterable<Node> closed);
    
    public void setPath(Path path);
    
    public int getDelay();
}