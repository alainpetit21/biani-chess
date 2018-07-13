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
import java.util.Stack;
import java.util.ArrayList;

//Bianisoft imports
import com.bianisoft.engine.backgrounds.Background;
import com.bianisoft.engine.Context;
import com.bianisoft.engine.Countainer;
import com.bianisoft.engine.labels.Label;
import com.bianisoft.engine.PhysObj;
import com.bianisoft.engine.sprites.Sprite;
import com.bianisoft.engine.manager.MngInput;


public class CtxGame extends Context{
	public final static int STATE_TEAM0_PICKING		= 0;
	public final static int STATE_TEAM0_IN_HAND		= 1;
	public final static int STATE_TEAM0_DROPPED		= 2;
	public final static int STATE_TEAM1_PICKING		= 3;
	public final static int STATE_TEAM1_IN_HAND		= 4;
	public final static int STATE_TEAM1_DROPPED		= 5;
	public final static int STATE_ENDGAME			= 6;

	//GUI Objects
	public Background[]	m_arBackground= new Background[5];
	public Sprite		m_sprMouse;
	public SprSelector	m_sprSelector;
	public SprPiece		m_sprPieceInHand;
	public Label		m_lblStatus;
	public Label[]		m_lblScore= new Label[2];

	//Game logic Objects
	public Stack<Move>		m_stkAllMoves	= new Stack<Move>();
	public Board			m_objBoard		= new Board();
	public MoveCoordinator	m_objMoveCoord	= new MoveCoordinator(m_objBoard);
	public AI				m_objAI			= new AI(m_objBoard, m_objMoveCoord);
	public boolean[]		m_isTeamHuman	= {true, false};
	public int				m_nGameState	= STATE_TEAM0_PICKING;


	public void activate(){
		super.activate();

		DesignCtxGame.load(this);

		//GUI Activate
		m_sprMouse= (Sprite)findByTextID("Spr_Cursor");
		m_sprMouse.setHotSpot(0, 16);
		setCursor(m_sprMouse);

		m_lblStatus= (Label)findByTextID("Lbl_GameStatus");
		m_lblStatus.hide();

		m_lblStatus= (Label)findByTextID("Lbl_GameStatus");
		m_lblStatus.hide();

		m_lblScore[0]= (Label)findByTextID("Lbl_GameTeam0ScoreValue");
		m_lblScore[1]= (Label)findByTextID("Lbl_GameTeam1ScoreValue");

		m_sprSelector= (SprSelector)findByTextID("Spr_Selector");
		m_sprSelector.hide();

		for(int i= 0; i < 5; ++i){
			m_arBackground[i]= (Background)findByTextID("Back_GameBoard" + i);
			m_arBackground[i].hide();
		}
		m_arBackground[0].show();

		//Game Logic Activate
		m_objBoard.load(this, GlobalSetting.m_pieceColor);
		m_objMoveCoord.m_sprSelector= m_sprSelector;
		m_objAI.load();
	}

	public void manageSort(Countainer p_objCountainer){
		ArrayList<PhysObj>	vecChilds= p_objCountainer.getVectorChilds();

		for(int i= 0; i < vecChilds.size(); ++i){
			PhysObj physObj1= vecChilds.get(i);

			for(int j= i+1; j < vecChilds.size(); ++j){
				PhysObj physObj2= vecChilds.get(j);

				if(physObj2.getPosZ() > physObj1.getPosZ()){
					vecChilds.set(i, physObj2);
					vecChilds.set(j, physObj1);
					i= -1;
					break;
				}else if(physObj2.getPosZ() == physObj1.getPosZ()){
					if(physObj2.getPosY() < physObj1.getPosY()){
						vecChilds.set(i, physObj2);
						vecChilds.set(j, physObj1);
						i= -1;
						break;
					}
				}
			}
		}
	}

	public void manage(float p_fTimeScaleFactor){
		MngInput mngInput= MngInput.get();

		//Core manage
		super.manage(p_fTimeScaleFactor);

		//AI Manage
		m_objAI.evaluateBoard();

		//GUI Manage
		m_lblScore[0].set(m_objAI.getScore(0), 2);	m_lblScore[0].append(" %");
		m_lblScore[1].set(m_objAI.getScore(1), 2);	m_lblScore[1].append(" %");

		//Game Logic Manage
		if((m_nGameState == STATE_TEAM0_PICKING) || (m_nGameState == STATE_TEAM1_PICKING)){
			int playingTeam= m_nGameState == STATE_TEAM0_PICKING? 0:1;

			if(m_isTeamHuman[playingTeam]){
				if(mngInput.isMouseClicked(MngInput.M_LEFT)){
					SprPiece sprPiece= (SprPiece)findAtByRtti((int)m_sprMouse.getPosX(), (int)m_sprMouse.getPosY(), Integer.MIN_VALUE, SprPiece.IDCLASS_Piece);

					if((sprPiece != null) && (sprPiece.getTeam() == playingTeam)){
						m_sprPieceInHand= sprPiece;
						m_sprSelector.show();
						m_sprSelector.setBoardPos(sprPiece.getBoardPosX(), sprPiece.getBoardPosY());
						m_objMoveCoord.m_sprPieceInHand= m_sprPieceInHand;
						m_objMoveCoord.preCheckRulesMove();
						m_nGameState++;
					}
				}else if(mngInput.isKeyboardClicked(MngInput.K_ENTER)){
					if(!m_stkAllMoves.empty()){
						Move poped= m_stkAllMoves.pop();
						m_objMoveCoord.undoMove(poped);
						m_nGameState= (m_nGameState==0)? 3:0;
					}
					if(!m_stkAllMoves.empty()){
						Move poped= m_stkAllMoves.pop();
						m_objMoveCoord.undoMove(poped);
						m_nGameState= (m_nGameState==0)? 3:0;
					}
				}
			}else{
				Move move= m_objAI.think(m_nGameState == STATE_TEAM0_PICKING? 0:1);
				m_sprPieceInHand= m_objMoveCoord.m_sprPieceInHand= move.m_sprSubject;

				move= m_objMoveCoord.doMove(m_sprPieceInHand, move.m_nToX, move.m_nToY);
				m_objMoveCoord.m_sprPieceInHand= m_sprPieceInHand= null;
				m_stkAllMoves.push(move);
				m_nGameState+= 2;
			}
		}else if((m_nGameState == STATE_TEAM0_IN_HAND) || (m_nGameState == STATE_TEAM1_IN_HAND)){
			m_objMoveCoord.checkRulesMove();
			m_sprPieceInHand.setPosX(m_sprMouse.getPosX());
			m_sprPieceInHand.setPosY(m_sprMouse.getPosY()-32);
			
			if(mngInput.isMouseClicked(MngInput.M_RIGHT)){
				m_sprPieceInHand.resetBoardPos();
				m_objMoveCoord.m_sprPieceInHand= m_sprPieceInHand= null;
				m_sprSelector.hide();
				m_nGameState--;
			}else if(mngInput.isMouseClicked(MngInput.M_LEFT)){
				int boardPosX= Board.ConvertScreenX((int)m_sprMouse.getPosX());
				int boardPosY= Board.ConvertScreenY((int)m_sprMouse.getPosY());

				if(!((boardPosX < 0) || (boardPosX >= 8) || (boardPosY < 0) || (boardPosY >= 8))){
					if(m_objMoveCoord.isMoveOk(boardPosX, boardPosY)){
						m_sprSelector.hide();
						Move move= m_objMoveCoord.doMove(m_sprPieceInHand, boardPosX, boardPosY);
						m_objMoveCoord.m_sprPieceInHand= m_sprPieceInHand= null;
						m_stkAllMoves.push(move);
						m_nGameState++;
					}
				}
			}
		}else if((m_nGameState == STATE_TEAM0_DROPPED) || (m_nGameState == STATE_TEAM1_DROPPED)){
			int nCurTeam= (m_nGameState == STATE_TEAM0_DROPPED)? 0:1;
			int nTeamToCheck= 1- nCurTeam;

			m_objMoveCoord.managePromotions(nCurTeam);

			if(m_objMoveCoord.isInCheck(nTeamToCheck)){
				if(m_objMoveCoord.isInStalemate(nTeamToCheck)){
					m_lblStatus.set("Team" + nTeamToCheck + " is in checkmate");
					m_lblStatus.show();
					m_nGameState= STATE_ENDGAME;
				}else{
					m_lblStatus.set("Team" + nTeamToCheck + " is in check");
					m_lblStatus.show();
					m_nGameState= (nTeamToCheck==1)? STATE_TEAM1_PICKING:STATE_TEAM0_PICKING;
				}
			}else{
				if(m_objMoveCoord.isInStalemate(nTeamToCheck)){
					m_lblStatus.set("Team" + nTeamToCheck + " is in stalemate");
					m_lblStatus.show();
					m_nGameState= STATE_ENDGAME;
				}else{
					m_lblStatus.hide();
					m_nGameState= (nTeamToCheck==1)? STATE_TEAM1_PICKING:STATE_TEAM0_PICKING;
				}
			}
		}else if(m_nGameState == STATE_ENDGAME){
			return;
		}
	}
}
