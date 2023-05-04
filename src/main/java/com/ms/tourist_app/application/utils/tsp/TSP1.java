package com.ms.tourist_app.application.utils.tsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TSP1 extends TemplateTSP {
    @Override
    protected double bound(Integer currentVertex, List<Integer> unvisited) {
        return g.getMinCost()*unvisited.size();
    }

    @Override
    protected Iterator<Integer> iterator(Integer currentVertex, List<Integer> unvisited, Graph g) {
        return new SeqIter(unvisited, currentVertex, g);
    }
}
