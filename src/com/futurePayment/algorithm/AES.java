package com.futurePayment.algorithm;

public class AES {
	private static byte[][] sBox;        //s box
	private static byte[][] invsBox;     
	private static byte[] key;   //key
	private static byte[][] w;   // expanded key
	private static byte[][] state;
	private static byte[][] Rcon;
	private static int Nb;       //number of blocks
	private static int Nk;       //length of key
	private static int Nr;       // number of rounds
	
	private AES(){
		
	}
	static {
		byte[] keyBytes = new byte[] { 0x23, 0x56, 0x02, 0x03, 0x04, 0x05,  
        0x06, 0x07, 0x08, 0x09, 0x0c, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,  
        0x10, 0x11, 0x12, 0x13, 0x14, 0x51, 0x16, 0x17 };
		
		setParameter(keyBytes, 128, 4);
		initialize();
		keyExpansion();
	}
	
	public static String encrypt(String text){
		int length = text.length();
		StringBuffer encryptedText = new StringBuffer(length);
		final byte SPACE = ' ';
		byte[] current = new byte[16];
		int textIndex = 0,charIndex = 0; 
		while(textIndex < text.length()){
			for(charIndex = 0; charIndex < 16 && textIndex < text.length();){
				current[charIndex] = (byte)text.charAt(textIndex);
				charIndex++;
				textIndex++;
			}
			if(charIndex < 16)
				for(; charIndex < 16; charIndex++)
					current[charIndex] = SPACE;
			
			current = Cipher(current);
			for(int i = 0; i < current.length; i++)
				encryptedText.append((char)current[i]);
		}
		return  encryptedText.toString();
	}
	
	public static String decrypt(String text){
		int length = text.length();
		StringBuffer decryptedText = new StringBuffer(length);
		final byte SPACE = ' ';
		byte[] current = new byte[16];
		int textIndex = 0,charIndex = 0; 
		while(textIndex < text.length()){
			for(charIndex = 0; charIndex < 16 && textIndex < text.length();){
				current[charIndex] = (byte)text.charAt(textIndex);
				charIndex++;
				textIndex++;
			}
			if(charIndex < 16)
				for(; charIndex < 16; charIndex++)
					current[charIndex] = SPACE;
			
			current = invCipher(current);
			for(int i = 0; i < current.length; i++)
				decryptedText.append((char)current[i]);
		}
		return  decryptedText.toString();
	}
	
	public static byte[] Cipher(byte[] input){
		state = new byte[4][Nb];
		for(int i = 0; i < (4 * Nb); i++)
			state[i/Nb][i%Nb] = input[i];
		addRoundKey(0);
		for(int round = 1; round < Nr; round++){
			subBytes();
			shiftRows();
			mixColumns();
			addRoundKey(round);
		}
		
		subBytes();
		shiftRows();
		addRoundKey(Nr);
		
		byte[] out = new byte[4 * Nb];
		for(int i = 0; i < (4 * Nb); i++){
			out[i] = state[i/Nb][i%Nb];
		}
		
		return out;
	}
	
	public static byte[] invCipher(byte[]input){
		state = new byte[4][Nb];
		for(int i = 0; i < (4 * Nb); i++)
			state[i/Nb][i%Nb] = input[i];
		addRoundKey(Nr);
		for(int round = Nr - 1; round > 0; round--){
			invShiftRows();
			invSubBytes();
			addRoundKey(round);
			invMixColumns();
		}
		
		invShiftRows();
		invSubBytes();
		addRoundKey(0);
		
		byte[] out = new byte[4 * Nb];
		for(int i = 0; i < (4 * Nb); i++){
			out[i] = state[i/Nb][i%Nb];
		}
		return out;
	}
	
	/**
	 * initialize sBox and indvsBox
	 */
	private static void initialize(){
		byte[][] box1 = {
			{99,124,119,123,-14,107,111,-59,48,1,103,43,-2,-41,-85,118},
			{-54,-126,-55,125,-6,89,71,-16,-83,-44,-94,-81,-100,-92,114,-64},
			{-73,-3,-109,38,54,63,-9,-52,52,-91,-27,-15,113,-40,49,21},
			{4,-57,35,-61,24,-106,5,-102,7,18,-128,-30,-21,39,-78,117},
			{9,-125,44,26,27,110,90,-96,82,59,-42,-77,41,-29,47,-124},
			{83,-47,0,-19,32,-4,-79,91,106,-53,-66,57,74,76,88,-49},
			{-48,-17,-86,-5,67,77,51,-123,69,-7,2,127,80,60,-97,-88},
			{81,-93,64,-113,-110,-99,56,-11,-68,-74,-38,33,16,-1,-13,-46},
			{-51,12,19,-20,95,-105,68,23,-60,-89,126,61,100,93,25,115},
			{96,-127,79,-36,34,42,-112,-120,70,-18,-72,20,-34,94,11,-37},
			{-32,50,58,10,73,6,36,92,-62,-45,-84,98,-111,-107,-28,121},
			{-25,-56,55,109,-115,-43,78,-87,108,86,-12,-22,101,122,-82,8},
			{-70,120,37,46,28,-90,-76,-58,-24,-35,116,31,75,-67,-117,-118},
			{112,62,-75,102,72,3,-10,14,97,53,87,-71,-122,-63,29,-98},
			{-31,-8,-104,17,105,-39,-114,-108,-101,30,-121,-23,-50,85,40,-33},
			{-116,-95,-119,13,-65,-26,66,104,65,-103,45,15,-80,84,-69,22}
		};
		
		byte[][] box2 = {
			{82,9,106,-43,48,54,-91,56,-65,64,-93,-98,-127,-13,-41,-5},
			{124,-29,57,-126,-101,47,-1,-121,52,-114,67,68,-60,-34,-23,-53},
			{84,123,-108,50,-90,-62,35,61,-18,76,-107,11,66,-6,-61,78},
			{8,46,-95,102,40,-39,36,-78,118,91,-94,73,109,-117,-47,37},
			{114,-8,-10,100,-122,104,-104,22,-44,-92,92,-52,93,101,-74,-110},
			{108,112,72,80,-3,-19,-71,-38,94,21,70,87,-89,-115,-99,-124},
			{-112,-40,-85,0,-116,-68,-45,10,-9,-28,88,5,-72,-77,69,6},
			{-48,44,30,-113,-54,63,15,2,-63,-81,-67,3,1,19,-118,107},
			{58,-111,17,65,79,103,-36,-22,-105,-14,-49,-50,-16,-76,-26,115},
			{-106,-84,116,34,-25,-83,53,-123,-30,-7,55,-24,28,117,-33,110},
			{71,-15,26,113,29,41,-59,-119,111,-73,98,14,-86,24,-66,27},
			{-4,86,62,75,-58,-46,121,32,-102,-37,-64,-2,120,-51,90,-12},
			{31,-35,-88,51,-120,7,-57,49,-79,18,16,89,39,-128,-20,95},
			{96,81,127,-87,25,-75,74,13,45,-27,122,-97,-109,-55,-100,-17},
			{-96,-32,59,77,-82,42,-11,-80,-56,-21,-69,60,-125,83,-103,97},
			{23,43,4,126,-70,119,-42,38,-31,105,20,99,85,33,12,125}
		};
		
		sBox = box1;
		invsBox = box2;
		
		Rcon = new byte[100][4];
		Rcon[0][0] = 0X00;
		for(int i = 1; i < 100; i++)
			Rcon[i][0] = gfmutby02(Rcon[i-1][0]);
	}
	
	public static void setParameter(byte[] k,int keyLength,int nb){
		Nb = nb;
		switch(keyLength){
		case 128:
			Nk = 4;
			Nr = 10;
			break;
		case 192:
			Nk = 6;
			Nr = 12;
			break;
		case 256:
			Nk = 8;
			Nr = 14;
			break;
		}
	
		key = k;
	}
	
	private static void subWord(byte[] subword){
		for(int c = 0; c < subword.length; c++){
			int x = (subword[c] >> 4) & 0x0f;
			int y = subword[c] & 0x0f;
			subword[c] = sBox[x][y];
		}
	}
	
	private static byte[] rotWord(byte[] rotword){
		byte[] b = new byte[4];
		for(int i = 0; i < 4; i++)
			b[i] = rotword[(i+1)%4];
		
		return b;
	}
	
	private static void addRoundKey(int round){
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < Nb; c++)
				state[r][c] = (byte) (state[r][c] ^ w[(round*Nb)+c][r]);
		}
	}
	
	private static void subBytes(){
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < Nb; c++){
				int x = (state[r][c] >> 4) & 0x0f;
				int y = state[r][c] & 0x0f;
				state[r][c] = sBox[x][y];
			}
		}
	}
	
	private static void invSubBytes(){
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < Nb; c++){
				int x = (state[r][c] >> 4) & 0x0f;
				int y = state[r][c] & 0x0f;
				state[r][c] = invsBox[x][y];
			}
		}
	}
	
	private static void shiftRows(){
		byte[] temp = new byte[Nb];
		
		int r,c;
		for(r = 1; r < 4; r++){
			for(c = 0; c < Nb; c++)
				temp[c] = state[r][(c+r)%Nb];
			
			for(c = 0; c < Nb; c++)
				state[r][c] = temp[c];
		}
	}
	
	private static void invShiftRows(){
		byte[] temp = new byte[Nb];
		
		int r,c;
		for(r = 1; r < 4; r++){
			for(c = 0; c < Nb; c++)
				temp[c] = state[r][(c+Nb-r)%Nb];
			
			for(c = 0; c < Nb; c++)
				state[r][c] = temp[c];
		}
	}

	private static void mixColumns(){
		byte[][] temp = new byte[4][Nb];
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < Nb; c++){
				temp[r][c] = state[r][c];
			}
		}
		
		for(int c = 0; c < Nb; c++){
			state[0][c] = (byte) (gfmutby02(temp[0][c])
                    ^ gfmutby03(temp[1][c]) ^ gfmutby01(temp[2][c]) ^ gfmutby01(temp[3][c])); 
			
            state[1][c] = (byte) (gfmutby01(temp[0][c]) ^ gfmutby02(temp[1][c]) 
            		^ gfmutby03(temp[2][c]) ^ gfmutby01(temp[3][c]));  
            

            state[2][c] = (byte) (gfmutby01(temp[0][c]) ^ (gfmutby01(temp[1][c])  
                    ^ gfmutby02(temp[2][c]) ^ gfmutby03(temp[3][c]))); 
            
            state[3][c] = (byte) (gfmutby03(temp[0][c])   
                    ^ gfmutby01(temp[1][c]) ^ (gfmutby01(temp[2][c]) ^ gfmutby02(temp[3][c])));  

		}
	
	}
	
	private static void invMixColumns(){
		byte[][] temp = new byte[4][Nb];
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < Nb; c++){
				temp[r][c] = state[r][c];
			}
		}
		
		for(int c = 0; c < Nb; c++){
			state[0][c] = (byte) (gfmutby0e(temp[0][c])   
		                    ^ gfmutby0b(temp[1][c]) ^ gfmutby0d(temp[2][c]) ^ gfmutby09(temp[3][c]));  

            state[1][c] = (byte) (gfmutby09(temp[0][c])  
                            ^ gfmutby0e(temp[1][c]) ^ gfmutby0b(temp[2][c]) ^ gfmutby0d(temp[3][c]));  
            
            state[2][c] = (byte) (gfmutby0d(temp[0][c]) ^ (gfmutby09(temp[1][c])  
                            ^ gfmutby0e(temp[2][c]) ^ gfmutby0b(temp[3][c])));  

            state[3][c] = (byte) (gfmutby0b(temp[0][c])   
                            ^ gfmutby0d(temp[1][c]) ^ (gfmutby09(temp[2][c]) ^ gfmutby0e(temp[3][c])));  
		}
	}
	
	/**
	 * expand the key
	 */
	private static void keyExpansion(){
		w = new byte[Nb*(Nr + 1)][4];
		
		for(int r = 0; r < Nk; r++){
			w[r][0] = key[4*r];
			w[r][1] = key[4*r+1];
			w[r][2] = key[4*r+2];
			w[r][3] = key[4*r+3];
		}
		
		byte[] temp = new byte[4]; 
		for(int r = Nk; r < Nb * (Nr + 1); r++){
			temp[0] = w[r-1][0];  
            temp[1] = w[r-1][1];  
            temp[2] = w[r-1][2];  
            temp[3] = w[r-1][3];  
 
            if(r % Nk == 0){
            	temp = rotWord(temp);
            	subWord(temp);
            	temp[0] = (byte) (temp[0] ^ Rcon[r/Nk][0]);  
                temp[1] = (byte) (temp[1] ^ Rcon[r/Nk][1]);  
                temp[2] = (byte) (temp[2] ^ Rcon[r/Nk][2]); 
                temp[3] = (byte) (temp[3] ^ Rcon[r/Nk][3]);  
            }
            else if(Nk > 6 && (r % Nk == 4))
            	subWord(temp);
            
            w[r][0] = (byte) (w[r-Nk][0] ^ temp[0]);  
            w[r][1] = (byte) (w[r-Nk][1] ^ temp[1]);  
            w[r][2] = (byte) (w[r-Nk][2] ^ temp[2]); 
            w[r][3] = (byte) (w[r-Nk][3] ^ temp[3]);  
		}
	}
	
	/**
	 * GF(28) Transform
	 * 
	 * @param b  input byte
	 * @return   the responding byte after GF(28) transform
	 */
	private static byte gfmutby01(byte b){
		return b;
	}
	
	private static byte gfmutby02(byte b){
		if(((b >> 7) & 0x01)== 0){
			return (byte)(b << 1);
		}
		else 
			return (byte)((b << 1) ^ 0x1b);
	}
	
	private static byte gfmutby03(byte b){
		return (byte)(gfmutby01(b) ^ gfmutby02(b));
	}
	
	private static byte gfmutby09(byte b){
		return (byte)(gfmutby02(gfmutby02(gfmutby02(b))) ^ gfmutby01(b));
	}
	
	private static byte gfmutby0b(byte b){
		return (byte)(gfmutby02(gfmutby02(gfmutby02(b))) ^ gfmutby02(b) ^ gfmutby01(b));
	}
	
	private static byte gfmutby0d(byte b){
		return (byte)(gfmutby02(gfmutby02(gfmutby02(b))) ^
					  gfmutby02(gfmutby02(b)) ^ gfmutby01(b));
	}
	
	private static byte gfmutby0e(byte b){
		return (byte)(gfmutby02(gfmutby02(gfmutby02(b))) ^
				  gfmutby02(gfmutby02(b)) ^ gfmutby02(b));
	}
	
}
