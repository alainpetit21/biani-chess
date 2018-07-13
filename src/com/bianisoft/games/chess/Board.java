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

//Bianisoft imports
import com.bianisoft.engine.Context;


public class Board{
	public BoardInit	m_objInit;
	public SprPiece[][]	m_boardShortcut	= new SprPiece[8][8];
	public SprPiece[][]	m_tempBoard		= new SprPiece[8][8];

	public Vector[] m_vecPieces= new Vector[2];

	public static int ConvertScreenX(int p_nPosX)	{return ((p_nPosX+21) + 235) / 43;}
	public static int ConvertScreenY(int p_nPosY)	{return ((p_nPosY-48) - 150) / -48;}

	public Board(){
		m_objInit= BoardInit.createBoardInit(BoardInit.TYPE_STANDARD);
//		m_objInit= BoardInit.createBoardInit(BoardInit.TYPE_CUSTOM);

		m_vecPieces[0]= new Vector<SprPiece>();
		m_vecPieces[1]= new Vector<SprPiece>();
	}

	public boolean isValidCoordinate(int p_nX, int p_nY){
		return !((p_nX < 0) || (p_nX >= 8) || (p_nY < 0) || (p_nY >= 8));
	}

	public boolean isFree(int p_nX, int p_nY){
		if(!isValidCoordinate(p_nX, p_nY))
			return false;

		return m_boardShortcut[p_nX][p_nY] == null;
	}

	public boolean isPieceMatch(int p_nX, int p_nY, int p_nTeam){
		if(!isValidCoordinate(p_nX, p_nY))
			return false;

		SprPiece piece= m_boardShortcut[p_nX][p_nY];
		if((piece != null) && (piece.getTeam() == p_nTeam))
			return true;

		return false;
	}

	public boolean isPieceMatch(int p_nX, int p_nY, int p_nType, int p_nTeam){
		if(!isValidCoordinate(p_nX, p_nY))
			return false;

		SprPiece piece= m_boardShortcut[p_nX][p_nY];
		if((piece != null) && (piece.getTeam() == p_nTeam) && (piece.getType() == p_nType))
			return true;
		
		return false;
	}

	public boolean isPieceProtected(SprPiece p_piece){

		//If piece if Threatetened by my own team, then it is protected!
		return isPieceThreatened(p_piece, 1 - p_piece.getTeam());
	}

	public boolean isPieceThreatened(SprPiece p_piece, int p_nTeam){
		int posX= p_piece.getBoardPosX();
		int posY= p_piece.getBoardPosY();

		if(isPieceMatch(posX-1, posY+((p_nTeam==0)? 1:-1), SprPiece.PIECE_PAWN, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+1, posY+((p_nTeam==0)? 1:-1), SprPiece.PIECE_PAWN, 1-p_nTeam))	return true;

		if(isPieceMatch(posX-1, posY-2, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+1, posY-2, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	return true;
		if(isPieceMatch(posX-2, posY-1, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+2, posY-1, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	return true;
		if(isPieceMatch(posX-1, posY+2, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+1, posY+2, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	return true;
		if(isPieceMatch(posX-2, posY+1, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+2, posY+1, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	return true;

		if(isPieceMatch(posX-1, posY-1, SprPiece.PIECE_KING, 1-p_nTeam))	return true;
		if(isPieceMatch(posX-1, posY+0, SprPiece.PIECE_KING, 1-p_nTeam))	return true;
		if(isPieceMatch(posX-1, posY+1, SprPiece.PIECE_KING, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+0, posY-1, SprPiece.PIECE_KING, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+0, posY+1, SprPiece.PIECE_KING, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+1, posY-1, SprPiece.PIECE_KING, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+1, posY+0, SprPiece.PIECE_KING, 1-p_nTeam))	return true;
		if(isPieceMatch(posX+1, posY+1, SprPiece.PIECE_KING, 1-p_nTeam))	return true;

		//-X ; -Y
		for(int i= 1; i < 8; ++i){
			//Those are offensive
			if(isPieceMatch(posX-i, posY-i, SprPiece.PIECE_BISHOP, 1-p_nTeam))	return true;
			if(isPieceMatch(posX-i, posY-i, SprPiece.PIECE_QUEEN, 1-p_nTeam))	return true;

			//Those will block further attacks
			if(isPieceMatch(posX-i, posY-i, SprPiece.PIECE_PAWN, 1-p_nTeam))	break;
			if(isPieceMatch(posX-i, posY-i, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	break;
			if(isPieceMatch(posX-i, posY-i, SprPiece.PIECE_ROOK, 1-p_nTeam))	break;
			if(isPieceMatch(posX-i, posY-i, SprPiece.PIECE_KING, 1-p_nTeam))	break;

			//Also all my team will block
			if(isPieceMatch(posX-i, posY-i, p_nTeam))							break;
		}

		//0 ; -Y
		for(int i= 1; i < 8; ++i){
			//Those are offensive
			if(isPieceMatch(posX, posY-i, SprPiece.PIECE_ROOK, 1-p_nTeam))		return true;
			if(isPieceMatch(posX, posY-i, SprPiece.PIECE_QUEEN, 1-p_nTeam))		return true;

			//Those will block further attacks
			if(isPieceMatch(posX, posY-i, SprPiece.PIECE_PAWN, 1-p_nTeam))		break;
			if(isPieceMatch(posX, posY-i, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	break;
			if(isPieceMatch(posX, posY-i, SprPiece.PIECE_BISHOP, 1-p_nTeam))	break;
			if(isPieceMatch(posX, posY-i, SprPiece.PIECE_KING, 1-p_nTeam))		break;

			//Also all my team will block
			if(isPieceMatch(posX, posY-i, p_nTeam))								break;
		}

		//+X ; -Y
		for(int i= 1; i < 8; ++i){
			//Those are offensive
			if(isPieceMatch(posX+i, posY-i, SprPiece.PIECE_BISHOP, 1-p_nTeam))	return true;
			if(isPieceMatch(posX+i, posY-i, SprPiece.PIECE_QUEEN, 1-p_nTeam))	return true;

			//Those will block further attacks
			if(isPieceMatch(posX+i, posY-i, SprPiece.PIECE_PAWN, 1-p_nTeam))	break;
			if(isPieceMatch(posX+i, posY-i, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	break;
			if(isPieceMatch(posX+i, posY-i, SprPiece.PIECE_ROOK, 1-p_nTeam))	break;
			if(isPieceMatch(posX+i, posY-i, SprPiece.PIECE_KING, 1-p_nTeam))	break;

			//Also all my team will block
			if(isPieceMatch(posX+i, posY-i, p_nTeam))							break;
		}

		//-X ; 0
		for(int i= 1; i < 8; ++i){
			//Those are offensive
			if(isPieceMatch(posX-i, posY, SprPiece.PIECE_ROOK, 1-p_nTeam))		return true;
			if(isPieceMatch(posX-i, posY, SprPiece.PIECE_QUEEN, 1-p_nTeam))		return true;

			//Those will block further attacks
			if(isPieceMatch(posX-i, posY, SprPiece.PIECE_PAWN, 1-p_nTeam))		break;
			if(isPieceMatch(posX-i, posY, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	break;
			if(isPieceMatch(posX-i, posY, SprPiece.PIECE_BISHOP, 1-p_nTeam))	break;
			if(isPieceMatch(posX-i, posY, SprPiece.PIECE_KING, 1-p_nTeam))		break;

			//Also all my team will block
			if(isPieceMatch(posX-i, posY, p_nTeam))								break;
		}

		//+X ; 0
		for(int i= 1; i < 8; ++i){
			//Those are offensive
			if(isPieceMatch(posX+i, posY, SprPiece.PIECE_ROOK, 1-p_nTeam))		return true;
			if(isPieceMatch(posX+i, posY, SprPiece.PIECE_QUEEN, 1-p_nTeam))		return true;

			//Those will block further attacks
			if(isPieceMatch(posX+i, posY, SprPiece.PIECE_PAWN, 1-p_nTeam))		break;
			if(isPieceMatch(posX+i, posY, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	break;
			if(isPieceMatch(posX+i, posY, SprPiece.PIECE_BISHOP, 1-p_nTeam))	break;
			if(isPieceMatch(posX+i, posY, SprPiece.PIECE_KING, 1-p_nTeam))		break;

			//Also all my team will block
			if(isPieceMatch(posX+i, posY, p_nTeam))								break;
		}

		//-X ; +Y
		for(int i= 1; i < 8; ++i){
			//Those are offensive
			if(isPieceMatch(posX-i, posY+i, SprPiece.PIECE_BISHOP, 1-p_nTeam))	return true;
			if(isPieceMatch(posX-i, posY+i, SprPiece.PIECE_QUEEN, 1-p_nTeam))	return true;

			//Those will block further attacks
			if(isPieceMatch(posX-i, posY+i, SprPiece.PIECE_PAWN, 1-p_nTeam))	break;
			if(isPieceMatch(posX-i, posY+i, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	break;
			if(isPieceMatch(posX-i, posY+i, SprPiece.PIECE_ROOK, 1-p_nTeam))	break;
			if(isPieceMatch(posX-i, posY+i, SprPiece.PIECE_KING, 1-p_nTeam))	break;

			//Also all my team will block
			if(isPieceMatch(posX-i, posY+i, p_nTeam))							break;
		}

		//0 ; +Y
		for(int i= 1; i < 8; ++i){
			//Those are offensive
			if(isPieceMatch(posX, posY+i, SprPiece.PIECE_ROOK, 1-p_nTeam))		return true;
			if(isPieceMatch(posX, posY+i, SprPiece.PIECE_QUEEN, 1-p_nTeam))		return true;

			//Those will block further attacks
			if(isPieceMatch(posX, posY+i, SprPiece.PIECE_PAWN, 1-p_nTeam))		break;
			if(isPieceMatch(posX, posY+i, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	break;
			if(isPieceMatch(posX, posY+i, SprPiece.PIECE_BISHOP, 1-p_nTeam))	break;
			if(isPieceMatch(posX, posY+i, SprPiece.PIECE_KING, 1-p_nTeam))		break;

			//Also all my team will block
			if(isPieceMatch(posX, posY+i, p_nTeam))								break;
		}

		//+X ; +Y
		for(int i= 1; i < 8; ++i){
			//Those are offensive
			if(isPieceMatch(posX+i, posY+i, SprPiece.PIECE_BISHOP, 1-p_nTeam))	return true;
			if(isPieceMatch(posX+i, posY+i, SprPiece.PIECE_QUEEN, 1-p_nTeam))	return true;

			//Those will block further attacks
			if(isPieceMatch(posX+i, posY+i, SprPiece.PIECE_PAWN, 1-p_nTeam))	break;
			if(isPieceMatch(posX+i, posY+i, SprPiece.PIECE_KNIGHT, 1-p_nTeam))	break;
			if(isPieceMatch(posX+i, posY+i, SprPiece.PIECE_ROOK, 1-p_nTeam))	break;
			if(isPieceMatch(posX+i, posY+i, SprPiece.PIECE_KING, 1-p_nTeam))	break;

			//Also all my team will block
			if(isPieceMatch(posX+i, posY+i, p_nTeam))							break;
		}

		return false;

	}

	public boolean isInCheck(int p_nTeam){
		SprPiece pieceKing= null;
		for(int i= 0; i < 8; ++i)
			for(int j= 0; j < 8; ++j)
				if((m_boardShortcut[i][j] != null) && 
				(m_boardShortcut[i][j].getType() == SprPiece.PIECE_KING) &&
				(m_boardShortcut[i][j].getTeam() == p_nTeam))
					pieceKing= m_boardShortcut[i][j];

		return isPieceThreatened(pieceKing, pieceKing.getTeam());
	}


	public SprPiece getAt(int p_nX, int p_nY){
		if(!isValidCoordinate(p_nX, p_nY))
			return null;

		return m_boardShortcut[p_nX][p_nY];
	}

	public void setAt(SprPiece p_piece, int p_nX, int p_nY){
		if(!isValidCoordinate(p_nX, p_nY))
			return;

		m_boardShortcut[p_nX][p_nY]= p_piece;
	}

	public void doMove(Move p_objMove){
		m_boardShortcut[p_objMove.m_nFromX][p_objMove.m_nFromY]= null;
		m_boardShortcut[p_objMove.m_nToX][p_objMove.m_nToY]= p_objMove.m_sprSubject;
	}

	public void undoMove(Move p_objMove){
		m_boardShortcut[p_objMove.m_nFromX][p_objMove.m_nFromY]= p_objMove.m_sprSubject;
		m_boardShortcut[p_objMove.m_nToX][p_objMove.m_nToY]= null;
	}

	public void load(Context p_ctxUnder, int[] p_TeamColor){
		SprPiece sprTemplate= (SprPiece)p_ctxUnder.findByTextID("Spr_PieceTemplate");

		for(int i= 0; i < m_objInit.getMaxEntries(); ++i){
			int posX= m_objInit.getPosXAt(i);
			int posY= m_objInit.getPosYAt(i);
			int type= m_objInit.getPieceTypeAt(i);
			int team= m_objInit.getTeamAt(i);

			SprPiece piece= new SprPiece(sprTemplate);

			m_vecPieces[team].add(piece);
			m_boardShortcut[posX][posY]= piece;
			m_boardShortcut[posX][posY].setBoardPos(posX, posY);
			m_boardShortcut[posX][posY].setCurState(p_TeamColor[team]);
			m_boardShortcut[posX][posY].setTeam(team);
			m_boardShortcut[posX][posY].setType(type);
			m_boardShortcut[posX][posY].setTextID("Spr_PieceTeam" + team + "_Type" + type + "_PosX" + posX + "_PosY" + posY);

			p_ctxUnder.addChild(m_boardShortcut[posX][posY]);
		}
	}
}
