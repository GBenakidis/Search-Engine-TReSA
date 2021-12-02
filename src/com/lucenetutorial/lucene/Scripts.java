package com.lucenetutorial.lucene;

import java.io.IOException;

public class Scripts {
	static void Script(int choise) throws IOException {
		if(choise == 1) {
			System.out.println("\n\nKALOSORISES STO TADE\nEXEIS TIS EPILOGES PARAKATW:");
			System.out.println("1. Plirofories gia to Data directory\n2. Dimiourgia h enhmerwsh euretiriou\n3. Anazhthsh leksis\n4. Exit");
		}
		else if(choise == 2) {
			System.out.println("\n\nIparxoyn sinolika: " + LuceneTester.numberOfFiles() + " arxeia");
		}
		else if(choise == 3) {
			System.out.println("\nBye!");
		}
		else if(choise == 4) {
			System.out.println("\n\nLathos kwdikos");
		}
		else if(choise == 5) {
			System.out.println("\n\nTo arxeio sou dimiourgithike");
		}
		else if(choise == 6) {
			System.out.println("\nSe poia xwra exei graftei:");
		}
		else if(choise == 7) {
			System.out.println("\nApo poious exei graftei[mporeis na to afhseis kai keno]:");
		}
		else if(choise == 8) {
			System.out.println("\nPoios einai o titlos tous arthrou:");
		}
		else if(choise == 9) {
			System.out.println("\nPoia einai ta periexomena tou arthrou:");
		}
		else if(choise == 10) {
			System.out.println("\nPoion arithmo arxeiou thes na diagrapseis:");
		}
		else if(choise == 11) {
			System.out.println("\nTi theleis na kanei:");
		}
		else if(choise == 12) {
			System.out.println("\nTo euretirio dimiourgithike!");
		}
		else if(choise == 13) {
			System.out.println("\nGrapse thn leksi pou thes na breis:");
		}

	}
	
}
