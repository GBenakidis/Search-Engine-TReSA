package com.lucenetutorial.lucene;

import java.io.IOException;

public class Scripts {
	static void Script(int choise) throws IOException {
		if(choise == 1) {
			System.out.println("\n\nKALOSORISES STO TADE\nEXEIS TIS EPILOGES PARAKATW:");
			System.out.println("1. Plirofories kai energies gia to Data directory\n2. Anazhthsh leksis\n3. Exit");
		}
		else if(choise == 2) {
			System.out.println("\n\nIparxoyn sinolika: " + LuceneTester.numberOfFiles() + " arxeia");
			System.out.println("\nOI EPILOGES POU EXEIS EINAI:\n1. Prosthiki arxeiou\n2. Afairesi arxeiou\n3. Tropopoihsh arxeiou\n4. Back!");
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

	}
	
}
