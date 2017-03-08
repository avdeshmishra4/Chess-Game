package com.stephengware.java.games.chess.bot;

import java.util.Iterator;

import com.stephengware.java.games.chess.state.Player;
import com.stephengware.java.games.chess.state.State;
import com.stephengware.java.games.mm_game.ai.Utility;

/**
 * A chess bot which selects its next move based on min-max algorithm with alpha beta pruning
 * It looks four moves ahead (2 for each player)
 * 
 * @author Avdesh Mishra
 */
public class ChessBot extends Bot {

	class Node {
		State state;
		double value;
	}


	/**
	 * Chess bot uses min max search with alpha beta pruning and looks four step ahead in future to choose next move
	 */
	public ChessBot() {
		super("amishra2");
	}


	@Override
	protected State chooseMove(State root) {
		int depth = 4;

		Node selectedNode = new Node();
		State nextMove = null;
		if (root.player.equals(Player.WHITE)) {

			selectedNode = findMax(root, Double.NEGATIVE_INFINITY,
					Double.POSITIVE_INFINITY, depth);

			if (selectedNode.state.previous.equals(root)) {

				return nextMove = selectedNode.state;

			} else {
				while (!selectedNode.state.previous.equals(root)) {
					selectedNode.state = selectedNode.state.previous;
					nextMove = selectedNode.state;

				}

				return nextMove;

			}

		} else {

			selectedNode = findMin(root, Double.NEGATIVE_INFINITY,
					Double.POSITIVE_INFINITY, depth);

			if (selectedNode.state.previous.equals(root)) {

				return nextMove = selectedNode.state;

			} else {
				while (!selectedNode.state.previous.equals(root)) {
					selectedNode.state = selectedNode.state.previous;
					nextMove = selectedNode.state;

				}

				return nextMove;

			}
		}

	}

	private Node findMax(State root, double alpha, double beta, int depth) {
		if (root.over || depth <= 0) {
			Node node = new Node();
			node.state = root;
			node.value = Utility.evaluate(node.state);
			return node;
		}
		double max = Double.NEGATIVE_INFINITY;
		Node returned = new Node();
		Node bestNode = new Node();
		Iterable<State> iterate = root.next();
		Iterator<State> itr = iterate.iterator();

		while (itr.hasNext() && !root.searchLimitReached()) {

			Node nextState = new Node();
			nextState.state = itr.next();
			returned = findMin(nextState.state, alpha, beta, depth - 1);
			if (returned.value > max) {

				bestNode = returned;
			}
			max = Math.max(max, returned.value);

			if (max >= beta)
				return bestNode;
			alpha = Math.max(alpha, max);
		}

		if (bestNode.state == null) {
			System.out.println("I am inside null of max");
			bestNode.state = root;
			bestNode.value = 0;
			return bestNode;

		} else {

			return bestNode;
		}
	}

	private Node findMin(State root, double alpha, double beta, int depth) {

		if (root.over || depth <= 0) {
			Node node = new Node();
			node.state = root;
			node.value = Utility.evaluate(node.state);
			return node;
		}

		double min = Double.POSITIVE_INFINITY;
		Node returned = new Node();
		Node bestNode = new Node();
		Iterable<State> iterate = root.next();
		Iterator<State> itr = iterate.iterator();

		while (itr.hasNext() && !root.searchLimitReached()) {

			Node nextState = new Node();
			nextState.state = itr.next();
			returned = findMax(nextState.state, alpha, beta, depth - 1);

			if (returned.value < min) {

				bestNode = returned;

			}
			min = Math.min(min, returned.value);
			if (min <= alpha)
				return bestNode;
			beta = Math.min(beta, min);
		}

		if (bestNode.state == null) {
			System.out.println("I am inside null of min");
			bestNode.state = root;
			bestNode.value = 0;
			return bestNode;

		} else {

			return bestNode;
		}
	}

}
