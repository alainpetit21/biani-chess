package com.bianisoft.games.chess;


//LWJGL library imports
import org.lwjgl.util.Rectangle;

//Bianisoft imports
import com.bianisoft.engine.Context;
import com.bianisoft.engine.backgrounds.Background;
import com.bianisoft.engine.labels.Label;
import com.bianisoft.engine.sprites.Sprite;
import com.bianisoft.engine.sprites.Sprite.State;
import com.bianisoft.engine.sprites.Button;


public class DesignCtxGame{
	public static void load(Context p_ctxUnder){
		/*DATA_BACKGROUND_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS*/
		/*DATA:0|Background|Back_Game|/res/backgrounds/Game.png|0|0|10|1|*/
		Background backBack_Game= new Background("/res/backgrounds/Game.png");
		backBack_Game.setTextID("Back_Game");
		backBack_Game.setPos(0, 0, 10);
		backBack_Game.load();
		p_ctxUnder.addChild(backBack_Game, false, false);

		/*DATA_BACKGROUND_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS*/
		/*DATA:1|Background|Back_GameBoard0|/res/backgrounds/Board0.png|-85|0|9|1|*/
		Background backBack_GameBoard0= new Background("/res/backgrounds/Board0.png");
		backBack_GameBoard0.setTextID("Back_GameBoard0");
		backBack_GameBoard0.setPos(-85, 0, 9);
		backBack_GameBoard0.load();
		p_ctxUnder.addChild(backBack_GameBoard0, false, false);

		/*DATA_BACKGROUND_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS*/
		/*DATA:2|Background|Back_GameBoard1|/res/backgrounds/Board1.png|-85|0|9|1|*/
		Background backBack_GameBoard1= new Background("/res/backgrounds/Board1.png");
		backBack_GameBoard1.setTextID("Back_GameBoard1");
		backBack_GameBoard1.setPos(-85, 0, 9);
		backBack_GameBoard1.load();
		p_ctxUnder.addChild(backBack_GameBoard1, false, false);

		/*DATA_BACKGROUND_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS*/
		/*DATA:3|Background|Back_GameBoard2|/res/backgrounds/Board2.png|-85|0|9|1|*/
		Background backBack_GameBoard2= new Background("/res/backgrounds/Board2.png");
		backBack_GameBoard2.setTextID("Back_GameBoard2");
		backBack_GameBoard2.setPos(-85, 0, 9);
		backBack_GameBoard2.load();
		p_ctxUnder.addChild(backBack_GameBoard2, false, false);

		/*DATA_BACKGROUND_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS*/
		/*DATA:4|Background|Back_GameBoard3|/res/backgrounds/Board3.png|-85|0|9|1|*/
		Background backBack_GameBoard3= new Background("/res/backgrounds/Board3.png");
		backBack_GameBoard3.setTextID("Back_GameBoard3");
		backBack_GameBoard3.setPos(-85, 0, 9);
		backBack_GameBoard3.load();
		p_ctxUnder.addChild(backBack_GameBoard3, false, false);

		/*DATA_BACKGROUND_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS*/
		/*DATA:5|Background|Back_GameBoard4|/res/backgrounds/Board4.png|-85|0|9|1|*/
		Background backBack_GameBoard4= new Background("/res/backgrounds/Board4.png");
		backBack_GameBoard4.setTextID("Back_GameBoard4");
		backBack_GameBoard4.setPos(-85, 0, 9);
		backBack_GameBoard4.load();
		p_ctxUnder.addChild(backBack_GameBoard4, false, false);

		/*DATA_LABEL_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS|FONT_SIZE|TEXT|ALIGNMENT|MULTILINE|REC_LEFT|REC_TOP|REC_WIDTH|RECT_HEIGHT*/
		/*DATA:6|Label|Lbl_GameStatus|/res/font/FreeSerif.ttf|-84|220|9|1|25|Team# is in check|1|false|-192|-13|384|26|*/
		Label lblLbl_GameStatus= new Label("/res/font/FreeSerif.ttf", 25, "Team# is in check", 1, false, new Rectangle(-192, -13, 384, 26));
		lblLbl_GameStatus.setTextID("Lbl_GameStatus");
		lblLbl_GameStatus.setPos(-84, 220, 9);
		lblLbl_GameStatus.load();
		p_ctxUnder.addChild(lblLbl_GameStatus, false, false);

		/*DATA_LABEL_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS|FONT_SIZE|TEXT|ALIGNMENT|MULTILINE|REC_LEFT|REC_TOP|REC_WIDTH|RECT_HEIGHT*/
		/*DATA:7|Label|Lbl_GameTeam0ScoreValue|/res/font/FreeSerif.ttf|299|50|9|1|25|0|2|false|-125|-13|125|26|*/
		Label lblLbl_GameTeam0ScoreValue= new Label("/res/font/FreeSerif.ttf", 25, "0", 2, false, new Rectangle(-125, -13, 125, 26));
		lblLbl_GameTeam0ScoreValue.setTextID("Lbl_GameTeam0ScoreValue");
		lblLbl_GameTeam0ScoreValue.setPos(299, 50, 9);
		lblLbl_GameTeam0ScoreValue.load();
		p_ctxUnder.addChild(lblLbl_GameTeam0ScoreValue, false, false);

		/*DATA_LABEL_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS|FONT_SIZE|TEXT|ALIGNMENT|MULTILINE|REC_LEFT|REC_TOP|REC_WIDTH|RECT_HEIGHT*/
		/*DATA:8|Label|Lbl_GameTeam1ScoreValue|/res/font/FreeSerif.ttf|300|-130|9|1|25|0|2|false|-125|-13|125|26|*/
		Label lblLbl_GameTeam1ScoreValue= new Label("/res/font/FreeSerif.ttf", 25, "0", 2, false, new Rectangle(-125, -13, 125, 26));
		lblLbl_GameTeam1ScoreValue.setTextID("Lbl_GameTeam1ScoreValue");
		lblLbl_GameTeam1ScoreValue.setPos(300, -130, 9);
		lblLbl_GameTeam1ScoreValue.load();
		p_ctxUnder.addChild(lblLbl_GameTeam1ScoreValue, false, false);

		/*DATA_LABEL_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS|FONT_SIZE|TEXT|ALIGNMENT|MULTILINE|REC_LEFT|REC_TOP|REC_WIDTH|RECT_HEIGHT*/
		/*DATA:9|Label|Lbl_GameTeam1Score|/res/font/FreeSerif.ttf|175|-160|9|1|25|Score:|0|false|0|-13|125|26|*/
		Label lblLbl_GameTeam1Score= new Label("/res/font/FreeSerif.ttf", 25, "Score:", 0, false, new Rectangle(0, -13, 125, 26));
		lblLbl_GameTeam1Score.setTextID("Lbl_GameTeam1Score");
		lblLbl_GameTeam1Score.setPos(175, -160, 9);
		lblLbl_GameTeam1Score.load();
		p_ctxUnder.addChild(lblLbl_GameTeam1Score, false, false);

		/*DATA_LABEL_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS|FONT_SIZE|TEXT|ALIGNMENT|MULTILINE|REC_LEFT|REC_TOP|REC_WIDTH|RECT_HEIGHT*/
		/*DATA:10|Label|Lbl_GameTeam0Score|/res/font/FreeSerif.ttf|175|20|9|1|25|Score:|0|false|0|-13|125|26|*/
		Label lblLbl_GameTeam0Score= new Label("/res/font/FreeSerif.ttf", 25, "Score:", 0, false, new Rectangle(0, -13, 125, 26));
		lblLbl_GameTeam0Score.setTextID("Lbl_GameTeam0Score");
		lblLbl_GameTeam0Score.setPos(175, 20, 9);
		lblLbl_GameTeam0Score.load();
		p_ctxUnder.addChild(lblLbl_GameTeam0Score, false, false);

		/*DATA_CUSTOM_SPRITE_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS|DEFAULT_STATE|DEFAULT_FRAME|NB_STATES|STATE_NAME|STATE_NB_FRAMES|STATE_SPEED*/
		/*DATA:11|com.bianisoft.games.chess.SprSelector|Spr_Selector|/res/sprites/Selector.png|-172|275|4|1|0|0|2|Selection|1|0.0|MovePossible|1|0.0|*/
		com.bianisoft.games.chess.SprSelector sprSpr_Selector= new com.bianisoft.games.chess.SprSelector("/res/sprites/Selector.png");
		sprSpr_Selector.setTextID("Spr_Selector");
		sprSpr_Selector.setPos(-172, 275, 4);
		sprSpr_Selector.addState(sprSpr_Selector.new State("Selection", 1, 0.0f));
		sprSpr_Selector.addState(sprSpr_Selector.new State("MovePossible", 1, 0.0f));
		sprSpr_Selector.load();
		sprSpr_Selector.setCurState(0);
		sprSpr_Selector.setCurFrame(0);
		p_ctxUnder.addChild(sprSpr_Selector, false, false);

		/*DATA_CUSTOM_SPRITE_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS|DEFAULT_STATE|DEFAULT_FRAME|NB_STATES|STATE_NAME|STATE_NB_FRAMES|STATE_SPEED*/
		/*DATA:12|com.bianisoft.games.chess.SprPiece|Spr_PieceTemplate|/res/sprites/Pieces.png|-243|264|3|1|0|0|6|Color0|6|0.0|Color1|6|0.0|Color2|6|0.0|Color3|6|0.0|Color4|6|0.0|Color5|6|0.0|*/
		com.bianisoft.games.chess.SprPiece sprSpr_PieceTemplate= new com.bianisoft.games.chess.SprPiece("/res/sprites/Pieces.png");
		sprSpr_PieceTemplate.setTextID("Spr_PieceTemplate");
		sprSpr_PieceTemplate.setPos(-243, 264, 3);
		sprSpr_PieceTemplate.addState(sprSpr_PieceTemplate.new State("Color0", 6, 0.0f));
		sprSpr_PieceTemplate.addState(sprSpr_PieceTemplate.new State("Color1", 6, 0.0f));
		sprSpr_PieceTemplate.addState(sprSpr_PieceTemplate.new State("Color2", 6, 0.0f));
		sprSpr_PieceTemplate.addState(sprSpr_PieceTemplate.new State("Color3", 6, 0.0f));
		sprSpr_PieceTemplate.addState(sprSpr_PieceTemplate.new State("Color4", 6, 0.0f));
		sprSpr_PieceTemplate.addState(sprSpr_PieceTemplate.new State("Color5", 6, 0.0f));
		sprSpr_PieceTemplate.load();
		sprSpr_PieceTemplate.setCurState(0);
		sprSpr_PieceTemplate.setCurFrame(0);
		p_ctxUnder.addChild(sprSpr_PieceTemplate, false, false);

		/*DATA_SPRITE_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS|DEFAULT_STATE|DEFAULT_FRAME|NB_STATES|STATE_NAME|STATE_NB_FRAMES|STATE_SPEED*/
		/*DATA:13|Sprite|Spr_Cursor|/res/sprites/Cursor.png|0|0|0|2|0|0|3|Idle|1|0.0|Over|1|0.0|Waiting|1|0.0|*/
		Sprite sprSpr_Cursor= new Sprite("/res/sprites/Cursor.png");
		sprSpr_Cursor.setTextID("Spr_Cursor");
		sprSpr_Cursor.setPos(0, 0, 0);
		sprSpr_Cursor.addState(sprSpr_Cursor.new State("Idle", 1, 0.0f));
		sprSpr_Cursor.addState(sprSpr_Cursor.new State("Over", 1, 0.0f));
		sprSpr_Cursor.addState(sprSpr_Cursor.new State("Waiting", 1, 0.0f));
		sprSpr_Cursor.load();
		sprSpr_Cursor.setCurState(0);
		sprSpr_Cursor.setCurFrame(0);
		p_ctxUnder.addChild(sprSpr_Cursor, true, false);

	}
}
