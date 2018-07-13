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


public class DesignCtxHelp{
	public static void load(Context p_ctxUnder){
		/*DATA_BACKGROUND_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS*/
		/*DATA:0|Background|Back_Help|/res/backgrounds/Help.png|0|0|5|1|*/
		Background backBack_Help= new Background("/res/backgrounds/Help.png");
		backBack_Help.setTextID("Back_Help");
		backBack_Help.setPos(0, 0, 5);
		backBack_Help.load();
		p_ctxUnder.addChild(backBack_Help, false, false);

	}
}
