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


public class DesignCtxTitle{
	public static void load(Context p_ctxUnder){
		/*DATA_BACKGROUND_TEMPLATE:#|CLASS_ID|NAME|RESSOURCE_NAME|POS_X|POS_Y|POS_Z|DEEPNESS*/
		/*DATA:0|Background|Back_Title|/res/backgrounds/Title.png|0|0|5|1|*/
		Background backBack_Title= new Background("/res/backgrounds/Title.png");
		backBack_Title.setTextID("Back_Title");
		backBack_Title.setPos(0, 0, 5);
		backBack_Title.load();
		p_ctxUnder.addChild(backBack_Title, false, false);

	}
}
