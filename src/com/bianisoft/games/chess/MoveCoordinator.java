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


public class MoveCoordinator{
	public SprPiece		m_sprPieceInHand;
	public SprSelector	m_sprSelector;
	public Board		m_objBoard;
	public boolean		m_isCheckRulesDone;


	public MoveCoordinator(Board p_objBoard){
		m_objBoard= p_objBoard;
	}

	public boolean isInCheck(int p_nTeam){
		return m_objBoard.isInCheck(p_nTeam);
	}

	public boolean isInStalemate(int p_nTeam){
		preCheckRulesMove();

		for(int i= 0; i < 8; ++i){
			for(int j= 0; j < 8; ++j){
				SprPiece piece= m_objBoard.getAt(i, j);

				if((piece != null) && (piece.getTeam() == p_nTeam)){
					m_sprPieceInHand= piece;
					preCheckRulesMove();
					checkRulesMove();

					if(!m_sprSelector.isClear())
						return false;
				}
			}
		}

		return true;
	}

	public boolean willBeInCheck(SprPiece p_piece, int p_nX, int p_nY){
		boolean willBeInCheck= false;

		if((p_nX < 0) || (p_nX >= 8) || (p_nY < 0) || (p_nY >= 8))
				return false;


		Move objMove= doMove(p_piece, p_nX, p_nY);

		willBeInCheck= m_objBoard.isInCheck(p_piece.getTeam());

		undoMove(objMove);

		return willBeInCheck;
	}

	public boolean isMoveOk(int p_nX, int p_nY){
		return m_sprSelector.isActive(p_nX, p_nY);
	}

	public void undoMove(Move p_objMove){
		p_objMove.m_sprSubject.undoMove(p_objMove);
		m_objBoard.undoMove(p_objMove);

		if(p_objMove.m_wasEnPassant){
			p_objMove.m_sprAttacked.show();
			m_objBoard.setAt(p_objMove.m_sprAttacked, p_objMove.m_sprAttacked.getBoardPosX(), p_objMove.m_sprAttacked.getBoardPosY());
			p_objMove.m_sprAttacked.setBoardPos(p_objMove.m_sprAttacked.getBoardPosX(), p_objMove.m_sprAttacked.getBoardPosY());
		}else if(p_objMove.m_wasAttacking){
			p_objMove.m_sprAttacked.show();
			m_objBoard.setAt(p_objMove.m_sprAttacked, p_objMove.m_nToX, p_objMove.m_nToY);
			p_objMove.m_sprAttacked.setBoardPos(p_objMove.m_nToX, p_objMove.m_nToY);
		}else if(p_objMove.m_wasQueensideCastling){
			SprPiece pieceRook= m_objBoard.getAt(3, p_objMove.m_sprSubject.getTeam() * 7);
			pieceRook.setBoardPos(0, p_objMove.m_sprSubject.getTeam() * 7);
			m_objBoard.setAt(null, 3,  p_objMove.m_sprSubject.getTeam() * 7);
			m_objBoard.setAt(pieceRook, 0, p_objMove.m_sprSubject.getTeam() * 7);
		}else if(p_objMove.m_wasKingsideCastling){
			SprPiece pieceRook= m_objBoard.getAt(5, p_objMove.m_sprSubject.getTeam() * 7);
			pieceRook.setBoardPos(7, p_objMove.m_sprSubject.getTeam() * 7);
			m_objBoard.setAt(null, 5,  p_objMove.m_sprSubject.getTeam() * 7);
			m_objBoard.setAt(pieceRook, 7, p_objMove.m_sprSubject.getTeam() * 7);
		}
	}

	public void moveKingTo(Move p_objMove){
		int deltaX= p_objMove.m_nToX - m_sprPieceInHand.getBoardPosX();

		if(deltaX == -2){
			//Queenside
			SprPiece pieceRook= m_objBoard.getAt(0, m_sprPieceInHand.getTeam() * 7);
			pieceRook.setBoardPos(3,  m_sprPieceInHand.getTeam() * 7);
			p_objMove.m_wasQueensideCastling= true;

			m_objBoard.setAt(null, 0,  m_sprPieceInHand.getTeam() * 7);
			m_objBoard.setAt(pieceRook, 3,  m_sprPieceInHand.getTeam() * 7);
		}else if(deltaX == 2){
			//Kingside
			SprPiece pieceRook= m_objBoard.getAt(7, m_sprPieceInHand.getTeam() * 7);
			pieceRook.setBoardPos(5,  m_sprPieceInHand.getTeam() * 7);
			p_objMove.m_wasKingsideCastling= true;

			m_objBoard.setAt(null, 7,  m_sprPieceInHand.getTeam() * 7);
			m_objBoard.setAt(pieceRook, 5,  m_sprPieceInHand.getTeam() * 7);
		}
	}

	public void movePawnTo(Move p_objMove){
		int deltaY= p_objMove.m_nToY - m_sprPieceInHand.getBoardPosY();
		int nDirection= -1;

		if(m_sprPieceInHand.getTeam() == 1)
			nDirection*= -1;

		if(Math.abs(deltaY) == 2)
			p_objMove.m_wasMovingPawnInitial2Square= true;

		//Attack En Passant
		if(p_objMove.m_sprAttacked == null){
			SprPiece pieceEnPassant= m_objBoard.getAt(p_objMove.m_nToX, p_objMove.m_nToY + nDirection);

			if((pieceEnPassant != null) && (pieceEnPassant.getTeam() != m_sprPieceInHand.getTeam()) && pieceEnPassant.m_hasJustMove2Square){
				p_objMove.m_sprAttacked= pieceEnPassant;
				p_objMove.m_wasAttacking= true;
				p_objMove.m_wasEnPassant= true;
				p_objMove.m_wasMoving= false;

				pieceEnPassant.hide();
				m_objBoard.setAt(null, p_objMove.m_nToX, p_objMove.m_nToY + nDirection);
			}
		}
	}

	public Move doMove(SprPiece p_piece, int p_nX, int p_nY){
		Move objMove= new Move();

		objMove.m_sprSubject= p_piece;
		objMove.m_nFromX= p_piece.getBoardPosX();
		objMove.m_nFromY= p_piece.getBoardPosY();
		objMove.m_nToX= p_nX;
		objMove.m_nToY= p_nY;

		objMove.m_sprAttacked= m_objBoard.getAt(p_nX, p_nY);
		if(objMove.m_sprAttacked != null){
			objMove.m_wasAttacking= true;
			objMove.m_sprAttacked.hide();
		}else{
			objMove.m_wasMoving= true;
		}

		if(p_piece.getType() == SprPiece.PIECE_PAWN)
			movePawnTo(objMove);
		else if(p_piece.getType() == SprPiece.PIECE_KING)
			moveKingTo(objMove);

		m_objBoard.doMove(objMove);
		p_piece.move(objMove);

		return objMove;
	}

	//3 is code to continue loop, but without setting to draw, use in special case of Check
	//2 is code to continue loop
	//1 to stop the Loop INCLUDING this square
	//0 to stop the Loop EXCLUSING this square
	public int checkBasicRules(int p_nX, int p_nY){
		SprPiece sprPiece= m_objBoard.getAt(p_nX, p_nY);

		if(sprPiece == null){
			if(!willBeInCheck(m_sprPieceInHand, p_nX, p_nY)){
				m_sprSelector.setToDraw(p_nX, p_nY);
				return 2;
			}else
				return 3;
		}else if(sprPiece.getTeam() != m_sprPieceInHand.getTeam()){
			if(!willBeInCheck(m_sprPieceInHand, p_nX, p_nY)){
				m_sprSelector.setToDraw(p_nX, p_nY);
				return 1;
			}
		}

		return 0;
	}

	public void checkRulesMove_Pawn(){
		int posX= m_sprPieceInHand.getBoardPosX();
		int posY= m_sprPieceInHand.getBoardPosY();
		int nDirection= 1;

		if(m_sprPieceInHand.getTeam() == 1)
			nDirection*= -1;

		//Will Test Initial 2Square move
		if(!m_sprPieceInHand.m_hasMovedOnce)
			if(m_objBoard.isFree(posX, posY+(2*nDirection)) && m_objBoard.isFree(posX, posY+(1*nDirection)))
				if(!willBeInCheck(m_sprPieceInHand, posX, posY+(2*nDirection)))
					m_sprSelector.setToDraw(posX, posY+(2*nDirection));

		//Will Test Normal Move
		if(m_objBoard.isFree(posX, posY+(1*nDirection)))
			if(!willBeInCheck(m_sprPieceInHand, posX, posY+(1*nDirection)))
				m_sprSelector.setToDraw(posX, posY+(1*nDirection));

		//Will Test Attack, Left and Right
		SprPiece sprPieceAttacked= m_objBoard.getAt(posX-1, posY+(1*nDirection));
		if((sprPieceAttacked != null) && (sprPieceAttacked.getTeam() != m_sprPieceInHand.getTeam()))
			if(!willBeInCheck(m_sprPieceInHand, posX-1, posY+(1*nDirection)))
				m_sprSelector.setToDraw(posX-1, posY+(1*nDirection));

		sprPieceAttacked= m_objBoard.getAt(posX+1, posY+(1*nDirection));
		if((sprPieceAttacked != null) && (sprPieceAttacked.getTeam() != m_sprPieceInHand.getTeam()))
			if(!willBeInCheck(m_sprPieceInHand, posX+1, posY+(1*nDirection)))
				m_sprSelector.setToDraw(posX+1, posY+(1*nDirection));


		//Will Test Attack En Passant Left and right
		sprPieceAttacked= m_objBoard.getAt(posX-1, posY);
		if((sprPieceAttacked != null) && (sprPieceAttacked.getTeam() != m_sprPieceInHand.getTeam()) && sprPieceAttacked.m_hasJustMove2Square)
			if(m_objBoard.getAt(posX-1, posY+(1*nDirection)) == null)
				if(!willBeInCheck(m_sprPieceInHand, posX-1, posY+(1*nDirection)))
					m_sprSelector.setToDraw(posX-1, posY+(1*nDirection));

		sprPieceAttacked= m_objBoard.getAt(posX+1, posY);
		if((sprPieceAttacked != null) && (sprPieceAttacked.getTeam() != m_sprPieceInHand.getTeam()) && sprPieceAttacked.m_hasJustMove2Square)
			if(m_objBoard.getAt(posX-1, posY+(1*nDirection)) == null)
				if(!willBeInCheck(m_sprPieceInHand, posX-1, posY+(1*nDirection)))
					m_sprSelector.setToDraw(posX+1, posY+(1*nDirection));
	}

	public void checkRulesMove_Knight(){
		int posBoardX= m_sprPieceInHand.getBoardPosX();
		int posBoardY= m_sprPieceInHand.getBoardPosY();

		checkBasicRules(posBoardX-1, posBoardY-2);
		checkBasicRules(posBoardX+1, posBoardY-2);
		checkBasicRules(posBoardX-2, posBoardY-1);
		checkBasicRules(posBoardX+2, posBoardY-1);
		checkBasicRules(posBoardX-2, posBoardY+1);
		checkBasicRules(posBoardX+2, posBoardY+1);
		checkBasicRules(posBoardX-1, posBoardY+2);
		checkBasicRules(posBoardX+1, posBoardY+2);
	}

	public void checkRulesMove_Bishop(){
		int posBoardX= m_sprPieceInHand.getBoardPosX();
		int posBoardY= m_sprPieceInHand.getBoardPosY();

		//Manage Row -X ; -Y	//2 is code to continue loop
		for(int i= 1; i < 8; ++i){
			int ret= checkBasicRules(posBoardX-i, posBoardY-i);
			if(!((ret == 2) || (ret == 3)))
				break;
		}

		//Manage Row +X ; -Y	//2 is code to continue loop
		for(int i= 1; i < 8; ++i){
			int ret= checkBasicRules(posBoardX+i, posBoardY-i);
			if(!((ret == 2) || (ret == 3)))
				break;
		}

		//Manage Row +X ; +Y	//2 is code to continue loop
		for(int i= 1; i < 8; ++i){
			int ret= checkBasicRules(posBoardX+i, posBoardY+i);
			if(!((ret == 2) || (ret == 3)))
				break;
		}

		//Manage Row -X ; +Y	//2 is code to continue loop
		for(int i= 1; i < 8; ++i){
			int ret= checkBasicRules(posBoardX-i, posBoardY+i);
			if(!((ret == 2) || (ret == 3)))
				break;
		}
	}

	public void checkRulesMove_Rook(){
		int posBoardX= m_sprPieceInHand.getBoardPosX();
		int posBoardY= m_sprPieceInHand.getBoardPosY();

		//Manage Row -X	//2 is code to continue loop
		for(int i= 1; i < 8; ++i){
			int ret= checkBasicRules(posBoardX-i, posBoardY);
			if(!((ret == 2) || (ret == 3)))
				break;
		}

		//Manage Row +X	//2 is code to continue loop
		for(int i= 1; i < 8; ++i){
			int ret= checkBasicRules(posBoardX+i, posBoardY);
			if(!((ret == 2) || (ret == 3)))
				break;
		}

		//Manage Row -Y	//2 is code to continue loop
		for(int i= 1; i < 8; ++i){
			int ret= checkBasicRules(posBoardX, posBoardY-i);
			if(!((ret == 2) || (ret == 3)))
				break;
		}

		//Manage Row +Y	//2 is code to continue loop
		for(int i= 1; i < 8; ++i){
			int ret= checkBasicRules(posBoardX, posBoardY+i);
			if(!((ret == 2) || (ret == 3)))
				break;
		}
	}
	
	public void checkRulesMove_King(){
		int posX= m_sprPieceInHand.getBoardPosX();
		int posY= m_sprPieceInHand.getBoardPosY();

		for(int i= posX-1; i <= posX+1; ++i){
			for(int j= posY-1; j <= posY+1; ++j){
				if((i < 0) || (i >= 8) || (j < 0) || (j >= 8))
					continue;

				SprPiece sprPiece= m_objBoard.getAt(i, j);
				if(sprPiece == null){
					if(!willBeInCheck(m_sprPieceInHand, i, j))
						m_sprSelector.setToDraw(i, j);
				}else if(sprPiece.getTeam() != m_sprPieceInHand.getTeam()){
					if(!willBeInCheck(m_sprPieceInHand, i, j))
						m_sprSelector.setToDraw(i, j);
				}
			}
		}

		//Castling
		if(!m_objBoard.isInCheck(m_sprPieceInHand.getTeam()) && !m_sprPieceInHand.m_hasMovedOnce){
			//Queenside
			SprPiece pieceRook= m_objBoard.getAt(0, m_sprPieceInHand.getTeam() * 7);
			if((pieceRook != null) && (pieceRook.getType() == SprPiece.PIECE_ROOK) && !pieceRook.m_hasMovedOnce)
				if((m_objBoard.getAt(posX-1, posY) == null) && (m_objBoard.getAt(posX-2, posY) == null) && (m_objBoard.getAt(posX-3, posY) == null))
					if(!willBeInCheck(m_sprPieceInHand, posX-1, posY) && !willBeInCheck(m_sprPieceInHand, posX-2, posY))
						m_sprSelector.setToDraw(posX-2, posY);

			//Kingside
			pieceRook= m_objBoard.getAt(7, m_sprPieceInHand.getTeam() * 7);
			if((pieceRook != null) && (pieceRook.getType() == SprPiece.PIECE_ROOK) && !pieceRook.m_hasMovedOnce)
				if((m_objBoard.getAt(posX+1, posY) == null) && (m_objBoard.getAt(posX+2, posY) == null))
					if(!willBeInCheck(m_sprPieceInHand, posX+1, posY) && !willBeInCheck(m_sprPieceInHand, posX+2, posY))
						m_sprSelector.setToDraw(posX+2, posY);
		}
	}

	public void preCheckRulesMove(){
		m_sprSelector.clearBoardWillDraw();
		m_isCheckRulesDone= false;
	}

	public void checkRulesMove(){
		if(m_isCheckRulesDone)
			return;
		
		switch(m_sprPieceInHand.getType()){
		case SprPiece.PIECE_PAWN:	checkRulesMove_Pawn();	break;
		case SprPiece.PIECE_KNIGHT:	checkRulesMove_Knight();break;
		case SprPiece.PIECE_BISHOP:	checkRulesMove_Bishop();break;
		case SprPiece.PIECE_ROOK:	checkRulesMove_Rook();	break;
		case SprPiece.PIECE_QUEEN:	checkRulesMove_Bishop();checkRulesMove_Rook();	break;
		case SprPiece.PIECE_KING:	checkRulesMove_King();	break;
		}

		m_isCheckRulesDone= true;
	}

	public void managePromotions(int p_nTeam){
		for(int i= 0; i < 8; ++i){
			SprPiece piece= m_objBoard.getAt(i, (1-p_nTeam)*7);
			if((piece != null) && (piece.getType() == SprPiece.PIECE_PAWN) && (piece.getTeam() == p_nTeam)){
				piece.setType(SprPiece.PIECE_QUEEN);	//Maybe ask for differate piece
				m_objBoard.setAt(piece, i, 7);
			}
		}
	}
}
