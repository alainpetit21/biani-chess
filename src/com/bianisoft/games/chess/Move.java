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


public class Move{
	public int m_nFromX;
	public int m_nFromY;
	public int m_nToX;
	public int m_nToY;
	public boolean m_wasQueensideCastling;
	public boolean m_wasKingsideCastling;
	public boolean m_wasAttacking;
	public boolean m_wasEnPassant;
	public boolean m_wasMoving;
	public boolean m_wasMovingPawnInitial2Square;
	public SprPiece	m_sprSubject;
	public SprPiece	m_sprAttacked;

	public double m_nScore;
}

