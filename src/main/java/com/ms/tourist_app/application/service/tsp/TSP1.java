package com.ms.tourist_app.application.service.tsp;

import java.util.ArrayList;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {
    @Override
    protected double bound(Integer currentVertex, ArrayList<Integer> unvisited) {
        return g.getMinCost()*unvisited.size();
    }

    @Override
    protected Iterator<Integer> iterator(Integer currentVertex, ArrayList<Integer> unvisited, Graph g) {
        return new SeqIter(unvisited, currentVertex, g);
    }
}
