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
import java.util.ArrayList;


public class AI {
	final private int[]		m_nPiecesValue= {100, 320, 325, 500, 975, 10000};
	private final int[][][]	m_nPositionValue= {
		{{0,  0,  0,  0,  0,  0,  0,  0},
		{30, 50, 50, 50, 50, 50, 50, 30},
		{-10, 10, 20, 30, 30, 20, 10, -10},
		{-15,  5, 10, 27, 27, 10,  5, -15},
		{-20,  0,  0, 25, 25,  0,  0, -20},
		{-15, -5,-10,  0,  0,-10, -5, -15},
		{-15, 10, 10,-25,-25, 10, 10, -15},
		{0,  0,  0,  0,  0,  0,  0,  0}},

		{{-50,-40,-30,-30,-30,-30,-40,-50},
		{-40,-20,  0,  0,  0,  0,-20,-40},
		{-30,  0, 10, 15, 15, 10,  0,-30},
		{-30,  5, 15, 20, 20, 15,  5,-30},
		{-30,  0, 15, 20, 20, 15,  0,-30},
		{-30,  5, 10, 15, 15, 10,  5,-30},
		{-40,-20,  0,  5,  5,  0,-20,-40},
		{-50,-40,-20,-30,-30,-20,-40,-50}},

		{{-20,-10,-10,-10,-10,-10,-10,-20},
		{-10,  0,  0,  0,  0,  0,  0,-10},
		{-10,  0,  5, 10, 10,  5,  0,-10},
		{-10,  5,  5, 10, 10,  5,  5,-10},
		{-10,  0, 10, 10, 10, 10,  0,-10},
		{-10, 10, 10, 10, 10, 10, 10,-10},
		{-10,  5,  0,  0,  0,  0,  5,-10},
		{-20,-10,-40,-10,-10,-40,-10,-20}},

		{{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0}},

		{{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0}},

		{{-30, -40, -40, -50, -50, -40, -40, -30},
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-20, -30, -30, -40, -40, -30, -30, -20},
		{-10, -20, -20, -20, -20, -20, -20, -10},
		{20,  20,   0,   0,   0,   0,  20,  20},
		{20,  30,  10,   0,   0,  10,  30,  20}}};

	public Board			m_objBoard;
	public MoveCoordinator	m_objMover;

	public int		m_nScoreMax;
	public int[]	m_nScore;
    
    //Few Pieces shortcuts
	private final	SprPiece[]	m_pieceKing;
	private final	ArrayList[]	m_vecAllPieces;
	private final	ArrayList[]	m_vecAllRooks;
	private final	ArrayList[]	m_vecAllKnights;
	private final	ArrayList[]	m_vecAllBishops;
	private final	ArrayList[]	m_vecAllPawns;


	public AI(Board p_objBoard, MoveCoordinator p_objCoordinator){
        m_vecAllPawns   = new ArrayList[2];
        m_vecAllBishops = new ArrayList[2];
        m_vecAllKnights = new ArrayList[2];
        m_vecAllRooks   = new ArrayList[2];
        m_vecAllPieces  = new ArrayList[2];
        m_pieceKing     = new SprPiece[2];
        m_nScore        = new int[2];
        
		m_objBoard= p_objBoard;
		m_objMover= p_objCoordinator;

		m_vecAllPieces[0]	= new ArrayList();
		m_vecAllPieces[1]	= new ArrayList();
		m_vecAllRooks[0]	= new ArrayList();
		m_vecAllRooks[1]	= new ArrayList();
		m_vecAllKnights[0]	= new ArrayList();
		m_vecAllKnights[1]	= new ArrayList();
		m_vecAllBishops[0]	= new ArrayList();
		m_vecAllBishops[1]	= new ArrayList();
		m_vecAllPawns[0]	= new ArrayList();
		m_vecAllPawns[1]	= new ArrayList();
	}

	public void load(){
		for(int i= 0; i < 8; ++i){
			for(int j= 0; j < 8; ++j){
				SprPiece piece= m_objBoard.getAt(i, j);

				if(piece == null)
					continue;

				m_vecAllPieces[piece.getTeam()].add(piece);

                switch (piece.getType()) {
                    case SprPiece.PIECE_KING:
                        m_pieceKing[piece.getTeam()]= piece;
                        break;
                    case SprPiece.PIECE_ROOK:
                        m_vecAllRooks[piece.getTeam()].add(piece);
                        break;
                    case SprPiece.PIECE_KNIGHT:
                        m_vecAllKnights[piece.getTeam()].add(piece);
                        break;
                    case SprPiece.PIECE_BISHOP:
                        m_vecAllBishops[piece.getTeam()].add(piece);
                        break;
                    case SprPiece.PIECE_PAWN:
                        m_vecAllPawns[piece.getTeam()].add(piece);
                        break;
                    default:
                        break;
                }
			}
		}
	}

	public float getScore(int p_nTeam){
		return (((float)m_nScore[p_nTeam]) / (float)m_nScoreMax)*100.0f;
	}

	private void evaluateBoardPiecesValues(int p_nTeam){
		for(int i= 0; i < m_vecAllPieces[p_nTeam].size(); ++i){
			SprPiece piece= (SprPiece)m_vecAllPieces[p_nTeam].get(i);

			if(piece.isShown())
				m_nScore[p_nTeam]+= m_nPiecesValue[piece.getType()];
		}
	}

	private void evaluateBoardPiecesPositions(int p_nTeam){
		for(int i= 0; i < m_vecAllPieces[p_nTeam].size(); ++i){
			SprPiece piece= (SprPiece)m_vecAllPieces[p_nTeam].get(i);

			if(piece.isShown())
				m_nScore[p_nTeam]+= m_nPositionValue[piece.getType()][piece.getBoardPosX()][piece.getBoardPosY()];
		}
	}

	private void evaluateProtectedPieces(int p_nTeam){
		for(int i= 0; i < m_vecAllPieces[p_nTeam].size(); ++i){
			SprPiece piece= (SprPiece)m_vecAllPieces[p_nTeam].get(i);

			if(piece.isShown())
				if(m_objBoard.isPieceThreatened(piece, 1 - p_nTeam))
					if(piece.getType() != SprPiece.PIECE_KING)
						m_nScore[p_nTeam]+= m_nPiecesValue[piece.getType()] / 10;
		}
	}

	private void evaluateThreatenedPieces(int p_nTeam){
		for(int i= 0; i < m_vecAllPieces[p_nTeam].size(); ++i){
			SprPiece piece= (SprPiece)m_vecAllPieces[p_nTeam].get(i);

			if(piece.isShown())
				if(m_objBoard.isPieceThreatened(piece, p_nTeam))
					if(piece.getType() != SprPiece.PIECE_KING)
						m_nScore[p_nTeam]+= m_nPiecesValue[piece.getType()] / 10;
		}
	}
	
	public void evaluateRookSpecialBeforeCastling(int p_nTeam){
		if(!m_pieceKing[p_nTeam].m_hasMovedOnce){
			for(int i= 0; i < m_vecAllRooks[p_nTeam].size(); ++i){
				SprPiece piece= (SprPiece)m_vecAllRooks[p_nTeam].get(i);
				
				if(!piece.isShown())
					continue;
					
				if((piece.getBoardPosX() == 0 ) && (piece.getBoardPosY() == 0 + (p_nTeam*7)))
					m_nScore[p_nTeam]+= 20;
				else if((piece.getBoardPosX() == 7) && (piece.getBoardPosY() == 0 + (p_nTeam*7)))
					m_nScore[p_nTeam]+= 20;
			}
		}
	}

	public void evaluateBoard(){
		m_nScore[0]= 0;
		m_nScore[1]= 0;

		evaluateBoardPiecesValues(0);
		evaluateBoardPiecesValues(1);
		evaluateBoardPiecesPositions(0);
		evaluateBoardPiecesPositions(1);
		evaluateThreatenedPieces(0);
		evaluateThreatenedPieces(1);
		evaluateProtectedPieces(0);
		evaluateProtectedPieces(1);

		//Special Strategies
		evaluateRookSpecialBeforeCastling(0);
		evaluateRookSpecialBeforeCastling(1);
//		evaluatePawnGoForPromotion

		//Final Calculation
		if((m_nScore[0]-= m_nPiecesValue[SprPiece.PIECE_KING]) < 0)
			m_nScore[0]= 0;
		if((m_nScore[1]-= m_nPiecesValue[SprPiece.PIECE_KING]) < 0)
			m_nScore[1]= 0;
		m_nScoreMax= m_nScore[0]+m_nScore[1];
	}

	public Move doSearch(boolean p_isMax, int p_nTeam, int p_nDepth){
		double score			= 10000 * ((p_isMax)? -1:1);
		double bestSubScoreSub	= 10000 * ((p_isMax)? -1:1);
		double bestScore		= 10000 * ((p_isMax)? -1:1);
		Move move		= null;
		Move bestMove	= null;
		Move bestSubMove= null;

		for(int i= 0; i < m_vecAllPieces[p_nTeam].size(); ++i){
			SprPiece piece= (SprPiece)m_vecAllPieces[p_nTeam].get(i);

			if(!piece.isShown())
				continue;

			m_objMover.m_sprPieceInHand= piece;
			m_objMover.preCheckRulesMove();
			m_objMover.checkRulesMove();

			for(int x= 0; x < 8; ++x){
				for(int y= 0; y < 8; ++y){
					if(m_objMover.m_sprSelector.isActive(x, y)){
						move= m_objMover.doMove(piece, x, y);
						evaluateBoard();
						move.m_nScore= score= getScore(p_nTeam);

						if(p_isMax && (move.m_nScore > bestScore)){
							bestMove= move;
							bestScore= move.m_nScore;
						}else if(!p_isMax && (move.m_nScore < bestScore)){
							bestMove= move;
							bestScore= move.m_nScore;
						}

						if(p_nDepth > 0){
							m_objMover.m_sprSelector.pushSelector();
							bestSubMove= doSearch(!p_isMax, 1-p_nTeam, p_nDepth-1);
							m_objMover.m_sprSelector.popSelector();
							m_objMover.m_sprPieceInHand= piece;

							if(p_isMax && ((100 - bestSubMove.m_nScore) > bestScore)){
								move.m_nScore= 100 - bestSubMove.m_nScore;
								bestScore= 100 - bestSubMove.m_nScore;
								bestMove= move;
							}else if(!p_isMax && ((100 - bestSubMove.m_nScore) < bestScore)){
								move.m_nScore= 100 - bestSubMove.m_nScore;
								bestScore= 100 - bestSubMove.m_nScore;
								bestMove= move;
							}
						}

						m_objMover.undoMove(move);
					}
				}
			}
		}
		return bestMove;
	}
	
	public Move think(int p_nTeam){
		Move move= doSearch(true, p_nTeam, 2);
		
		evaluateBoard();	//Re-evaluate the "Current" State
		return move;
	}
}
