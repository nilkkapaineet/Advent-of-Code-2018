package com.company;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int order;
    private int numberOfChildNodes;
    private int numberOfMetadataEntries;
    private List<Node> children;
    private List<Integer> metadata;

    public Node() {
    }
    public Node(int o) {
        this.order = o;
        children = new ArrayList<>();
        metadata = new ArrayList<>();
    }

    public int getOrder() {
        return this.order;
    }
    public void setOrder(int o) {
        this.order = o;
    }
    public int getNumberOfChildNodes() {
        return this.numberOfChildNodes;
    }

    public void setNumberOfChildNodes(int numberOfChildNodes) {
        this.numberOfChildNodes = numberOfChildNodes;
    }

    public int getNumberOfMetadataEntries() {
        return numberOfMetadataEntries;
    }

    public void setNumberOfMetadataEntries(int numberOfMetadataEntries) {
        this.numberOfMetadataEntries = numberOfMetadataEntries;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChildren(Node child) {
        this.children.add(child);
    }
    public List<Integer> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<Integer> metadata) {
        this.metadata = metadata;
    }
    public void addMetadata(int m) {
        this.metadata.add(m);
    }
}
