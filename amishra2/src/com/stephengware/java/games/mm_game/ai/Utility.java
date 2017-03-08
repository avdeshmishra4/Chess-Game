package com.stephengware.java.games.mm_game.ai;


import com.stephengware.java.games.chess.state.Bishop;
import com.stephengware.java.games.chess.state.King;
import com.stephengware.java.games.chess.state.Knight;
import com.stephengware.java.games.chess.state.Pawn;
import com.stephengware.java.games.chess.state.Piece;
import com.stephengware.java.games.chess.state.Player;
import com.stephengware.java.games.chess.state.Queen;
import com.stephengware.java.games.chess.state.Rook;
import com.stephengware.java.games.chess.state.State;
/*
 * Utility class to evaluate the node's value
 */
public class Utility {

	/*
	 * computes the material score of the given state
	 * input:- state or the node
	 * output:- material score corresponding to given state or the node
	 */
	public static double evaluate(State root) {
		double WhiteValue = 0.0;
		double BlackValue = 0.0;
		double returnValue = 0.0;

		if (root.check) {
			if (root.player.equals(Player.WHITE)) {

				return -10000;
			} else if (root.player.equals(Player.BLACK)) {

				return 10000;
			}

		}

		for (Piece piece : root.board) {

			if (piece.player.equals(Player.WHITE) && (piece instanceof Queen)) {

				WhiteValue += 8.8;
				if (root.player == piece.player) {

					WhiteValue += 0.2;
				}

			} else if (piece.player.equals(Player.BLACK)
					&& (piece instanceof Queen)) {

				BlackValue += 8.8;
				if (root.player == piece.player) {

					WhiteValue += 0.2;
				}

			} else if (piece.player.equals(Player.WHITE)
					&& (piece instanceof Bishop)) {

				WhiteValue += 3.33 + ((piece.rank) * 0.02);

			} else if (piece.player.equals(Player.BLACK)
					&& (piece instanceof Bishop)) {

				BlackValue += 3.33 + ((7 - piece.rank) * 0.02);

			} else if (piece.player.equals(Player.WHITE)
					&& (piece instanceof Knight)) {

				WhiteValue += 3.2 + ((piece.rank) * 0.03);
				// calculate the manhattan distance and inverse it to get closeness from the opponents king
				int row = piece.rank;
				int col = piece.file;
				int oppKingRow = root.board.getKing(root.player.other()).rank;
				int oppKingCol = root.board.getKing(root.player.other()).file;
				double manHatDist = Math.abs(oppKingRow - row)
						+ Math.abs(oppKingCol - col);
				double closeness = 1 / manHatDist;

				WhiteValue += closeness;

			} else if (piece.player.equals(Player.BLACK)
					&& (piece instanceof Knight)) {

				BlackValue += 3.2 + ((7 - piece.rank) * 0.03);
			//	calculate the manhattan distance and inverse it to get closeness from the opponents king
				int row = piece.rank;
				int col = piece.file;
				int oppKingRow = root.board.getKing(root.player.other()).rank;
				int oppKingCol = root.board.getKing(root.player.other()).file;
				double manHatDist = Math.abs(oppKingRow - row)
						+ Math.abs(oppKingCol - col);
				double closeness = 1 / manHatDist;

				WhiteValue += closeness;

			} else if (piece.player.equals(Player.WHITE)
					&& (piece instanceof Rook)) {
				
				WhiteValue += 5.1;

			} else if (piece.player.equals(Player.BLACK)
					&& (piece instanceof Rook)) {

				BlackValue += 5.1;

			} else if (piece.player.equals(Player.WHITE)
					&& (piece instanceof Pawn)) {

				WhiteValue += 1 + ((piece.rank) * 0.07);

			} else if (piece.player.equals(Player.BLACK)
					&& (piece instanceof Pawn)) {

				BlackValue += 1 + ((6 - piece.rank) * 0.07);

			}

		}

		/*
		 * to overcome stalemate check if root.over is true and if it is true return the opposite value (BlackValue-WhiteValue)
		 */
		if (root.over) {

			returnValue = BlackValue - WhiteValue;

		} else {

			returnValue = WhiteValue - BlackValue;

		}

		return returnValue;

	}

}
