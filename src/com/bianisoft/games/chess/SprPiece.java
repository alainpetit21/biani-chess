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

//Bianisoft imports
import com.bianisoft.engine.Obj;
import com.bianisoft.engine.sprites.Sprite;


public class SprPiece extends Sprite{
	static final public int IDCLASS_Piece= (1<<16) | Obj.IDCLASS_GAME | Obj.IDCLASS_Sprite;

	public final static int PIECE_PAWN	= 0;
	public final static int PIECE_KNIGHT= 1;
	public final static int PIECE_BISHOP= 2;
	public final static int PIECE_ROOK	= 3;
	public final static int PIECE_QUEEN	= 4;
	public final static int PIECE_KING	= 5;


	protected int m_nType;
	protected int m_nTeam;
	protected int m_nBoardX;
	protected int m_nBoardY;

	protected boolean	m_isInHand;
	protected boolean	m_hasMovedOnce;
	protected boolean	m_hasJustMove2Square;

	private Stack<Move> m_stkMoves= new Stack<Move>();


	public SprPiece(String p_stResFile)	{super(p_stResFile); setClassID(IDCLASS_Piece, "IDCLASS_Piece");}
	public SprPiece(Sprite p_sprTemplate)	{super(p_sprTemplate); setClassID(IDCLASS_Piece, "IDCLASS_Piece");}

	public void setType(int p_nType)		{m_nType= p_nType;}
	public void setTeam(int p_nTeam)		{m_nTeam= p_nTeam;}
	public void resetBoardPos()				{setPosX(-235 + (m_nBoardX*43)); setPosY(150 - (m_nBoardY*48));}
	public void setBoardPosX(int p_nPosX)	{m_nBoardX= p_nPosX; setPosX(-235 + (m_nBoardX*43));}
	public void setBoardPosY(int p_nPosY)	{m_nBoardY= p_nPosY; setPosY(150 - (m_nBoardY*48));}
	public void setBoardPos(int p_nPosX, int p_nPosY)	{setBoardPosX(p_nPosX); setBoardPosY(p_nPosY); }

	public int getType()		{return m_nType;}
	public int getTeam()		{return m_nTeam;}
	public int getBoardPosX()	{return m_nBoardX;}
	public int getBoardPosY()	{return m_nBoardY;}

	public void undoMove(Move p_objMove){
		if(m_stkMoves.empty())
			return;

		if(m_hasJustMove2Square)
			m_hasJustMove2Square= false;

		Move poped= m_stkMoves.pop();
		assert(p_objMove == poped);

		if(m_stkMoves.empty())
			m_hasMovedOnce= false;
		setBoardPos(p_objMove.m_nFromX, p_objMove.m_nFromY);
	}

	public void move(Move p_objMove){
		m_hasMovedOnce= true;

		if(m_nType == PIECE_PAWN)
			m_hasJustMove2Square= Math.abs(p_objMove.m_nToY - m_nBoardY) == 2;

		setBoardPos(p_objMove.m_nToX, p_objMove.m_nToY);
		m_stkMoves.push(p_objMove);
	}

	public void manage(float p_fTimeScaleFactor){
		super.manage(p_fTimeScaleFactor);

		setCurFrame(m_nType);
	}
}
