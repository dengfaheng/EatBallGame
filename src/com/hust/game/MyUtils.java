package com.hust.game;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;
import java.util.Scanner;

public class MyUtils {
	static String getRandomColor(Random r) {
		int count=(int) (r.nextInt(18)+1);
		String returnColor;
		switch (count) {
		case 1:
			returnColor = "#00FF00";
			break;
		case 2:
			returnColor = "#00FFFF";
			break;
		case 3:
			returnColor = "#00BFFF";
			break;
		case 4:
			returnColor = "#8A7CDA";
			break;
		case 5: //yellow , reset player small
			returnColor = "#FF00FF";
			break;
		case 6:
			returnColor = "#FF0000";
			break;
		case 7:
			returnColor = "#FA663C";
			break;
		case 8:
			returnColor = "#B5B5B5";
			break;
		case 9:
			returnColor = "#BF487A";
			break;	
		case 10:
			returnColor = "#90EE90";
			break;
		case 11:
			returnColor = "#C978E7";
			break;
		case 12: //boom
			returnColor = "#EED5D2";
			break;
		case 13:
			returnColor = "#52EA41";
			break;	
		case 14:
			returnColor = "#FF7F00";
			break;	
		case 15:
			returnColor = "#8B658B";
			break;
		case 16:
			returnColor = "#7FFFD4";
			break;
		case 17:      //life
			returnColor = "#6A5ACD";
			break;
		case 18:
			returnColor = "#9BCD9B";
			break;	
		default:
			returnColor = "#8B0A50";
			break;
		}
		
		return returnColor;

	}
	//filepath字体文件的路径
	public static java.awt.Font getSelfDefinedFont(String filepath, int size){
        java.awt.Font font = null;
        File file = new File(filepath);
        try{
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, file);
            font = font.deriveFont(java.awt.Font.PLAIN, size);
        }catch (FontFormatException e){
            return null;
        }catch (FileNotFoundException e){
            return null;
        }catch (IOException e){
            return null;
        }
        return font;
    }
	
	
	public static int readRecordFromFile(String filename) {
		int record = 0;
		try {		
			Scanner in = new Scanner(new FileReader(filename));
			if(in.hasNext()) {
				record = in.nextInt();
			}else {
				System.out.println("no record!");
			}
			in.close();
		} catch (FileNotFoundException e) {
			// File not found
			System.out.println("File not found!");
		}
		return record;
	}
    public static void writeRecordInFile(String filename, int record) {
    	File file = new File(filename);
        Writer writer = null;
        StringBuilder outputString = new StringBuilder();
        try {
            outputString.append(record);
            writer = new FileWriter(file, false); // true表示追加
            writer.write(outputString.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
