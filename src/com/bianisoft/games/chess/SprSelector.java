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


//Bianisoft imports
import java.util.Stack;
import com.bianisoft.engine.Obj;
import com.bianisoft.engine.sprites.Sprite;


public class SprSelector extends Sprite{
	static final public int IDCLASS_Selector= (1<<17) | Obj.IDCLASS_GAME | Obj.IDCLASS_Sprite;

	private int m_nBoardX;
	private int m_nBoardY;

	public Stack<boolean[][]>	m_stkWillDraw= new Stack<boolean[][]>();


	public SprSelector(String p_stResFile)	{super(p_stResFile); setClassID(IDCLASS_Selector, "IDCLASS_Selector");}
	public void setBoardPosX(int p_nPosX)	{m_nBoardX= p_nPosX; setPosX(-237 + (m_nBoardX*43));}
	public void setBoardPosY(int p_nPosY)	{m_nBoardY= p_nPosY; setPosY(168 - (m_nBoardY*48));}
	public void setBoardPos(int p_nPosX, int p_nPosY)	{setBoardPosX(p_nPosX); setBoardPosY(p_nPosY); }

	public int getBoardPosX()	{return m_nBoardX;}
	public int getBoardPosY()	{return m_nBoardY;}

	public void setToDraw(int p_nX, int p_nY){
		if((p_nX < 0) || (p_nX >= 8) || (p_nY < 0) || (p_nY >= 8))
			return;

		boolean[][] willDraw= m_stkWillDraw.peek();

		willDraw[p_nX][p_nY]= true;
	}

	public boolean isActive(int p_nX, int p_nY){
		if((p_nX < 0) || (p_nX >= 8) || (p_nY < 0) || (p_nY >= 8))
			return false;

		boolean[][] willDraw= m_stkWillDraw.peek();

		return willDraw[p_nX][p_nY];
	}

	public void clearBoardWillDraw(){
		boolean[][] willDraw= m_stkWillDraw.peek();

		for(int x= 0; x < 8; ++x){
			for(int y= 0; y < 8; ++y){
				willDraw[x][y]= false;
			}
		}
	}

	public boolean isClear(){
		boolean[][] willDraw= m_stkWillDraw.peek();

		for(int x= 0; x < 8; ++x){
			for(int y= 0; y < 8; ++y){
				if(willDraw[x][y])
					return false;
			}
		}

		return true;
	}

	public void pushSelector(){
		m_stkWillDraw.push(new boolean[8][8]);
	}

	public void popSelector(){
		m_stkWillDraw.pop();
	}

	public void load() {
		super.load();
		m_stkWillDraw.push(new boolean[8][8]);
	}

	public void hide(){
		super.hide();
		clearBoardWillDraw();
	}

	public void draw(){
		setCurState(0);
		super.draw();

		setCurState(1);
		boolean[][] willDraw= m_stkWillDraw.peek();

		for(int x= 0; x < 8; ++x){
			for(int y= 0; y < 8; ++y){
				if(willDraw[x][y]){
					setPosX(-237 + (x*43));	setPosY(168 - (y*48));
					super.draw();
				}
			}
		}

		setBoardPosX(getBoardPosX());
		setBoardPosY(getBoardPosY());
	}

}
