package com.hust.game;

import java.awt.Color;
import java.util.Random;

public class MyUtils {
	static Color getRandomColor(Random r) {
		int count=(int) (r.nextInt(18)+1);
		Color returnColor;
		switch (count) {
		case 1:
			returnColor = Color.green;
			break;
		case 2:
			returnColor = Color.black;
			break;
		case 3:
			returnColor = Color.blue;
			break;
		case 4:
			returnColor = Color.CYAN;
			break;
		case 5:
			returnColor = Color.yellow;
			break;
		case 6:
			returnColor = Color.red;
			break;
		case 7:
			returnColor = Color.gray;
			break;
		case 8:
			returnColor = Color.PINK;
			break;
		case 9:
			returnColor = Color.magenta;
			break;	
		case 10:
			returnColor = Color.gray;
			break;
		case 11:
			returnColor = Color.gray;
			break;
		case 12:
			returnColor = Color.PINK;
			break;
		case 13:
			returnColor = Color.magenta;
			break;	
		case 14:
			returnColor = Color.gray;
			break;	
		case 15:
			returnColor = Color.green;
			break;
		case 16:
			returnColor = Color.black;
			break;
		case 17:
			returnColor = Color.blue;
			break;
		case 18:
			returnColor = Color.CYAN;
			break;	
		default:
			returnColor = Color.green;
			break;
		}
		
		return returnColor;

	}
	
}
