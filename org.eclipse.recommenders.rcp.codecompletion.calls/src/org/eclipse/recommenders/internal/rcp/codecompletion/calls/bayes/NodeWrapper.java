/**
 * Copyright (c) 2010 Darmstadt University of Technology.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Johannes Lerch - initial API and implementation.
 */
package org.eclipse.recommenders.internal.rcp.codecompletion.calls.bayes;

import java.util.HashMap;

import org.eclipse.recommenders.commons.bayesnet.Node;

import smile.Network;

public class NodeWrapper {

    private final Node node;
    private final Network smileNetwork;
    private int nodeHandle;
    private HashMap<String, Integer> stateMapping;

    public NodeWrapper(final Node node, final Network smileNetwork) {
        this.node = node;
        this.smileNetwork = smileNetwork;
        initialize();
    }

    private void initialize() {
        nodeHandle = smileNetwork.addNode(Network.NodeType.Cpt);

        final String[] states = node.getStates();
        stateMapping = new HashMap<String, Integer>();
        for (int i = 0; i < states.length; i++) {
            stateMapping.put(states[i], i);
            if (i > 1) {
                smileNetwork.addOutcome(nodeHandle, "state_" + String.valueOf(i));
            }
        }
    }

    public int getHandle() {
        return nodeHandle;
    }

    public void observeState(final String state) {
        smileNetwork.setEvidence(nodeHandle, stateMapping.get(state));
    }

    public boolean isEvidence() {
        return smileNetwork.isEvidence(nodeHandle);
    }

    public int getStateIndex(final String state) {
        return stateMapping.get(state);
    }

    public double[] getProbability() {
        return smileNetwork.getNodeValue(nodeHandle);
    }

}
