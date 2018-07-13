/* This file is part of the Chess test AI algorithm.
 *
 * This game is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *----------------------------------------------------------------------
 * Copyright (C) Alain Petit - alainpetit21@hotmail.com
 *
 * 01/03/11			0.1 First beta initial Version.
 * 12/09/11			0.1.1 Moved everything to a com.bianisoft and GPL
 *
 *-----------------------------------------------------------------------
 */
package com.bianisoft.games.chess;


//Standard Java imports
import java.util.Vector;


public class BoardInit{
	public final static int TYPE_STANDARD	= 0;
	public final static int TYPE_CUSTOM		= 1;

	public static BoardInit createBoardInit(int p_nType){
		if(p_nType == TYPE_STANDARD)		return new BoardInit_Standard();
		if(p_nType == TYPE_CUSTOM)			return new BoardInit_Custom();

		return new BoardInit();
	}


	private Vector<BoardInitEntry>	m_arEntry= new Vector<BoardInitEntry>();

	class BoardInitEntry{
		public int m_nPosX;
		public int m_nPosY;
		public int m_nTypePiece;
		public int m_nTeam;

		public BoardInitEntry(int p_nPosX, int p_nPosY, int p_nTypePiece, int p_nTeam){
			m_nPosX= p_nPosX;
			m_nPosY= p_nPosY;
			m_nTypePiece= p_nTypePiece;
			m_nTeam= p_nTeam;
		}
	}


	public void addEntry(int p_nPosX, int p_nPosY, int p_nTypePiece, int p_nTeam){
		m_arEntry.addElement(new BoardInitEntry(p_nPosX, p_nPosY, p_nTypePiece, p_nTeam));
	}

	public int getMaxEntries()	{return m_arEntry.size();}
	public int getPosXAt(int p_nIndex)	{return m_arEntry.get(p_nIndex).m_nPosX;}
	public int getPosYAt(int p_nIndex)	{return m_arEntry.get(p_nIndex).m_nPosY;}
	public int getTeamAt(int p_nIndex)	{return m_arEntry.get(p_nIndex).m_nTeam;}
	public int getPieceTypeAt(int p_nIndex)	{return m_arEntry.get(p_nIndex).m_nTypePiece;}
}

class BoardInit_Standard extends BoardInit{
	public BoardInit_Standard(){
		addEntry(0, 0, SprPiece.PIECE_ROOK, 0);
		addEntry(1, 0, SprPiece.PIECE_KNIGHT, 0);
		addEntry(2, 0, SprPiece.PIECE_BISHOP, 0);
		addEntry(3, 0, SprPiece.PIECE_QUEEN, 0);
		addEntry(4, 0, SprPiece.PIECE_KING, 0);
		addEntry(5, 0, SprPiece.PIECE_BISHOP, 0);
		addEntry(6, 0, SprPiece.PIECE_KNIGHT, 0);
		addEntry(7, 0, SprPiece.PIECE_ROOK, 0);
		addEntry(0, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(1, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(2, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(3, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(4, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(5, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(6, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(7, 1, SprPiece.PIECE_PAWN, 0);

		addEntry(0, 7, SprPiece.PIECE_ROOK, 1);
		addEntry(1, 7, SprPiece.PIECE_KNIGHT, 1);
		addEntry(2, 7, SprPiece.PIECE_BISHOP, 1);
		addEntry(3, 7, SprPiece.PIECE_QUEEN, 1);
		addEntry(4, 7, SprPiece.PIECE_KING, 1);
		addEntry(5, 7, SprPiece.PIECE_BISHOP, 1);
		addEntry(6, 7, SprPiece.PIECE_KNIGHT, 1);
		addEntry(7, 7, SprPiece.PIECE_ROOK, 1);
		addEntry(0, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(1, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(2, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(3, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(4, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(5, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(6, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(7, 6, SprPiece.PIECE_PAWN, 1);
	}
}

class BoardInit_Custom extends BoardInit{
	public BoardInit_Custom(){
		addEntry(0, 0, SprPiece.PIECE_ROOK, 0);
		addEntry(2, 3, SprPiece.PIECE_KNIGHT, 0);
		addEntry(2, 0, SprPiece.PIECE_BISHOP, 0);
		addEntry(0, 6, SprPiece.PIECE_QUEEN, 0);
		addEntry(4, 0, SprPiece.PIECE_KING, 0);
		addEntry(5, 0, SprPiece.PIECE_BISHOP, 0);
		addEntry(7, 0, SprPiece.PIECE_ROOK, 0);
		addEntry(0, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(1, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(2, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(4, 2, SprPiece.PIECE_PAWN, 0);
		addEntry(5, 2, SprPiece.PIECE_PAWN, 0);
		addEntry(6, 1, SprPiece.PIECE_PAWN, 0);
		addEntry(7, 1, SprPiece.PIECE_PAWN, 0);

		addEntry(2, 7, SprPiece.PIECE_BISHOP, 1);
		addEntry(3, 7, SprPiece.PIECE_QUEEN, 1);
		addEntry(4, 7, SprPiece.PIECE_KING, 1);
		addEntry(3, 4, SprPiece.PIECE_BISHOP, 1);
		addEntry(5, 5, SprPiece.PIECE_KNIGHT, 1);
		addEntry(6, 7, SprPiece.PIECE_ROOK, 1);
		addEntry(1, 5, SprPiece.PIECE_PAWN, 1);
		addEntry(2, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(3, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(5, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(6, 6, SprPiece.PIECE_PAWN, 1);
		addEntry(7, 6, SprPiece.PIECE_PAWN, 1);
	}
}
